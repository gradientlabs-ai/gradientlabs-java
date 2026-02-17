package ai.gradientlabs.client.webhook;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The type of webhook event.
 */
public enum WebhookType {
    /**
     * The agent wants to send the customer a message.
     */
    AGENT_MESSAGE("agent.message"),

    /**
     * The agent is escalating or handing the conversation off to a human agent.
     */
    CONVERSATION_HANDOFF("conversation.hand_off"),

    /**
     * The agent has concluded the conversation with the customer.
     */
    CONVERSATION_FINISHED("conversation.finished"),

    /**
     * The agent needs an action to be executed (e.g., while following a procedure).
     */
    ACTION_EXECUTE("action.execute"),

    /**
     * The agent wants to pull a resource.
     */
    RESOURCE_PULL("resource.pull");

    private final String value;

    WebhookType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
