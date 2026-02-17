package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represents the way a customer is getting in touch.
 * <p>
 * The channel is used to determine how the agent formats responses.
 */
public enum Channel {
    /**
     * Live chat conversation (e.g., instant messaging).
     */
    CHAT("web"),

    /**
     * Email conversation.
     */
    EMAIL("email");

    private final String value;

    Channel(String value) {
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
