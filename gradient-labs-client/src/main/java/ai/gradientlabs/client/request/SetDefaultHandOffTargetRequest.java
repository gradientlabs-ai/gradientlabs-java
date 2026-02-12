package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.Channel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for setting the default hand-off target for a channel.
 * <p>
 * Sets the default hand-off target that the AI agent will use when handing off
 * the conversation, if there is no specific target for that intent or procedure.
 * <p>
 * Requires a Management API key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SetDefaultHandOffTargetRequest {

    @JsonProperty("id")
    private final String id;

    @JsonProperty("channel")
    private final Channel channel;

    private SetDefaultHandOffTargetRequest(Builder builder) {
        this.id = builder.id;
        this.channel = builder.channel;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public Channel getChannel() {
        return channel;
    }

    public static class Builder {
        private String id;
        private Channel channel;

        private Builder() {
        }

        /**
         * Sets the unique identifier for the hand-off target to set as default (required).
         * <p>
         * This should match an existing hand-off target's ID.
         * Set to empty string to clear the default.
         *
         * @param id the hand-off target ID (or empty string to clear)
         * @return this builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the conversation channel for which to set the default hand-off target (required).
         *
         * @param channel the channel
         * @return this builder
         */
        public Builder channel(Channel channel) {
            this.channel = channel;
            return this;
        }

        /**
         * Builds the request.
         *
         * @return a new request instance
         * @throws IllegalStateException if required fields are missing
         */
        public SetDefaultHandOffTargetRequest build() {
            if (id == null) {
                throw new IllegalStateException("id is required (use empty string to clear default)");
            }
            if (channel == null) {
                throw new IllegalStateException("channel is required");
            }
            return new SetDefaultHandOffTargetRequest(this);
        }
    }
}
