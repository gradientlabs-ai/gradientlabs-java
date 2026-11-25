package ai.gradientlabs.client.webhook.event;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Legacy resource event. Resource fetches use {@link ActionExecuteEvent}.
 * <p>
 * Indicates the agent wants to pull a resource.
 */
@Deprecated
public class ResourcePullEvent {

    @JsonProperty("resource_type")
    private String resourceType;

    @JsonProperty("conversation")
    private WebhookConversation conversation;

    @JsonProperty("back_office_task")
    private WebhookBackOfficeTask backOfficeTask;

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

    /**
     * Returns the back-office task the resource is read for. Null when it is read for a conversation.
     *
     * @return the back office task
     */
    public WebhookBackOfficeTask getBackOfficeTask() {
        return backOfficeTask;
    }

    @Override
    public String toString() {
        return "ResourcePullEvent{" +
                "resourceType='" + resourceType + '\'' +
                ", conversation=" + conversation +
                '}';
    }
}
