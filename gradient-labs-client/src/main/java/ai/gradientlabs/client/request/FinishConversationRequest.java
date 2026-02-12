package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for finishing a conversation.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FinishConversationRequest {

    @JsonProperty("reason")
    private final String reason;

    private FinishConversationRequest(Builder builder) {
        this.reason = builder.reason;
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

    public static class Builder {
        private String reason;

        private Builder() {
        }

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public FinishConversationRequest build() {
            return new FinishConversationRequest(this);
        }
    }
}
