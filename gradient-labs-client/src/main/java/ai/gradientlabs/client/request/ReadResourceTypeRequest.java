package ai.gradientlabs.client.request;

/**
 * Parameters for reading a specific resource type.
 * <p>
 * Requires a Management API key.
 */
public class ReadResourceTypeRequest {

    private final String id;

    private ReadResourceTypeRequest(Builder builder) {
        this.id = builder.id;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets the unique identifier for the resource type to read.
     *
     * @return the resource type ID
     */
    public String getId() {
        return id;
    }

    public static class Builder {
        private String id;

        private Builder() {
        }

        /**
         * Sets the unique identifier for the resource type to read (required).
         *
         * @param id the resource type ID
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
        public ReadResourceTypeRequest build() {
            if (id == null || id.isBlank()) {
                throw new IllegalStateException("id is required");
            }
            return new ReadResourceTypeRequest(this);
        }
    }
}
