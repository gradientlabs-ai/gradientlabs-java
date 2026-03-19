package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for updating a traffic group.
 * <p>
 * <strong>Note:</strong> Requires a Management API key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateTrafficGroupRequest {

    @JsonIgnore
    private final String id;

    @JsonProperty("name")
    private final String name;

    private UpdateTrafficGroupRequest(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets the unique identifier of the traffic group to update.
     *
     * @return the traffic group ID
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the new display name for the traffic group.
     *
     * @return the traffic group name
     */
    public String getName() {
        return name;
    }

    public static class Builder {
        private String id;
        private String name;

        private Builder() {
        }

        /**
         * Sets the traffic group ID (required).
         *
         * @param id the traffic group ID
         * @return this builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the new display name for the traffic group (required).
         *
         * @param name the traffic group name
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
        public UpdateTrafficGroupRequest build() {
            if (id == null || id.isBlank()) {
                throw new IllegalStateException("id is required");
            }
            if (name == null || name.isBlank()) {
                throw new IllegalStateException("name is required");
            }
            return new UpdateTrafficGroupRequest(this);
        }
    }
}
