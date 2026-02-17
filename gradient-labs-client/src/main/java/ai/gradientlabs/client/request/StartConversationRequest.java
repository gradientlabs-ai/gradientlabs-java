package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.Channel;
import ai.gradientlabs.client.model.ParticipantType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Parameters for starting a conversation.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StartConversationRequest {

    @JsonProperty("id")
    private final String id;

    @JsonProperty("customer_id")
    private final String customerId;

    @JsonProperty("assignee_id")
    private final String assigneeId;

    @JsonProperty("assignee_type")
    private final ParticipantType assigneeType;

    @JsonProperty("channel")
    private final Channel channel;

    @JsonProperty("metadata")
    private final Map<String, Object> metadata;

    @JsonProperty("created")
    private final Instant created;

    @JsonProperty("resources")
    private final Map<String, Object> resources;

    @JsonProperty("conversation_token")
    private final String conversationToken;

    private StartConversationRequest(Builder builder) {
        this.id = builder.id;
        this.customerId = builder.customerId;
        this.assigneeId = builder.assigneeId;
        this.assigneeType = builder.assigneeType;
        this.channel = builder.channel;
        this.metadata = builder.metadata;
        this.created = builder.created;
        this.resources = builder.resources;
        this.conversationToken = builder.conversationToken;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public ParticipantType getAssigneeType() {
        return assigneeType;
    }

    public Channel getChannel() {
        return channel;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public Instant getCreated() {
        return created;
    }

    public Map<String, Object> getResources() {
        return resources;
    }

    public String getConversationToken() {
        return conversationToken;
    }

    public static class Builder {
        private String id;
        private String customerId;
        private String assigneeId;
        private ParticipantType assigneeType;
        private Channel channel;
        private Map<String, Object> metadata;
        private Instant created;
        private Map<String, Object> resources;
        private String conversationToken;

        private Builder() {
        }

        /**
         * Sets the conversation ID (required).
         * <p>
         * Can be anything consisting of letters, numbers, or any of the following
         * characters: _ - + =
         * <p>
         * Tip: use something meaningful to your business (e.g. a ticket number).
         *
         * @param id the conversation ID
         * @return this builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the customer ID (required).
         * <p>
         * Used to build historical context of conversations the agent has had with this customer.
         *
         * @param customerId the customer ID
         * @return this builder
         */
        public Builder customerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        /**
         * Sets who the conversation is assigned to (optional).
         *
         * @param assigneeId the assignee ID
         * @return this builder
         */
        public Builder assigneeId(String assigneeId) {
            this.assigneeId = assigneeId;
            return this;
        }

        /**
         * Sets the type of participant currently assigned to respond (optional).
         * <p>
         * Set this to {@link ParticipantType#AI_AGENT} to assign the conversation
         * to the Gradient Labs AI when starting it.
         *
         * @param assigneeType the assignee type
         * @return this builder
         */
        public Builder assigneeType(ParticipantType assigneeType) {
            this.assigneeType = assigneeType;
            return this;
        }

        /**
         * Sets the communication channel (required).
         *
         * @param channel the channel
         * @return this builder
         */
        public Builder channel(Channel channel) {
            this.channel = channel;
            return this;
        }

        /**
         * Sets arbitrary metadata (optional).
         * <p>
         * This will be passed along with webhooks and can be used as action parameters.
         *
         * @param metadata the metadata
         * @return this builder
         */
        public Builder metadata(Map<String, Object> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Adds a single metadata entry.
         *
         * @param key   the metadata key
         * @param value the metadata value
         * @return this builder
         */
        public Builder addMetadata(String key, Object value) {
            if (this.metadata == null) {
                this.metadata = new HashMap<>();
            }
            this.metadata.put(key, value);
            return this;
        }

        /**
         * Sets when the conversation started (optional).
         * <p>
         * If not given, this will default to the current time.
         *
         * @param created the creation time
         * @return this builder
         */
        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        /**
         * Sets resources available to the AI agent (optional).
         * <p>
         * An arbitrary object attached to the conversation and available to the AI agent
         * during the conversation. You can also use resources as parameters for your tools.
         *
         * @param resources the resources
         * @return this builder
         */
        public Builder resources(Map<String, Object> resources) {
            this.resources = resources;
            return this;
        }

        /**
         * Adds a single resource entry.
         *
         * @param key   the resource key
         * @param value the resource value
         * @return this builder
         */
        public Builder addResource(String key, Object value) {
            if (this.resources == null) {
                this.resources = new HashMap<>();
            }
            this.resources.put(key, value);
            return this;
        }

        /**
         * Sets a sensitive token for the conversation (optional).
         * <p>
         * The latest token will be echoed back in future webhooks under the header
         * {@code X-GradientLabs-Token}, as well as in HTTP tools using templates.
         *
         * @param conversationToken the conversation token
         * @return this builder
         */
        public Builder conversationToken(String conversationToken) {
            this.conversationToken = conversationToken;
            return this;
        }

        /**
         * Builds the request.
         *
         * @return a new request instance
         * @throws IllegalStateException if required fields are missing
         */
        public StartConversationRequest build() {
            if (id == null || id.isBlank()) {
                throw new IllegalStateException("id is required");
            }
            if (customerId == null || customerId.isBlank()) {
                throw new IllegalStateException("customerId is required");
            }
            if (channel == null) {
                throw new IllegalStateException("channel is required");
            }
            return new StartConversationRequest(this);
        }
    }
}
