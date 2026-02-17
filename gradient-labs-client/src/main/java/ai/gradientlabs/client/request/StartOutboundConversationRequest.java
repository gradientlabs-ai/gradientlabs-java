package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Parameters for starting an outbound conversation.
 * <p>
 * This kicks off a proactive conversation where your AI agent initiates
 * contact with a customer.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StartOutboundConversationRequest {

    @JsonProperty("customer_id")
    private final String customerId;

    @JsonProperty("customer_source")
    private final String customerSource;

    @JsonProperty("procedure_id")
    private final String procedureId;

    @JsonProperty("support_platform")
    private final String supportPlatform;

    @JsonProperty("channel")
    private final String channel;

    @JsonProperty("subject")
    private final String subject;

    @JsonProperty("body")
    private final String body;

    @JsonProperty("resources")
    private final Map<String, Object> resources;

    private StartOutboundConversationRequest(Builder builder) {
        this.customerId = builder.customerId;
        this.customerSource = builder.customerSource;
        this.procedureId = builder.procedureId;
        this.supportPlatform = builder.supportPlatform;
        this.channel = builder.channel;
        this.subject = builder.subject;
        this.body = builder.body;
        this.resources = builder.resources;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerSource() {
        return customerSource;
    }

    public String getProcedureId() {
        return procedureId;
    }

    public String getSupportPlatform() {
        return supportPlatform;
    }

    public String getChannel() {
        return channel;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public Map<String, Object> getResources() {
        return resources;
    }

    /**
     * Customer source constants.
     */
    public static class CustomerSource {
        public static final String INTERCOM = "intercom";
        public static final String FRESHCHAT = "freshchat";
        public static final String FRESHDESK = "freshdesk";
        public static final String PUBLIC_API = "public-api";
        public static final String SALESFORCE = "salesforce";
        public static final String ZENDESK = "zendesk";
        public static final String VOICE = "livekit";
        public static final String VOICE_TWILIO = "twilio";
        public static final String VOICE_TALKDESK = "talkdesk";
        public static final String VOICE_INTERCOM = "intercom-voice";
        public static final String WEB_APP = "web-app";
        public static final String FILE = "file";
    }

    /**
     * Support platform constants.
     */
    public static class SupportPlatform {
        public static final String FRESHCHAT = "freshchat";
        public static final String FRESHDESK = "freshdesk";
        public static final String INTERCOM = "intercom";
        public static final String PUBLIC_API = "public-api";
        public static final String SALESFORCE = "salesforce";
        public static final String ZENDESK = "zendesk";
        public static final String VOICE = "livekit";
        public static final String VOICE_TWILIO = "twilio";
        public static final String VOICE_TALKDESK = "talkdesk";
        public static final String VOICE_INTERCOM = "intercom-voice";
        public static final String WEB_APP = "web-app";
    }

    public static class Builder {
        private String customerId;
        private String customerSource;
        private String procedureId;
        private String supportPlatform;
        private String channel;
        private String subject;
        private String body;
        private Map<String, Object> resources;

        private Builder() {
        }

        /**
         * Sets the customer ID (required).
         * <p>
         * The external identifier for the customer in your support platform.
         * For Intercom, this is the external ID you've defined for the user (e.g., "user-123456").
         * For other platforms, this is the customer identifier used by that platform.
         *
         * @param customerId the customer ID
         * @return this builder
         */
        public Builder customerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        /**
         * Sets the customer source (required).
         * <p>
         * The source of the customer data. For example, a customer ID and phone number
         * might be from Intercom, but the outbound conversation is initiated via Twilio.
         * Use constants from {@link CustomerSource}.
         *
         * @param customerSource the customer source
         * @return this builder
         */
        public Builder customerSource(String customerSource) {
            this.customerSource = customerSource;
            return this;
        }

        /**
         * Sets the procedure ID (required).
         * <p>
         * The ID of the outbound procedure that defines what the AI agent should accomplish
         * in this conversation. The procedure must be of type "outbound" and must be live (deployed).
         *
         * @param procedureId the procedure ID
         * @return this builder
         */
        public Builder procedureId(String procedureId) {
            this.procedureId = procedureId;
            return this;
        }

        /**
         * Sets the support platform (optional).
         * <p>
         * The support platform where the conversation should be created.
         * Valid values include "intercom", "zendesk", "freshdesk", "freshchat".
         * If not provided, the system will automatically select the first connected platform
         * in priority order: intercom, zendesk, freshchat, freshdesk, public-api.
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
         * Sets the channel (optional).
         * <p>
         * Specifies the communication channel for this conversation.
         * If not provided, defaults to "email".
         * Valid values: "email", "web", "sms", "voice", etc.
         *
         * @param channel the channel
         * @return this builder
         */
        public Builder channel(String channel) {
            this.channel = channel;
            return this;
        }

        /**
         * Sets the subject (optional).
         * <p>
         * The subject line for the initial message (primarily used for email channels).
         * Only used if body is also provided. If both subject and body are omitted,
         * the AI agent will generate the initial message.
         *
         * @param subject the subject
         * @return this builder
         */
        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        /**
         * Sets the body (optional).
         * <p>
         * The content of the initial message to send to the customer.
         * If provided, this message will be sent instead of having the AI agent generate one.
         * If omitted, the AI agent will generate an appropriate initial message based on the procedure.
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
         * A JSON object containing structured data that the AI agent can use during the conversation.
         * This should be organized as a map where keys are resource type names and values are the
         * corresponding data.
         * Example: {"customer_profile": {"tier": "premium", "lifetime_value": 5000}}
         * The data will be made available to the AI agent for context during conversation processing.
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
        public StartOutboundConversationRequest build() {
            if (customerId == null || customerId.isBlank()) {
                throw new IllegalArgumentException("customerId is required");
            }
            if (customerSource == null || customerSource.isBlank()) {
                throw new IllegalArgumentException("customerSource is required");
            }
            if (procedureId == null || procedureId.isBlank()) {
                throw new IllegalArgumentException("procedureId is required");
            }
            return new StartOutboundConversationRequest(this);
        }
    }
}
