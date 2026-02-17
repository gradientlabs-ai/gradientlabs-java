package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Describes the publication status of a help article or topic.
 */
public enum PublicationStatus {
    /**
     * The article or topic is being written or edited and is not published.
     */
    DRAFT("draft"),

    /**
     * The article or topic is published.
     */
    PUBLISHED("published");

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
