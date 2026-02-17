package ai.gradientlabs.client.request;

/**
 * Parameters for deleting a resource source.
 * <p>
 * Requires a Management API key.
 */
public class DeleteResourceSourceRequest {

    private final String id;

    private DeleteResourceSourceRequest(Builder builder) {
        this.id = builder.id;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets the unique identifier for the resource source to delete.
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
         * Sets the unique identifier for the resource source to delete (required).
         * <p>
         * This will permanently delete the resource source and cannot be undone.
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
        public DeleteResourceSourceRequest build() {
            if (id == null || id.isBlank()) {
                throw new IllegalStateException("id is required");
            }
            return new DeleteResourceSourceRequest(this);
        }
    }
}
