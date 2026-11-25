package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The kind of identifier a customer has on a support platform.
 */
public enum CustomerSupportPlatformIdentifierType {
    /**
     * An Intercom lead.
     */
    INTERCOM_LEAD("intercom_lead"),

    /**
     * An Intercom user.
     */
    INTERCOM_USER("intercom_user"),

    /**
     * A Zendesk conversations (messaging) user.
     */
    ZENDESK_CONVERSATION_USER("zendesk_conversation_user"),

    /**
     * A Zendesk Support user.
     */
    ZENDESK_SUPPORT_USER("zendesk_support_user"),

    /**
     * A Salesforce contact ID.
     */
    SALESFORCE_CONTACT_ID("salesforce_contact_id"),

    /**
     * A Salesforce account ID.
     */
    SALESFORCE_ACCOUNT_ID("salesforce_account_id"),

    /**
     * The email address on a Salesforce contact.
     */
    SALESFORCE_CONTACT_EMAIL("salesforce_contact_email"),

    /**
     * An email address supplied on a Salesforce case.
     */
    SALESFORCE_SUPPLIED_EMAIL("salesforce_supplied_email");

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
