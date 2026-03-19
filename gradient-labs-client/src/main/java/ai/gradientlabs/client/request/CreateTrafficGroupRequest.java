package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for creating a traffic group.
 * <p>
 * <strong>Note:</strong> Requires a Management API key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateTrafficGroupRequest {

    @JsonProperty("name")
    private final String name;

    private CreateTrafficGroupRequest(Builder builder) {
        this.name = builder.name;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets the display name for the traffic group.
     *
     * @return the traffic group name
     */
    public String getName() {
        return name;
    }

    public static class Builder {
        private String name;

        private Builder() {
        }

        /**
         * Sets the display name for the traffic group (required).
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
        public CreateTrafficGroupRequest build() {
            if (name == null || name.isBlank()) {
                throw new IllegalStateException("name is required");
            }
            return new CreateTrafficGroupRequest(this);
        }
    }
}
