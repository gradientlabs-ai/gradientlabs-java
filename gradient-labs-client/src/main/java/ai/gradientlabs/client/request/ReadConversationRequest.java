package ai.gradientlabs.client.request;

/**
 * Optional parameters for reading a conversation.
 */
public class ReadConversationRequest {

    private final String supportPlatform;

    private ReadConversationRequest(Builder builder) {
        this.supportPlatform = builder.supportPlatform;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static ReadConversationRequest empty() {
        return builder().build();
    }

    /**
     * Returns the support platform the conversation ID belongs to, or null for the public API.
     *
     * @return the support platform
     */
    public String getSupportPlatform() {
        return supportPlatform;
    }

    public static class Builder {
        private String supportPlatform;

        private Builder() {
        }

        /**
         * Reads a conversation by its ID on another support platform, such as {@code intercom},
         * instead of its public API ID.
         *
         * @param supportPlatform the support platform
         * @return this builder
         */
        public Builder supportPlatform(String supportPlatform) {
            this.supportPlatform = supportPlatform;
            return this;
        }

        public ReadConversationRequest build() {
            return new ReadConversationRequest(this);
        }
    }
}
