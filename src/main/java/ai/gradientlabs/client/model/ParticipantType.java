package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The type of participant in a conversation.
 */
public enum ParticipantType {
    /**
     * The customer.
     */
    CUSTOMER("customer"),

    /**
     * A human agent.
     */
    HUMAN("human"),

    /**
     * The AI agent.
     */
    AI_AGENT("ai_agent");

    private final String value;

    ParticipantType(String value) {
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
