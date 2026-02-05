package ai.gradientlabs.client.webhook.event;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Event data for a {@code resource.pull} webhook.
 * <p>
 * Indicates the agent wants to pull a resource.
 */
public class ResourcePullEvent {

    @JsonProperty("resource_type")
    private String resourceType;

    @JsonProperty("conversation")
    private WebhookConversation conversation;

    /**
     * Default constructor for Jackson.
     */
    public ResourcePullEvent() {
    }

    /**
     * Returns the name of the resource type the agent wants to pull.
     *
     * @return the resource type
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * Returns the conversation details.
     *
     * @return the conversation
     */
    public WebhookConversation getConversation() {
        return conversation;
    }

    @Override
    public String toString() {
        return "ResourcePullEvent{" +
                "resourceType='" + resourceType + '\'' +
                ", conversation=" + conversation +
                '}';
    }
}
