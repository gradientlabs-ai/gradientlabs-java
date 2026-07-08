package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * A third-party customer support platform.
 */
public enum SupportPlatform {
    /**
     * Intercom.
     */
    INTERCOM("intercom"),

    /**
     * Zendesk.
     */
    ZENDESK("zendesk"),

    /**
     * Salesforce.
     */
    SALESFORCE("salesforce"),

    /**
     * Freshchat.
     */
    FRESHCHAT("freshchat"),

    /**
     * Freshdesk.
     */
    FRESHDESK("freshdesk");

    private final String value;

    SupportPlatform(String value) {
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
