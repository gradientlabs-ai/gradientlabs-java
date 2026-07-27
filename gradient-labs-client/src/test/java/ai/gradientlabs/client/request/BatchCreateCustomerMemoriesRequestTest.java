package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.Memory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BatchCreateCustomerMemoriesRequestTest {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void serializesAllFields() throws Exception {
        BatchCreateCustomerMemoriesRequest request = BatchCreateCustomerMemoriesRequest.builder()
                .memories(List.of(
                        Memory.builder()
                                .externalId("order_123")
                                .customType("order")
                                .createdAt(Instant.parse("2026-07-01T10:00:00Z"))
                                .data(Map.of("title", "Order #1", "amount", 42))
                                .build(),
                        Memory.builder()
                                .externalId("order_456")
                                .createdAt(Instant.parse("2026-07-02T10:00:00Z"))
                                .data(Map.of("title", "Order #2"))
                                .build()))
                .build();

        JsonNode json = objectMapper.valueToTree(request);

        assertTrue(json.get("memories").isArray());
        assertEquals(2, json.get("memories").size());

        JsonNode first = json.get("memories").get(0);
        assertEquals("order_123", first.get("external_id").asText());
        assertEquals("order", first.get("custom_type").asText());
        assertEquals("Order #1", first.get("data").get("title").asText());
        assertEquals(42, first.get("data").get("amount").asInt());
        assertTrue(first.has("created_at"));
    }

    @Test
    void omitsCustomTypeWhenNotSet() throws Exception {
        BatchCreateCustomerMemoriesRequest request = BatchCreateCustomerMemoriesRequest.builder()
                .memories(List.of(Memory.builder()
                        .externalId("order_123")
                        .createdAt(Instant.parse("2026-07-01T10:00:00Z"))
                        .data(Map.of("title", "Order #1"))
                        .build()))
                .build();

        JsonNode json = objectMapper.valueToTree(request);

        assertFalse(json.get("memories").get(0).has("custom_type"));
    }

    @Test
    void requiresNonEmptyMemories() {
        assertThrows(IllegalStateException.class, () -> BatchCreateCustomerMemoriesRequest.builder()
                .memories(List.of())
                .build());
    }

    @Test
    void requiresExternalId() {
        assertThrows(IllegalStateException.class, () -> Memory.builder()
                .createdAt(Instant.parse("2026-07-01T10:00:00Z"))
                .data(Map.of("title", "Order #1"))
                .build());
    }

    @Test
    void requiresCreatedAt() {
        assertThrows(IllegalStateException.class, () -> Memory.builder()
                .externalId("order_123")
                .data(Map.of("title", "Order #1"))
                .build());
    }

    @Test
    void requiresData() {
        assertThrows(IllegalStateException.class, () -> Memory.builder()
                .externalId("order_123")
                .createdAt(Instant.parse("2026-07-01T10:00:00Z"))
                .build());
    }
}
