package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The type of a tool parameter.
 */
public enum ToolParameterType {
    /**
     * A string.
     */
    STRING("string"),

    /**
     * A list of strings.
     */
    STRING_ARRAY("string_array"),

    /**
     * A JSON value.
     */
    JSON("json"),

    /**
     * A whole number.
     */
    INTEGER("integer"),

    /**
     * A decimal number.
     */
    FLOAT("float"),

    /**
     * True or false.
     */
    BOOLEAN("boolean"),

    /**
     * A date.
     */
    DATE("date"),

    /**
     * A date and time.
     */
    TIMESTAMP("timestamp"),

    /**
     * A duration.
     */
    DURATION("duration"),

    /**
     * A file attached to the conversation.
     */
    ATTACHMENT("attachment");

    private final String value;

    ToolParameterType(String value) {
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
