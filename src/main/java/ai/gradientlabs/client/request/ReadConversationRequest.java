package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for reading a conversation.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReadConversationRequest {

    @JsonProperty("include_messages")
    private final Boolean includeMessages;

    private ReadConversationRequest(Builder builder) {
        this.includeMessages = builder.includeMessages;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static ReadConversationRequest empty() {
        return builder().build();
    }

    public Boolean getIncludeMessages() {
        return includeMessages;
    }

    public static class Builder {
        private Boolean includeMessages;

        private Builder() {
        }

        public Builder includeMessages(boolean includeMessages) {
            this.includeMessages = includeMessages;
            return this;
        }

        public ReadConversationRequest build() {
            return new ReadConversationRequest(this);
        }
    }
}
