package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * Parameters for finishing a conversation.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FinishConversationRequest {

    @JsonProperty("reason")
    private final String reason;

    @JsonProperty("reason_code")
    private final String reasonCode;

    @JsonProperty("timestamp")
    private final Instant timestamp;

    private FinishConversationRequest(Builder builder) {
        this.timestamp = builder.timestamp;
        this.reason = builder.reason;
        this.reasonCode = builder.reasonCode;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static FinishConversationRequest empty() {
        return builder().build();
    }

    public String getReason() {
        return reason;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    /**
     * Returns when the conversation finished. Defaults to now.
     *
     * @return the timestamp
     */
    public Instant getTimestamp() {
        return timestamp;
    }

    public static class Builder {
        private Instant timestamp;
        private String reason;
        private String reasonCode;

        private Builder() {
        }

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder reasonCode(String reasonCode) {
            this.reasonCode = reasonCode;
            return this;
        }

        /**
         * Sets when the conversation finished. Defaults to now.
         *
         * @param timestamp the timestamp
         * @return this builder
         */
        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public FinishConversationRequest build() {
            return new FinishConversationRequest(this);
        }
    }
}
