package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Describes who can view the given item (e.g., help article or topic).
 */
public enum Visibility {
    /**
     * The item is available to the general public.
     * For example, it is published on a public website.
     */
    PUBLIC("public"),

    /**
     * The item is only available to the company's customers.
     * For example, it is only accessible via an app after sign-up.
     */
    USERS("users"),

    /**
     * The item is only available to the company's employees.
     * For example, it is a procedure or SOP that customers do not have access to.
     */
    INTERNAL("internal");

    private final String value;

    Visibility(String value) {
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
