package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.CustomerSupportPlatformIdentifier;
import ai.gradientlabs.client.model.CustomerSupportPlatformIdentifierType;
import ai.gradientlabs.client.model.SupportPlatform;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StartOutboundConversationRequestTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void serializesChatRequest() {
        StartOutboundChatConversationRequest request = StartOutboundChatConversationRequest.builder()
                .customerId("user-1")
                .procedureId("procedure-1")
                .supportPlatform(StartOutboundChatConversationRequest.SupportPlatform.INTERCOM)
                .body("Hi there")
                .addCustomerSupportPlatformIdentifier(new CustomerSupportPlatformIdentifier(
                        SupportPlatform.INTERCOM,
                        CustomerSupportPlatformIdentifierType.INTERCOM_USER,
                        "6953e162a988d9ef0f73ef9b"))
                .resources(Map.of("customer_profile", Map.of("tier", "premium")))
                .build();

        JsonNode json = objectMapper.valueToTree(request);

        assertEquals("user-1", json.get("customer_id").asText());
        assertEquals("procedure-1", json.get("procedure_id").asText());
        assertEquals("intercom", json.get("support_platform").asText());
        assertEquals("Hi there", json.get("body").asText());
        assertEquals("premium", json.get("resources").get("customer_profile").get("tier").asText());

        JsonNode identifier = json.get("customer_support_platform_identifiers").get(0);
        assertEquals("intercom", identifier.get("support_platform").asText());
        assertEquals("intercom_user", identifier.get("type").asText());
        assertEquals("6953e162a988d9ef0f73ef9b", identifier.get("value").asText());
    }

    @Test
    void omitsUnsetOptionalChatFields() {
        StartOutboundChatConversationRequest request = StartOutboundChatConversationRequest.builder()
                .customerId("user-1")
                .procedureId("procedure-1")
                .supportPlatform(StartOutboundChatConversationRequest.SupportPlatform.PUBLIC_API)
                .build();

        JsonNode json = objectMapper.valueToTree(request);

        assertFalse(json.has("body"));
        assertFalse(json.has("resources"));
        assertFalse(json.has("customer_support_platform_identifiers"));
    }

    @Test
    void chatRequiresSupportPlatform() {
        StartOutboundChatConversationRequest.Builder builder = StartOutboundChatConversationRequest.builder()
                .customerId("user-1")
                .procedureId("procedure-1");

        assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void serializesEmailRequest() {
        StartOutboundEmailConversationRequest request = StartOutboundEmailConversationRequest.builder()
                .customerId("user-1")
                .procedureId("procedure-1")
                .supportPlatform(StartOutboundEmailConversationRequest.SupportPlatform.ZENDESK)
                .subject("Your order")
                .body("It has shipped.")
                .addCustomerSupportPlatformIdentifier(new CustomerSupportPlatformIdentifier(
                        SupportPlatform.ZENDESK,
                        CustomerSupportPlatformIdentifierType.ZENDESK_SUPPORT_USER,
                        "42"))
                .build();

        JsonNode json = objectMapper.valueToTree(request);

        assertEquals("zendesk", json.get("support_platform").asText());
        assertEquals("Your order", json.get("subject").asText());
        assertEquals("It has shipped.", json.get("body").asText());
        assertEquals("zendesk_support_user",
                json.get("customer_support_platform_identifiers").get(0).get("type").asText());
    }

    @Test
    void emailRejectsSubjectWithoutBody() {
        StartOutboundEmailConversationRequest.Builder builder = StartOutboundEmailConversationRequest.builder()
                .customerId("user-1")
                .procedureId("procedure-1")
                .supportPlatform(StartOutboundEmailConversationRequest.SupportPlatform.INTERCOM)
                .subject("Your order");

        assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void emailRejectsBodyWithoutSubject() {
        StartOutboundEmailConversationRequest.Builder builder = StartOutboundEmailConversationRequest.builder()
                .customerId("user-1")
                .procedureId("procedure-1")
                .supportPlatform(StartOutboundEmailConversationRequest.SupportPlatform.INTERCOM)
                .body("It has shipped.");

        assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void serializesPhoneRequest() {
        StartOutboundPhoneConversationRequest request = StartOutboundPhoneConversationRequest.builder()
                .customerId("user-1")
                .procedureId("procedure-1")
                .toPhoneNumber("+14155551234")
                .fromPhoneNumber("+14155555678")
                .build();

        JsonNode json = objectMapper.valueToTree(request);

        assertEquals("+14155551234", json.get("to_phone_number").asText());
        assertEquals("+14155555678", json.get("from_phone_number").asText());
        assertFalse(json.has("support_platform"));
        assertFalse(json.has("channel"));
    }

    @Test
    void phoneRequiresBothPhoneNumbers() {
        StartOutboundPhoneConversationRequest.Builder missingFrom = StartOutboundPhoneConversationRequest.builder()
                .customerId("user-1")
                .procedureId("procedure-1")
                .toPhoneNumber("+14155551234");

        assertThrows(IllegalArgumentException.class, missingFrom::build);

        StartOutboundPhoneConversationRequest.Builder missingTo = StartOutboundPhoneConversationRequest.builder()
                .customerId("user-1")
                .procedureId("procedure-1")
                .fromPhoneNumber("+14155555678");

        assertThrows(IllegalArgumentException.class, missingTo::build);
    }
}
