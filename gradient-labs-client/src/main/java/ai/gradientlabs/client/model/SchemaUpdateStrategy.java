package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represents how the new schema should be applied.
 */
public enum SchemaUpdateStrategy {
    /**
     * Merges the inferred schema with the existing schema, preserving existing fields and adding new ones.
     */
    MERGE("merge"),

    /**
     * Completely replaces the existing schema with the newly inferred one.
     */
    REPLACE("replace");

    private final String value;

    SchemaUpdateStrategy(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
