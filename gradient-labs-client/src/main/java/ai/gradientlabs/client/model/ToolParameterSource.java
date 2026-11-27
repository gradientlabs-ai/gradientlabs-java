package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Where the value of a tool parameter may come from.
 */
public enum ToolParameterSource {
    /**
     * The AI agent chooses the value.
     */
    LLM("llm"),

    /**
     * A fixed value set in a procedure.
     */
    LITERAL("literal"),

    /**
     * A value read from a resource.
     */
    RESOURCE("resource");

    private final String value;

    ToolParameterSource(String value) {
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
