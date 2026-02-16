package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Describes whether a note is draft, live, or deleted.
 */
public enum NoteStatus {
    /**
     * The note is being written or edited and is not published.
     */
    DRAFT("draft"),

    /**
     * The note is published and available.
     */
    LIVE("live"),

    /**
     * The note has been deleted.
     */
    DELETED("deleted");

    private final String value;

    NoteStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
