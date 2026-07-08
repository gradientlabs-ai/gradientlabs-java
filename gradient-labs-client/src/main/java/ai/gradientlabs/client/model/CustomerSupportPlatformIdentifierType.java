package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The kind of identifier a {@link CustomerSupportPlatformIdentifier} represents, for
 * platforms that have more than one kind of customer identifier.
 */
public enum CustomerSupportPlatformIdentifierType {
    /**
     * An Intercom lead ID.
     */
    INTERCOM_LEAD("intercom_lead"),

    /**
     * An Intercom user ID.
     */
    INTERCOM_USER("intercom_user"),

    /**
     * A Zendesk end-user ID from a messaging conversation.
     */
    ZENDESK_CONVERSATION_USER("zendesk_conversation_user"),

    /**
     * A Zendesk support (ticketing) user ID.
     */
    ZENDESK_SUPPORT_USER("zendesk_support_user"),

    /**
     * A Salesforce Contact ID.
     */
    SALESFORCE_CONTACT_ID("salesforce_contact_id"),

    /**
     * A Salesforce Account ID.
     */
    SALESFORCE_ACCOUNT_ID("salesforce_account_id");

    private final String value;

    CustomerSupportPlatformIdentifierType(String value) {
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
