package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for reading a topic.
 * <p>
 * <strong>Note:</strong> Requires a Management API key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReadTopicRequest {

    @JsonProperty("support_platform")
    private final String supportPlatform;

    private ReadTopicRequest(Builder builder) {
        this.supportPlatform = builder.supportPlatform;
    }

    /**
     * Gets the support platform.
     *
     * @return the support platform, or null to use the default ("public-api")
     */
    public String getSupportPlatform() {
        return supportPlatform;
    }

    /**
     * Creates a new builder for ReadTopicRequest.
     *
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Creates an empty request.
     *
     * @return an empty request
     */
    public static ReadTopicRequest empty() {
        return new Builder().build();
    }

    public static class Builder {
        private String supportPlatform;

        /**
         * Sets the support platform to read from.
         * <p>
         * Valid values include "public-api" and "intercom". If not provided,
         * defaults to "public-api". This allows reading topics from
         * different support platforms within the same organization.
         *
         * @param supportPlatform the support platform
         * @return this builder
         */
        public Builder supportPlatform(String supportPlatform) {
            this.supportPlatform = supportPlatform;
            return this;
        }

        /**
         * Builds the request.
         *
         * @return the built request
         */
        public ReadTopicRequest build() {
            return new ReadTopicRequest(this);
        }
    }
}
