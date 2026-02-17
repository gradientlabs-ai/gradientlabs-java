package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Determines when in the conversation the resource is fetched and used.
 */
public enum Scope {
    /**
     * The resource is available throughout the conversation and in all procedures.
     */
    GLOBAL("global"),

    /**
     * The resource is available only in procedures that explicitly use it, only fetched when it's used.
     */
    LOCAL("local");

    private final String value;

    Scope(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
