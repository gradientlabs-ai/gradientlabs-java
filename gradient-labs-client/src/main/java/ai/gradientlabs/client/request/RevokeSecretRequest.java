package ai.gradientlabs.client.request;

/**
 * Parameters for revoking (deleting) a secret.
 * <p>
 * Requires a Management API key.
 */
public class RevokeSecretRequest {

    private final String name;

    private RevokeSecretRequest(Builder builder) {
        this.name = builder.name;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets the unique identifier for the secret to revoke.
     *
     * @return the secret name
     */
    public String getName() {
        return name;
    }

    public static class Builder {
        private String name;

        private Builder() {
        }

        /**
         * Sets the unique identifier for the secret to revoke (required).
         * <p>
         * This will permanently delete the secret and cannot be undone.
         *
         * @param name the secret name
         * @return this builder
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Builds the request.
         *
         * @return a new request instance
         * @throws IllegalStateException if required fields are missing
         */
        public RevokeSecretRequest build() {
            if (name == null || name.isBlank()) {
                throw new IllegalStateException("name is required");
            }
            return new RevokeSecretRequest(this);
        }
    }
}
