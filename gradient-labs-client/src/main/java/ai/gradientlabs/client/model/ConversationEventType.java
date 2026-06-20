package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Describes an event that occurred within a conversation.
 */
public enum ConversationEventType {

    INTERNAL_NOTE("internal-note"),
    DELIVERED("delivered"),
    MESSAGE_READ("read"),
    TYPING("typing");

    private final String value;

    ConversationEventType(String value) {
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
