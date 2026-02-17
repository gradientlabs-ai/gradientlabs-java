package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for deleting a hand-off target.
 * <p>
 * This will fail if the hand-off target is in use - either in a procedure or in an intent.
 * <p>
 * Requires a Management API key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeleteHandOffTargetRequest {

    @JsonProperty("id")
    private final String id;

    private DeleteHandOffTargetRequest(Builder builder) {
        this.id = builder.id;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public static class Builder {
        private String id;

        private Builder() {
        }

        /**
         * Sets the unique identifier for the hand-off target to delete (required).
         *
         * @param id the hand-off target ID
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
        public DeleteHandOffTargetRequest build() {
            if (id == null || id.isBlank()) {
                throw new IllegalStateException("id is required");
            }
            return new DeleteHandOffTargetRequest(this);
        }
    }
}
