package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for listing topics.
 * <p>
 * <strong>Note:</strong> Requires a Management API key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListTopicsRequest {

    @JsonProperty("support_platform")
    private final String supportPlatform;

    private ListTopicsRequest(Builder builder) {
        this.supportPlatform = builder.supportPlatform;
    }

    /**
     * Gets the support platform filter.
     *
     * @return the support platform to filter by, or null to use the default ("public-api")
     */
    public String getSupportPlatform() {
        return supportPlatform;
    }

    /**
     * Creates a new builder for ListTopicsRequest.
     *
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Creates an empty request for listing all topics.
     *
     * @return an empty request
     */
    public static ListTopicsRequest empty() {
        return new Builder().build();
    }

    public static class Builder {
        private String supportPlatform;

        /**
         * Sets the support platform to filter by.
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
        public ListTopicsRequest build() {
            return new ListTopicsRequest(this);
        }
    }
}
