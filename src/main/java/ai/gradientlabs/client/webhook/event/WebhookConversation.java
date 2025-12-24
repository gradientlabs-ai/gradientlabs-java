package ai.gradientlabs.client.webhook.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Contains the details of the conversation a webhook relates to.
 */
public class WebhookConversation {

    @JsonProperty("id")
    private String id;

    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("metadata")
    private Map<String, Object> metadata;

    /**
     * Default constructor for Jackson.
     */
    public WebhookConversation() {
    }

    /**
     * Returns your chosen unique identifier for this conversation.
     *
     * @return the conversation ID
     */
    public String getId() {
        return id;
    }

    /**
     * Returns your chosen identifier for the customer.
     *
     * @return the customer ID
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Returns metadata you attached to the conversation when starting it.
     *
     * @return the metadata, or null if none
     */
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    @Override
    public String toString() {
        return "WebhookConversation{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                '}';
    }
}
