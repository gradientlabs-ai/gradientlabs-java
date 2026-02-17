package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Describes how the data is fetched from the source.
 */
public enum SourceType {
    /**
     * The source fetches data via HTTP requests.
     */
    HTTP("http"),

    /**
     * The source is provided by the system.
     */
    INTERNAL("internal"),

    /**
     * The source receives data via webhooks.
     */
    WEBHOOK("webhook");

    private final String value;

    SourceType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
