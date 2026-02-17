package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.RefreshMechanismHTTP;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * Parameters for writing (creating or updating) a secret.
 * <p>
 * Requires a Management API key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WriteSecretRequest {

    private final String name;

    @JsonProperty("value")
    private final String value;

    @JsonProperty("expiry")
    private final Instant expiry;

    @JsonProperty("refresh_mechanism_http")
    private final RefreshMechanismHTTP refreshMechanismHttp;

    private WriteSecretRequest(Builder builder) {
        this.name = builder.name;
        this.value = builder.value;
        this.expiry = builder.expiry;
        this.refreshMechanismHttp = builder.refreshMechanismHttp;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets the unique identifier for the secret.
     * <p>
     * Note: This is used as a path parameter, not included in the request body.
     *
     * @return the secret name
     */
    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Instant getExpiry() {
        return expiry;
    }

    public RefreshMechanismHTTP getRefreshMechanismHttp() {
        return refreshMechanismHttp;
    }

    public static class Builder {
        private String name;
        private String value;
        private Instant expiry;
        private RefreshMechanismHTTP refreshMechanismHttp;

        private Builder() {
        }

        /**
         * Sets the unique identifier for the secret (required).
         * <p>
         * This will be used as the secret's name and must be unique within your organization.
         *
         * @param name the secret name
         * @return this builder
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the secret value to store (required).
         * <p>
         * This is the sensitive data you want to securely store, such as an API key or password.
         *
         * @param value the secret value
         * @return this builder
         */
        public Builder value(String value) {
            this.value = value;
            return this;
        }

        /**
         * Sets the optional expiration time for the secret.
         * <p>
         * After this time, the secret will no longer be available.
         *
         * @param expiry the expiration time, or null for no expiration
         * @return this builder
         */
        public Builder expiry(Instant expiry) {
            this.expiry = expiry;
            return this;
        }

        /**
         * Sets the optional configuration for automatically refreshing the secret value.
         * <p>
         * This is commonly used for OAuth access tokens that need periodic renewal.
         *
         * @param refreshMechanismHttp the refresh mechanism, or null for no automatic refresh
         * @return this builder
         */
        public Builder refreshMechanismHttp(RefreshMechanismHTTP refreshMechanismHttp) {
            this.refreshMechanismHttp = refreshMechanismHttp;
            return this;
        }

        /**
         * Builds the request.
         *
         * @return a new request instance
         * @throws IllegalStateException if required fields are missing
         */
        public WriteSecretRequest build() {
            if (name == null || name.isBlank()) {
                throw new IllegalStateException("name is required");
            }
            if (value == null || value.isBlank()) {
                throw new IllegalStateException("value is required");
            }
            return new WriteSecretRequest(this);
        }
    }
}
