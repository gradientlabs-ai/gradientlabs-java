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
     * The agent is starting an outbound conversation and wants to send the first message.
     */
    CONVERSATION_START("conversation.start"),

    /**
     * The agent needs an action to be executed (e.g., while following a procedure).
     */
    ACTION_EXECUTE("action.execute"),

    /**
     * @deprecated Resource fetches use {@link #ACTION_EXECUTE}.
     */
    @Deprecated
    RESOURCE_PULL("resource.pull"),

    /**
     * A back-office task has completed.
     */
    BACK_OFFICE_TASK_COMPLETE("back-office-task.complete"),

    /**
     * A back-office task has been handed off to a human.
     */
    BACK_OFFICE_TASK_HAND_OFF("back-office-task.hand-off"),

    /**
     * A back-office task has failed.
     */
    BACK_OFFICE_TASK_FAIL("back-office-task.fail"),

    /**
     * An event type this version of the client does not know. Its data is the raw JSON.
     */
    UNKNOWN("unknown");

    private final String value;

    WebhookType(String value) {
        this.value = value;
    }

    /**
     * Returns the type with the given wire value, or {@link #UNKNOWN} if there is none.
     *
     * @param value the event type as sent in the webhook
     * @return the matching type
     */
    public static WebhookType fromValue(String value) {
        for (WebhookType type : values()) {
            if (type != UNKNOWN && type.value.equals(value)) {
                return type;
            }
        }
        return UNKNOWN;
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
