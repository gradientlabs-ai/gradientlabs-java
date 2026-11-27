package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for renaming a knowledge base.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateKnowledgeBaseRequest {

    @JsonProperty("name")
    private final String name;

    private UpdateKnowledgeBaseRequest(Builder builder) {
        this.name = builder.name;
    }

    /**
     * Returns a new builder.
     *
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the knowledge base's new name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Builder for {@link UpdateKnowledgeBaseRequest}.
     */
    public static class Builder {
        private String name;

        private Builder() {
        }

        /**
         * Sets the knowledge base's new name. Required.
         *
         * @param name the name
         * @return this builder
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Builds the request.
         *
         * @return the request
         * @throws IllegalStateException if a required field is missing
         */
        public UpdateKnowledgeBaseRequest build() {
            if (name == null || name.isBlank()) {
                throw new IllegalStateException("name is required");
            }
            return new UpdateKnowledgeBaseRequest(this);
        }
    }
}
