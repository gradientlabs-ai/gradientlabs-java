package ai.gradientlabs.client.webhook.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Event data for an {@code action.execute} webhook.
 * <p>
 * Sent when the agent calls a webhook tool or reads a webhook resource source.
 */
public class ActionExecuteEvent {

    @JsonProperty("action")
    private String action;

    @JsonProperty("params")
    private JsonNode params;

    @JsonProperty("conversation")
    private WebhookConversation conversation;

    @JsonProperty("back_office_task")
    private WebhookBackOfficeTask backOfficeTask;

    /**
     * Default constructor for Jackson.
     */
    public ActionExecuteEvent() {
    }

    /**
     * Returns the name of the action to execute.
     *
     * @return the action name
     */
    public String getAction() {
        return action;
    }

    /**
     * Returns the arguments to execute the action with.
     * <p>
     * This is a raw JSON node that can be deserialized to the appropriate type.
     *
     * @return the parameters
     */
    public JsonNode getParams() {
        return params;
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
     * Returns the back-office task the action is for. Null when it is for a conversation.
     *
     * @return the back office task
     */
    public WebhookBackOfficeTask getBackOfficeTask() {
        return backOfficeTask;
    }

    @Override
    public String toString() {
        return "ActionExecuteEvent{" +
                "action='" + action + '\'' +
                ", conversation=" + conversation +
                '}';
    }
}
