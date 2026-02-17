package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Describes whether an article is available for use by the AI agent.
 */
public enum UsageStatus {
    /**
     * The article is available for use by the AI agent.
     */
    ON("on"),

    /**
     * The article is not available for use by the AI agent.
     */
    OFF("off");

    private final String value;

    UsageStatus(String value) {
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
