package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The status of a back office task.
 */
public enum BackOfficeTaskStatus {

    PENDING("pending"),
    IN_PROGRESS("in-progress"),
    COMPLETED("completed"),
    FAILED("failed"),
    HANDED_OFF("handed-off");

    private final String value;

    BackOfficeTaskStatus(String value) {
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
