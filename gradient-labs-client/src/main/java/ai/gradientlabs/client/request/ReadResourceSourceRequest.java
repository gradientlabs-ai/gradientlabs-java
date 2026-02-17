package ai.gradientlabs.client.request;

/**
 * Parameters for reading a specific resource source.
 * <p>
 * Requires a Management API key.
 */
public class ReadResourceSourceRequest {

    private final String id;

    private ReadResourceSourceRequest(Builder builder) {
        this.id = builder.id;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets the unique identifier for the resource source to read.
     *
     * @return the resource source ID
     */
    public String getId() {
        return id;
    }

    public static class Builder {
        private String id;

        private Builder() {
        }

        /**
         * Sets the unique identifier for the resource source to read (required).
         *
         * @param id the resource source ID
         * @return this builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Builds the request.
         *
         * @return a new request instance
         * @throws IllegalStateException if required fields are missing
         */
        public ReadResourceSourceRequest build() {
            if (id == null || id.isBlank()) {
                throw new IllegalStateException("id is required");
            }
            return new ReadResourceSourceRequest(this);
        }
    }
}
