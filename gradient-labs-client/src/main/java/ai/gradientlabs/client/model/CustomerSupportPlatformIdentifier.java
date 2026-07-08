package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Links a customer to their record in a third-party customer support platform.
 * <p>
 * {@code type} is only meaningful (and only validated) for platforms that have more than one
 * kind of identifier: intercom, zendesk, and salesforce. Omit it entirely for freshchat and
 * freshdesk, which don't have subtypes.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerSupportPlatformIdentifier {

    @JsonProperty("support_platform")
    private SupportPlatform supportPlatform;

    @JsonProperty("type")
    private CustomerSupportPlatformIdentifierType type;

    @JsonProperty("value")
    private String value;

    /**
     * Default constructor for Jackson.
     */
    public CustomerSupportPlatformIdentifier() {
    }

    /**
     * Creates a new identifier for a platform that doesn't have subtypes (e.g. freshchat, freshdesk).
     *
     * @param supportPlatform the support platform
     * @param value           the external ID in that platform
     */
    public CustomerSupportPlatformIdentifier(SupportPlatform supportPlatform, String value) {
        this.supportPlatform = supportPlatform;
        this.value = value;
    }

    /**
     * Creates a new identifier with a subtype (required for intercom, zendesk, and salesforce).
     *
     * @param supportPlatform the support platform
     * @param type            the kind of identifier
     * @param value           the external ID in that platform
     */
    public CustomerSupportPlatformIdentifier(SupportPlatform supportPlatform, CustomerSupportPlatformIdentifierType type, String value) {
        this.supportPlatform = supportPlatform;
        this.type = type;
        this.value = value;
    }

    public SupportPlatform getSupportPlatform() {
        return supportPlatform;
    }

    public void setSupportPlatform(SupportPlatform supportPlatform) {
        this.supportPlatform = supportPlatform;
    }

    public CustomerSupportPlatformIdentifierType getType() {
        return type;
    }

    public void setType(CustomerSupportPlatformIdentifierType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CustomerSupportPlatformIdentifier{" +
                "supportPlatform=" + supportPlatform +
                ", type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
