package ai.gradientlabs.client.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class FinishConversationRequestTest {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void serializesReasonCode() throws Exception {
        FinishConversationRequest request = FinishConversationRequest.builder()
                .reason("The customer said goodbye")
                .reasonCode("customer-ended-chat")
                .build();

        JsonNode json = objectMapper.valueToTree(request);

        assertEquals("The customer said goodbye", json.get("reason").asText());
        assertEquals("customer-ended-chat", json.get("reason_code").asText());
        assertEquals("customer-ended-chat", request.getReasonCode());
    }

    @Test
    void omitsReasonCodeWhenNotSet() throws Exception {
        FinishConversationRequest request = FinishConversationRequest.builder()
                .reason("The customer said goodbye")
                .build();

        JsonNode json = objectMapper.valueToTree(request);

        assertFalse(json.has("reason_code"));
    }
}
