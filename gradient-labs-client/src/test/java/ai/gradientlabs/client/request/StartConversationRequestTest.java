package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.Channel;
import ai.gradientlabs.client.model.CustomerSupportPlatformIdentifier;
import ai.gradientlabs.client.model.CustomerSupportPlatformIdentifierType;
import ai.gradientlabs.client.model.SupportPlatform;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class StartConversationRequestTest {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void omitsCustomerSupportPlatformIdentifiersWhenNotSet() throws Exception {
        StartConversationRequest request = StartConversationRequest.builder()
                .id("conv-1")
                .customerId("user-1")
                .channel(Channel.CHAT)
                .build();

        JsonNode json = objectMapper.valueToTree(request);

        assertFalse(json.has("customer_support_platform_identifiers"));
    }

    @Test
    void serializesCustomerSupportPlatformIdentifiersWithAndWithoutType() throws Exception {
        StartConversationRequest request = StartConversationRequest.builder()
                .id("conv-1")
                .customerId("user-1")
                .channel(Channel.CHAT)
                .addCustomerSupportPlatformIdentifier(new CustomerSupportPlatformIdentifier(
                        SupportPlatform.INTERCOM,
                        CustomerSupportPlatformIdentifierType.INTERCOM_USER,
                        "6953e162a988d9ef0f73ef9b"))
                .addCustomerSupportPlatformIdentifier(new CustomerSupportPlatformIdentifier(
                        SupportPlatform.FRESHDESK,
                        "12345"))
                .build();

        JsonNode json = objectMapper.valueToTree(request);
        JsonNode identifiers = json.get("customer_support_platform_identifiers");

        assertEquals(2, identifiers.size());

        JsonNode intercomIdentifier = identifiers.get(0);
        assertEquals("intercom", intercomIdentifier.get("support_platform").asText());
        assertEquals("intercom_user", intercomIdentifier.get("type").asText());
        assertEquals("6953e162a988d9ef0f73ef9b", intercomIdentifier.get("value").asText());

        JsonNode freshdeskIdentifier = identifiers.get(1);
        assertEquals("freshdesk", freshdeskIdentifier.get("support_platform").asText());
        assertEquals("12345", freshdeskIdentifier.get("value").asText());
        assertFalse(freshdeskIdentifier.has("type"));
    }

    @Test
    void roundTripsThroughDeserialization() throws Exception {
        StartConversationRequest request = StartConversationRequest.builder()
                .id("conv-1")
                .customerId("user-1")
                .channel(Channel.CHAT)
                .addCustomerSupportPlatformIdentifier(new CustomerSupportPlatformIdentifier(
                        SupportPlatform.ZENDESK,
                        CustomerSupportPlatformIdentifierType.ZENDESK_SUPPORT_USER,
                        "42"))
                .build();

        String json = objectMapper.writeValueAsString(request);
        JsonNode tree = objectMapper.readTree(json);
        List<CustomerSupportPlatformIdentifier> identifiers = objectMapper.convertValue(
                tree.get("customer_support_platform_identifiers"),
                objectMapper.getTypeFactory().constructCollectionType(List.class, CustomerSupportPlatformIdentifier.class));

        assertEquals(1, identifiers.size());
        assertEquals(SupportPlatform.ZENDESK, identifiers.get(0).getSupportPlatform());
        assertEquals(CustomerSupportPlatformIdentifierType.ZENDESK_SUPPORT_USER, identifiers.get(0).getType());
        assertEquals("42", identifiers.get(0).getValue());
        assertEquals(1, request.getCustomerSupportPlatformIdentifiers().size());
    }
}
