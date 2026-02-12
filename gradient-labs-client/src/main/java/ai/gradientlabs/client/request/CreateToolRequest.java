package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Parameters for creating a tool.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateToolRequest {

    @JsonProperty("name")
    private final String name;

    @JsonProperty("description")
    private final String description;

    @JsonProperty("schema")
    private final Map<String, Object> schema;

    private CreateToolRequest(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.schema = builder.schema;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String description;
        private Map<String, Object> schema;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder schema(Map<String, Object> schema) {
            this.schema = schema;
            return this;
        }

        public CreateToolRequest build() {
            if (name == null || name.isBlank()) {
                throw new IllegalStateException("name is required");
            }
            return new CreateToolRequest(this);
        }
    }
}
