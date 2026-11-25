package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The publication status of an article or topic.
 */
public enum PublicationStatus {
    /**
     * Not yet published.
     */
    DRAFT("draft"),

    /**
     * Published.
     */
    PUBLISHED("published"),

    /**
     * Deleted. Only valid for articles.
     */
    DELETED("deleted"),

    /**
     * Excluded from use by the AI agent. Only valid for articles.
     */
    EXCLUDED("excluded");

    private final String value;

    PublicationStatus(String value) {
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
