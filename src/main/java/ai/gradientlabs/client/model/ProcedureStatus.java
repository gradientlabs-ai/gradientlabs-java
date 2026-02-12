package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Status of a procedure.
 * <p>
 * Procedures can be in draft mode (saved but not used in real conversations)
 * or live mode (actively used in conversations).
 */
public enum ProcedureStatus {
    /**
     * The procedure has been saved as a draft, but won't be used in real
     * conversations until it is promoted to live.
     */
    DRAFT("draft"),

    /**
     * The procedure is live and will be used in real conversations.
     */
    LIVE("live");

    private final String value;

    ProcedureStatus(String value) {
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
