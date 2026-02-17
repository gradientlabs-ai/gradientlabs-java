package ai.gradientlabs.client.webhook.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Event data for an {@code action.execute} webhook.
 * <p>
 * Indicates the agent needs an action to be executed (e.g., while following a procedure).
 */
public class ActionExecuteEvent {

    @JsonProperty("action")
    private String action;

    @JsonProperty("params")
    private JsonNode params;

    @JsonProperty("conversation")
    private WebhookConversation conversation;

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

    @Override
    public String toString() {
        return "ActionExecuteEvent{" +
                "action='" + action + '\'' +
                ", conversation=" + conversation +
                '}';
    }
}
