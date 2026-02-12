package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for upserting (inserting or updating) a hand-off target.
 * <p>
 * Requires a Management API key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpsertHandOffTargetRequest {

    @JsonProperty("id")
    private final String id;

    @JsonProperty("name")
    private final String name;

    private UpsertHandOffTargetRequest(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        private String id;
        private String name;

        private Builder() {
        }

        /**
         * Sets the unique identifier for this hand-off target (required).
         * <p>
         * This will be sent back in the {@code conversation.hand_off} webhook event.
         * Can consist of letters, numbers, or any of the following characters: _ - + =
         *
         * @param id the hand-off target ID
         * @return this builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the human-friendly name for this hand-off target (required).
         *
         * @param name the hand-off target name
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
        public UpsertHandOffTargetRequest build() {
            if (id == null || id.isBlank()) {
                throw new IllegalStateException("id is required");
            }
            if (name == null || name.isBlank()) {
                throw new IllegalStateException("name is required");
            }
            return new UpsertHandOffTargetRequest(this);
        }
    }
}
