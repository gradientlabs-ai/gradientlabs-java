package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The status of a procedure.
 */
public enum ProcedureStatus {
    /**
     * The procedure has never been saved.
     */
    UNSAVED("unsaved"),

    /**
     * The procedure is a draft and not used by the AI agent.
     */
    DRAFT("draft"),

    /**
     * The procedure is live and used by the AI agent.
     */
    LIVE("live"),

    /**
     * The procedure has been archived.
     */
    ARCHIVED("archived");

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
