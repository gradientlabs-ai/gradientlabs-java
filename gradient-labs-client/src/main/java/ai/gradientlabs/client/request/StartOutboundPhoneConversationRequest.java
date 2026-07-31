package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.CustomerSupportPlatformIdentifier;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Parameters for placing an outbound phone call.
 * <p>
 * This kicks off a proactive conversation where your AI agent calls a customer and works
 * through an outbound procedure.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StartOutboundPhoneConversationRequest {

    @JsonProperty("customer_id")
    private final String customerId;

    @JsonProperty("customer_support_platform_identifiers")
    private final List<CustomerSupportPlatformIdentifier> customerSupportPlatformIdentifiers;

    @JsonProperty("procedure_id")
    private final String procedureId;

    @JsonProperty("to_phone_number")
    private final String toPhoneNumber;

    @JsonProperty("from_phone_number")
    private final String fromPhoneNumber;

    @JsonProperty("resources")
    private final Map<String, Object> resources;

    private StartOutboundPhoneConversationRequest(Builder builder) {
        this.customerId = builder.customerId;
        this.customerSupportPlatformIdentifiers = builder.customerSupportPlatformIdentifiers;
        this.procedureId = builder.procedureId;
        this.toPhoneNumber = builder.toPhoneNumber;
        this.fromPhoneNumber = builder.fromPhoneNumber;
        this.resources = builder.resources;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<CustomerSupportPlatformIdentifier> getCustomerSupportPlatformIdentifiers() {
        return customerSupportPlatformIdentifiers;
    }

    public String getProcedureId() {
        return procedureId;
    }

    public String getToPhoneNumber() {
        return toPhoneNumber;
    }

    public String getFromPhoneNumber() {
        return fromPhoneNumber;
    }

    public Map<String, Object> getResources() {
        return resources;
    }

    public static class Builder {
        private String customerId;
        private List<CustomerSupportPlatformIdentifier> customerSupportPlatformIdentifiers;
        private String procedureId;
        private String toPhoneNumber;
        private String fromPhoneNumber;
        private Map<String, Object> resources;

        private Builder() {
        }

        /**
         * Sets your own identifier for the customer, as used in your systems (required).
         * <p>
         * It is stored as the customer's company customer ID, and is the identifier echoed
         * back to you in tool and webhook payloads.
         *
         * @param customerId the customer ID
         * @return this builder
         */
        public Builder customerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        /**
         * Sets the customer's identifiers in third-party customer support platforms (optional).
         * <p>
         * These are added to the customer alongside {@code customerId}, and can be used to
         * match against customers created via those platforms' native integrations, and to
         * pull that platform's customer data into the call as context.
         *
         * @param customerSupportPlatformIdentifiers the customer support platform identifiers
         * @return this builder
         */
        public Builder customerSupportPlatformIdentifiers(List<CustomerSupportPlatformIdentifier> customerSupportPlatformIdentifiers) {
            this.customerSupportPlatformIdentifiers = customerSupportPlatformIdentifiers;
            return this;
        }

        /**
         * Adds a single customer support platform identifier.
         *
         * @param customerSupportPlatformIdentifier the identifier to add
         * @return this builder
         */
        public Builder addCustomerSupportPlatformIdentifier(CustomerSupportPlatformIdentifier customerSupportPlatformIdentifier) {
            if (this.customerSupportPlatformIdentifiers == null) {
                this.customerSupportPlatformIdentifiers = new ArrayList<>();
            }
            this.customerSupportPlatformIdentifiers.add(customerSupportPlatformIdentifier);
            return this;
        }

        /**
         * Sets the procedure ID (required).
         * <p>
         * The ID of the outbound procedure that defines what the AI agent should accomplish
         * on the call. The procedure must be of type "outbound", must be live (deployed), and
         * must be enabled for the "voice" channel.
         *
         * @param procedureId the procedure ID
         * @return this builder
         */
        public Builder procedureId(String procedureId) {
            this.procedureId = procedureId;
            return this;
        }

        /**
         * Sets the customer's phone number to dial (required).
         * <p>
         * E.164 format, e.g. "+14155551234".
         *
         * @param toPhoneNumber the number to dial
         * @return this builder
         */
        public Builder toPhoneNumber(String toPhoneNumber) {
            this.toPhoneNumber = toPhoneNumber;
            return this;
        }

        /**
         * Sets the caller ID to place the call from (required).
         * <p>
         * E.164 format. It must be a phone number already provisioned for your company.
         *
         * @param fromPhoneNumber the caller ID
         * @return this builder
         */
        public Builder fromPhoneNumber(String fromPhoneNumber) {
            this.fromPhoneNumber = fromPhoneNumber;
            return this;
        }

        /**
         * Sets resources (optional).
         * <p>
         * A JSON object containing structured data that the AI agent can use during the
         * conversation. This should be organized as a map where keys are resource type names
         * and values are the corresponding data.
         * Example: {"customer_profile": {"tier": "premium", "lifetime_value": 5000}}
         *
         * @param resources the resources
         * @return this builder
         */
        public Builder resources(Map<String, Object> resources) {
            this.resources = resources;
            return this;
        }

        /**
         * Builds the request.
         *
         * @return a new request instance
         * @throws IllegalArgumentException if required fields are missing
         */
        public StartOutboundPhoneConversationRequest build() {
            if (customerId == null || customerId.isBlank()) {
                throw new IllegalArgumentException("customerId is required");
            }
            if (procedureId == null || procedureId.isBlank()) {
                throw new IllegalArgumentException("procedureId is required");
            }
            if (toPhoneNumber == null || toPhoneNumber.isBlank()) {
                throw new IllegalArgumentException("toPhoneNumber is required");
            }
            if (fromPhoneNumber == null || fromPhoneNumber.isBlank()) {
                throw new IllegalArgumentException("fromPhoneNumber is required");
            }
            return new StartOutboundPhoneConversationRequest(this);
        }
    }
}
