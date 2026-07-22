package ai.gradientlabs.client.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConversationMemoriesBulkUploadRequestTest {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void serializesAllFields() throws Exception {
        ConversationMemoriesBulkUploadRequest request = ConversationMemoriesBulkUploadRequest.builder()
                .idempotencyKey("key-123")
                .memories(List.of(
                        Map.of("title", "Order #1", "amount", 42),
                        Map.of("title", "Order #2")))
                .createdAtKeys(List.of("created_at", "timestamp"))
                .build();

        JsonNode json = objectMapper.valueToTree(request);

        assertEquals("key-123", json.get("idempotency_key").asText());
        assertTrue(json.get("memories").isArray());
        assertEquals(2, json.get("memories").size());
        assertEquals("Order #1", json.get("memories").get(0).get("title").asText());
        assertEquals(42, json.get("memories").get(0).get("amount").asInt());
        assertEquals("created_at", json.get("created_at_keys").get(0).asText());
        assertEquals("timestamp", json.get("created_at_keys").get(1).asText());
    }

    @Test
    void omitsCreatedAtKeysWhenNotSet() throws Exception {
        ConversationMemoriesBulkUploadRequest request = ConversationMemoriesBulkUploadRequest.builder()
                .idempotencyKey("key-123")
                .memories(List.of(Map.of("title", "Order #1")))
                .build();

        JsonNode json = objectMapper.valueToTree(request);

        assertFalse(json.has("created_at_keys"));
    }

    @Test
    void requiresIdempotencyKey() {
        assertThrows(IllegalStateException.class, () -> ConversationMemoriesBulkUploadRequest.builder()
                .memories(List.of(Map.of("title", "Order #1")))
                .build());
    }

    @Test
    void requiresNonEmptyMemories() {
        assertThrows(IllegalStateException.class, () -> ConversationMemoriesBulkUploadRequest.builder()
                .idempotencyKey("key-123")
                .memories(List.of())
                .build());
    }
}
