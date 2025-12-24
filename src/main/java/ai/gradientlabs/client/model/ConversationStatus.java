package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Describes the current state of a conversation.
 */
public enum ConversationStatus {
    /**
     * The agent is following the conversation but not participating
     * (e.g., when it has been handed-off to a human).
     */
    OBSERVING("observing"),

    /**
     * The agent is actively participating in the conversation.
     */
    ACTIVE("active"),

    /**
     * The conversation has been prematurely brought to a close
     * (e.g., because a human has taken it over) and the AI agent
     * can no longer participate in it.
     */
    CANCELLED("cancelled"),

    /**
     * The conversation has been closed because the customer's
     * issue has been resolved.
     */
    FINISHED("finished"),

    /**
     * The agent encountered an irrecoverable error, such as not
     * being able to deliver a message to your webhook endpoint
     * after multiple retries.
     */
    FAILED("failed");

    private final String value;

    ConversationStatus(String value) {
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
