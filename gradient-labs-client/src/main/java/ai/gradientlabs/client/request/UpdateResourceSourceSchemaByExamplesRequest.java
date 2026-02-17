package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.SchemaUpdateStrategy;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Parameters for updating a resource source schema by examples.
 * <p>
 * This endpoint allows you to automatically generate or update a resource source schema by providing
 * example data payloads. Instead of manually defining the JSON schema structure, you send representative
 * examples of the data your resource source returns, and the system automatically infers the schema from
 * these examples.
 * <p>
 * Requires a Management API key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateResourceSourceSchemaByExamplesRequest {

    private final String id;

    @JsonProperty("examples")
    private final List<Object> examples;

    @JsonProperty("schema_update_strategy")
    private final SchemaUpdateStrategy schemaUpdateStrategy;

    private UpdateResourceSourceSchemaByExamplesRequest(Builder builder) {
        this.id = builder.id;
        this.examples = builder.examples;
        this.schemaUpdateStrategy = builder.schemaUpdateStrategy;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets the unique identifier for the resource source to update.
     * <p>
     * Note: This is used as a path parameter, not included in the request body.
     *
     * @return the resource source ID
     */
    public String getId() {
        return id;
    }

    public List<Object> getExamples() {
        return examples;
    }

    public SchemaUpdateStrategy getSchemaUpdateStrategy() {
        return schemaUpdateStrategy;
    }

    public static class Builder {
        private String id;
        private List<Object> examples;
        private SchemaUpdateStrategy schemaUpdateStrategy;

        private Builder() {
        }

        /**
         * Sets the unique identifier for the resource source to update (required).
         *
         * @param id the resource source ID
         * @return this builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the array of example data payloads (required).
         * These examples represent the structure of the data your resource source returns.
         *
         * @param examples the examples list
         * @return this builder
         */
        public Builder examples(List<Object> examples) {
            this.examples = examples;
            return this;
        }

        /**
         * Sets the schema update strategy controlling how the new schema is applied.
         * Defaults to MERGE if not specified.
         *
         * @param schemaUpdateStrategy the schema update strategy
         * @return this builder
         */
        public Builder schemaUpdateStrategy(SchemaUpdateStrategy schemaUpdateStrategy) {
            this.schemaUpdateStrategy = schemaUpdateStrategy;
            return this;
        }

        /**
         * Builds the request.
         *
         * @return a new request instance
         * @throws IllegalStateException if required fields are missing
         */
        public UpdateResourceSourceSchemaByExamplesRequest build() {
            if (id == null || id.isBlank()) {
                throw new IllegalStateException("id is required");
            }
            if (examples == null || examples.isEmpty()) {
                throw new IllegalStateException("examples is required and cannot be empty");
            }
            return new UpdateResourceSourceSchemaByExamplesRequest(this);
        }
    }
}
