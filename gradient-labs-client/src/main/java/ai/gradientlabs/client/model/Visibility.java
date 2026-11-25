package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Who can see an article or topic.
 */
public enum Visibility {
    /**
     * Anyone can see it.
     */
    PUBLIC("public"),

    /**
     * Only signed-in users can see it.
     */
    USERS("users"),

    /**
     * Only your staff can see it.
     */
    INTERNAL("internal"),

    /**
     * The support platform did not say. Only returned when reading topics.
     */
    UNKNOWN("unknown");

    private final String value;

    Visibility(String value) {
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
