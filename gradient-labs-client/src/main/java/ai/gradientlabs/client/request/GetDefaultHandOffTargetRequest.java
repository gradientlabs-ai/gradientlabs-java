package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.Channel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for getting the default hand-off target for a channel.
 * <p>
 * Gets the current default hand-off target that the AI agent will use when handing off
 * the conversation, if there is no specific target for that intent or procedure.
 * <p>
 * Requires a Management API key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetDefaultHandOffTargetRequest {

    @JsonProperty("channel")
    private final Channel channel;

    private GetDefaultHandOffTargetRequest(Builder builder) {
        this.channel = builder.channel;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Channel getChannel() {
        return channel;
    }

    public static class Builder {
        private Channel channel;

        private Builder() {
        }

        /**
         * Sets the conversation channel for which to get the default hand-off target (required).
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
        public GetDefaultHandOffTargetRequest build() {
            if (channel == null) {
                throw new IllegalStateException("channel is required");
            }
            return new GetDefaultHandOffTargetRequest(this);
        }
    }
}
