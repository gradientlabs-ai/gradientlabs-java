package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for creating a knowledge base.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateKnowledgeBaseRequest {

    @JsonProperty("id")
    private final String id;

    @JsonProperty("name")
    private final String name;

    private CreateKnowledgeBaseRequest(Builder builder) {
        this.id = builder.id;
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
     * Returns your identifier for the knowledge base.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the knowledge base's name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Builder for {@link CreateKnowledgeBaseRequest}.
     */
    public static class Builder {
        private String id;
        private String name;

        private Builder() {
        }

        /**
         * Sets your identifier for the knowledge base. Required.
         *
         * @param id the id
         * @return this builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the knowledge base's name. Required.
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
        public CreateKnowledgeBaseRequest build() {
            if (id == null || id.isBlank()) {
                throw new IllegalStateException("id is required");
            }
            if (name == null || name.isBlank()) {
                throw new IllegalStateException("name is required");
            }
            return new CreateKnowledgeBaseRequest(this);
        }
    }
}
