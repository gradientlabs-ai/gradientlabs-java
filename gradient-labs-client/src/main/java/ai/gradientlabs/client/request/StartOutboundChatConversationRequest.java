package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.CustomerSupportPlatformIdentifier;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Parameters for starting an outbound live chat conversation.
 * <p>
 * This kicks off a proactive conversation where your AI agent initiates contact with a
 * customer over chat.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StartOutboundChatConversationRequest {

    @JsonProperty("customer_id")
    private final String customerId;

    @JsonProperty("customer_support_platform_identifiers")
    private final List<CustomerSupportPlatformIdentifier> customerSupportPlatformIdentifiers;

    @JsonProperty("procedure_id")
    private final String procedureId;

    @JsonProperty("support_platform")
    private final String supportPlatform;

    @JsonProperty("body")
    private final String body;

    @JsonProperty("resources")
    private final Map<String, Object> resources;

    private StartOutboundChatConversationRequest(Builder builder) {
        this.customerId = builder.customerId;
        this.customerSupportPlatformIdentifiers = builder.customerSupportPlatformIdentifiers;
        this.procedureId = builder.procedureId;
        this.supportPlatform = builder.supportPlatform;
        this.body = builder.body;
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

    public String getSupportPlatform() {
        return supportPlatform;
    }

    public String getBody() {
        return body;
    }

    public Map<String, Object> getResources() {
        return resources;
    }

    /**
     * The support platforms an outbound chat can be delivered on.
     */
    public static class SupportPlatform {
        public static final String INTERCOM = "intercom";

        /**
         * Delivers the conversation to your own webhook endpoint.
         */
        public static final String PUBLIC_API = "public-api";
    }

    public static class Builder {
        private String customerId;
        private List<CustomerSupportPlatformIdentifier> customerSupportPlatformIdentifiers;
        private String procedureId;
        private String supportPlatform;
        private String body;
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
         * match against customers created via those platforms' native integrations. The
         * platform named in {@code supportPlatform} needs an identifier here, unless the
         * customer already carries one from an earlier conversation.
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
         * in this conversation. The procedure must be of type "outbound", must be live
         * (deployed), and must be enabled for the chat channel.
         *
         * @param procedureId the procedure ID
         * @return this builder
         */
        public Builder procedureId(String procedureId) {
            this.procedureId = procedureId;
            return this;
        }

        /**
         * Sets the support platform the chat is delivered on (required).
         * <p>
         * Use constants from {@link SupportPlatform}.
         *
         * @param supportPlatform the support platform
         * @return this builder
         */
        public Builder supportPlatform(String supportPlatform) {
            this.supportPlatform = supportPlatform;
            return this;
        }

        /**
         * Sets the content of the initial message to send to the customer (optional).
         * <p>
         * If omitted, the AI agent will generate an appropriate opening message based on the
         * procedure.
         *
         * @param body the body
         * @return this builder
         */
        public Builder body(String body) {
            this.body = body;
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
        public StartOutboundChatConversationRequest build() {
            if (customerId == null || customerId.isBlank()) {
                throw new IllegalArgumentException("customerId is required");
            }
            if (procedureId == null || procedureId.isBlank()) {
                throw new IllegalArgumentException("procedureId is required");
            }
            if (supportPlatform == null || supportPlatform.isBlank()) {
                throw new IllegalArgumentException("supportPlatform is required");
            }
            return new StartOutboundChatConversationRequest(this);
        }
    }
}
