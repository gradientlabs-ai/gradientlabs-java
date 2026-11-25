package ai.gradientlabs.client.webhook;

import ai.gradientlabs.client.exception.InvalidWebhookSignatureException;
import ai.gradientlabs.client.model.BackOfficeTaskStatus;
import ai.gradientlabs.client.model.Channel;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.StringReader;
import java.lang.reflect.Proxy;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.HexFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WebhookVerifierTest {

    private static final String KEY = "signing-key";
    private final WebhookVerifier verifier = new WebhookVerifier(KEY, Duration.ofMinutes(5));

    private static String envelope(String type, String data) {
        return "{\"id\":\"evt-1\",\"type\":\"" + type + "\",\"sequence_number\":7,"
                + "\"timestamp\":\"2026-01-02T03:04:05Z\",\"data\":" + data + "}";
    }

    private static String sign(String key, long timestamp, String body) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] sig = mac.doFinal((timestamp + "." + body).getBytes(StandardCharsets.UTF_8));
        return "t=" + timestamp + ",v1=" + HexFormat.of().formatHex(sig);
    }

    private static HttpServletRequest request(String body, String signature) throws Exception {
        BufferedReader reader = new BufferedReader(new StringReader(body));
        return (HttpServletRequest) Proxy.newProxyInstance(
                HttpServletRequest.class.getClassLoader(),
                new Class<?>[]{HttpServletRequest.class},
                (proxy, method, args) -> {
                    switch (method.getName()) {
                        case "getHeader":
                            return "X-GradientLabs-Signature".equals(args[0]) ? signature : null;
                        case "getReader":
                            return reader;
                        default:
                            return null;
                    }
                });
    }

    private Webhook parse(String body) throws Exception {
        return verifier.parseAndVerify(request(body, sign(KEY, Instant.now().getEpochSecond(), body)));
    }

    @Test
    void rejectsWrongKeyAndStaleTimestamps() throws Exception {
        String body = envelope("agent.message", "{\"body\":\"hi\",\"conversation\":{\"id\":\"c\"}}");
        long now = Instant.now().getEpochSecond();

        assertThrows(InvalidWebhookSignatureException.class,
                () -> verifier.parseAndVerify(request(body, sign("other-key", now, body))));
        assertThrows(InvalidWebhookSignatureException.class,
                () -> verifier.parseAndVerify(request(body, sign(KEY, now - 3600, body))));
        assertThrows(InvalidWebhookSignatureException.class,
                () -> verifier.parseAndVerify(request(body, sign(KEY, now + 3600, body))));
        assertThrows(InvalidWebhookSignatureException.class,
                () -> verifier.parseAndVerify(request(body, "garbage")));
    }

    @Test
    void parsesAgentMessage() throws Exception {
        Webhook webhook = parse(envelope("agent.message", "{\"body\":\"hi\",\"is_holding\":true,"
                + "\"last_customer_message_id\":\"m1\",\"conversation\":{\"id\":\"c\",\"customer_id\":\"u\"}}"));

        assertEquals(WebhookType.AGENT_MESSAGE, webhook.getType());
        assertEquals("hi", webhook.asAgentMessage().getBody());
        assertEquals(Boolean.TRUE, webhook.asAgentMessage().getIsHolding());
        assertEquals("m1", webhook.asAgentMessage().getLastCustomerMessageId());
    }

    @Test
    void parsesConversationHandOff() throws Exception {
        Webhook webhook = parse(envelope("conversation.hand_off",
                "{\"conversation\":{\"id\":\"c\"},\"reason_code\":\"customer-request\",\"reason\":\"asked\"}"));

        assertEquals(WebhookType.CONVERSATION_HANDOFF, webhook.getType());
        assertEquals("customer-request", webhook.asConversationHandOff().getReason());
    }

    @Test
    void parsesConversationStart() throws Exception {
        Webhook webhook = parse(envelope("conversation.start", "{\"body\":\"Hello\",\"subject\":\"Hi\","
                + "\"channel\":\"email\",\"customer_id\":\"u\",\"initiator_id\":\"i\"}"));

        assertEquals(WebhookType.CONVERSATION_START, webhook.getType());
        assertEquals(Channel.EMAIL, webhook.asConversationStart().getChannel());
        assertEquals("i", webhook.asConversationStart().getInitiatorId());
    }

    @Test
    void parsesActionExecuteForABackOfficeTask() throws Exception {
        Webhook webhook = parse(envelope("action.execute",
                "{\"action\":\"lookup\",\"params\":{},\"back_office_task\":{\"id\":\"task-1\"}}"));

        assertEquals("task-1", webhook.asActionExecute().getBackOfficeTask().getId());
    }

    @Test
    void parsesBackOfficeTaskEvents() throws Exception {
        String task = "{\"back_office_task\":{\"id\":\"task-1\",\"agent_id\":\"a\",\"status\":\"completed\","
                + "\"input\":{},\"created\":\"2026-01-02T03:04:05Z\",\"attachments\":[{\"file_name\":\"f\","
                + "\"idempotency_key\":\"k\",\"external_url\":\"https://example.com/file\"}]}}";

        Webhook complete = parse(envelope("back-office-task.complete", task));
        assertEquals(WebhookType.BACK_OFFICE_TASK_COMPLETE, complete.getType());
        assertEquals(BackOfficeTaskStatus.COMPLETED, complete.asBackOfficeTaskComplete().getBackOfficeTask().getStatus());
        assertEquals("https://example.com/file", complete.asBackOfficeTaskComplete().getBackOfficeTask()
                .getAttachments().get(0).getExternalUrl());

        assertEquals(WebhookType.BACK_OFFICE_TASK_HAND_OFF,
                parse(envelope("back-office-task.hand-off", task)).getType());
        assertEquals("task-1", parse(envelope("back-office-task.fail", task))
                .asBackOfficeTaskFail().getBackOfficeTask().getId());
    }

    @Test
    void unknownTypesReturnTheRawDataInsteadOfThrowing() throws Exception {
        Webhook webhook = parse(envelope("something.new", "{\"x\":1}"));

        assertEquals(WebhookType.UNKNOWN, webhook.getType());
        assertTrue(webhook.getData() instanceof JsonNode);
    }
}
