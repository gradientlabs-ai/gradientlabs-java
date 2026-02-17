package ai.gradientlabs.client.request;

/**
 * Parameters for deleting a resource type.
 * <p>
 * Requires a Management API key.
 */
public class DeleteResourceTypeRequest {

    private final String id;

    private DeleteResourceTypeRequest(Builder builder) {
        this.id = builder.id;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets the unique identifier for the resource type to delete.
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
         * Sets the unique identifier for the resource type to delete (required).
         * <p>
         * This will permanently delete the resource type and cannot be undone.
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
        public DeleteResourceTypeRequest build() {
            if (id == null || id.isBlank()) {
                throw new IllegalStateException("id is required");
            }
            return new DeleteResourceTypeRequest(this);
        }
    }
}
