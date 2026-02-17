package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Determines how often the resource is re-fetched.
 */
public enum RefreshStrategy {
    /**
     * The resource value can change, so this is re-fetched throughout the conversation.
     */
    DYNAMIC("dynamic"),

    /**
     * The resource is fetched once at the start of the conversation (global) or when it's first used in a procedure (local).
     */
    STATIC("static");

    private final String value;

    RefreshStrategy(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
