package ai.gradientlabs.client.webhook;

import ai.gradientlabs.client.exception.GradientLabsException;
import ai.gradientlabs.client.exception.InvalidWebhookSignatureException;
import ai.gradientlabs.client.webhook.event.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Verifies the authenticity of webhook requests using their signature.
 */
public class WebhookVerifier {

    private static final String SIGNATURE_HEADER = "X-GradientLabs-Signature";
    private static final String TOKEN_HEADER = "X-GradientLabs-Token";
    private static final String HMAC_SHA256 = "HmacSHA256";

    private final byte[] secret;
    private final Duration leeway;
    private final ObjectMapper objectMapper;

    /**
     * Creates a new webhook verifier.
     *
     * @param signingKey the webhook signing key
     * @param leeway     the maximum age of webhooks to accept
     */
    public WebhookVerifier(String signingKey, Duration leeway) {
        this.secret = signingKey.getBytes(StandardCharsets.UTF_8);
        this.leeway = leeway;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Parses and verifies a webhook request.
     *
     * @param request the HTTP servlet request
     * @return the parsed webhook
     * @throws InvalidWebhookSignatureException if verification fails
     * @throws GradientLabsException            if parsing fails
     */
    public Webhook parseAndVerify(HttpServletRequest request) {
        String signature = request.getHeader(SIGNATURE_HEADER);
        if (signature == null || signature.isEmpty()) {
            throw new InvalidWebhookSignatureException("Missing " + SIGNATURE_HEADER + " header");
        }

        String body;
        try {
            body = request.getReader().lines().collect(Collectors.joining());
        } catch (IOException e) {
            throw new GradientLabsException("Failed to read request body", e);
        }

        verifySignature(body, signature);

        return parseWebhook(body);
    }

    /**
     * Verifies a webhook request's signature without parsing.
     *
     * @param request the HTTP servlet request
     * @throws InvalidWebhookSignatureException if verification fails
     * @throws GradientLabsException            if reading the request fails
     */
    public void verify(HttpServletRequest request) {
        String signature = request.getHeader(SIGNATURE_HEADER);
        if (signature == null || signature.isEmpty()) {
            throw new InvalidWebhookSignatureException("Missing " + SIGNATURE_HEADER + " header");
        }

        String body;
        try {
            body = request.getReader().lines().collect(Collectors.joining());
        } catch (IOException e) {
            throw new GradientLabsException("Failed to read request body", e);
        }

        verifySignature(body, signature);
    }

    /**
     * Verifies the signature of a webhook payload.
     *
     * @param body      the request body
     * @param signature the signature header value
     * @throws InvalidWebhookSignatureException if verification fails
     */
    public void verifySignature(String body, String signature) {
        SignatureComponents components = parseSignatureHeader(signature);

        // Check timestamp is within leeway
        Instant now = Instant.now();
        Duration age = Duration.between(components.timestamp, now).abs();
        if (age.compareTo(leeway) > 0) {
            throw new InvalidWebhookSignatureException("Webhook timestamp too old");
        }

        // Compute expected signature
        byte[] expectedSignature = computeSignature(components.timestamp, body);

        // Verify signature matches one of the provided signatures
        boolean valid = components.signatures.stream()
                .anyMatch(sig -> MessageDigest.isEqual(expectedSignature, sig));

        if (!valid) {
            throw new InvalidWebhookSignatureException("Webhook signature verification failed");
        }
    }

    private SignatureComponents parseSignatureHeader(String header) {
        Instant timestamp = null;
        java.util.List<byte[]> signatures = new java.util.ArrayList<>();

        String[] parts = header.split(",");
        for (String part : parts) {
            String[] kv = part.split("=", 2);
            if (kv.length != 2) {
                throw new InvalidWebhookSignatureException("Invalid signature header format");
            }

            String key = kv[0].trim();
            String value = kv[1].trim();

            switch (key) {
                case "t":
                    try {
                        long unix = Long.parseLong(value);
                        timestamp = Instant.ofEpochSecond(unix);
                    } catch (NumberFormatException e) {
                        throw new InvalidWebhookSignatureException("Invalid timestamp in signature");
                    }
                    break;
                case "v1":
                    try {
                        signatures.add(hexToBytes(value));
                    } catch (IllegalArgumentException e) {
                        throw new InvalidWebhookSignatureException("Invalid signature format");
                    }
                    break;
            }
        }

        if (timestamp == null) {
            throw new InvalidWebhookSignatureException("Missing timestamp in signature");
        }

        if (signatures.isEmpty()) {
            throw new InvalidWebhookSignatureException("Missing signature in header");
        }

        return new SignatureComponents(timestamp, signatures);
    }

    private byte[] computeSignature(Instant timestamp, String body) {
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256);
            SecretKeySpec keySpec = new SecretKeySpec(secret, HMAC_SHA256);
            mac.init(keySpec);

            String signedPayload = timestamp.getEpochSecond() + "." + body;
            return mac.doFinal(signedPayload.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new GradientLabsException("Failed to compute signature", e);
        }
    }

    private Webhook parseWebhook(String body) {
        try {
            JsonNode root = objectMapper.readTree(body);
            Webhook webhook = new Webhook();

            webhook.setId(root.get("id").asText());
            webhook.setType(WebhookType.valueOf(
                    root.get("type").asText().replace(".", "_").toUpperCase()
            ));
            webhook.setSequenceNumber(root.get("sequence_number").asInt());
            webhook.setTimestamp(Instant.parse(root.get("timestamp").asText()));

            JsonNode dataNode = root.get("data");
            Object eventData = parseEventData(webhook.getType(), dataNode);
            webhook.setData(eventData);

            return webhook;
        } catch (IOException e) {
            throw new GradientLabsException("Failed to parse webhook", e);
        }
    }

    private Object parseEventData(WebhookType type, JsonNode dataNode) throws IOException {
        switch (type) {
            case AGENT_MESSAGE:
                return objectMapper.treeToValue(dataNode, AgentMessageEvent.class);
            case CONVERSATION_HANDOFF:
                return objectMapper.treeToValue(dataNode, ConversationHandOffEvent.class);
            case CONVERSATION_FINISHED:
                return objectMapper.treeToValue(dataNode, ConversationFinishedEvent.class);
            case ACTION_EXECUTE:
                return objectMapper.treeToValue(dataNode, ActionExecuteEvent.class);
            case RESOURCE_PULL:
                return objectMapper.treeToValue(dataNode, ResourcePullEvent.class);
            default:
                throw new GradientLabsException("Unknown webhook type: " + type);
        }
    }

    private static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    private static class SignatureComponents {
        final Instant timestamp;
        final java.util.List<byte[]> signatures;

        SignatureComponents(Instant timestamp, java.util.List<byte[]> signatures) {
            this.timestamp = timestamp;
            this.signatures = signatures;
        }
    }
}
