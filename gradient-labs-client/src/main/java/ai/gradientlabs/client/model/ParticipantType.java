package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The type of participant in a conversation.
 */
public enum ParticipantType {
    /**
     * The customer.
     */
    CUSTOMER("Customer"),

    /**
     * A human agent.
     */
    AGENT("Agent"),

    /**
     * The Gradient Labs AI agent.
     */
    AI_AGENT("AI Agent"),

    /**
     * An automated system other than the Gradient Labs AI agent, such as a chatbot.
     */
    BOT("Bot");

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
