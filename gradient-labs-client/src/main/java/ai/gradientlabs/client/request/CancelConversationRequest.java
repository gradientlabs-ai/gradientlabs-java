package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * Parameters for cancelling a conversation.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CancelConversationRequest {

    @JsonProperty("timestamp")
    private final Instant timestamp;

    @JsonProperty("reason")
    private final String reason;

    private CancelConversationRequest(Builder builder) {
        this.timestamp = builder.timestamp;
        this.reason = builder.reason;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static CancelConversationRequest empty() {
        return builder().build();
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getReason() {
        return reason;
    }

    public static class Builder {
        private Instant timestamp;
        private String reason;

        private Builder() {
        }

        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public CancelConversationRequest build() {
            return new CancelConversationRequest(this);
        }
    }
}
