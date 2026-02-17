package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.ResourceHTTPDefinition;
import ai.gradientlabs.client.model.ResourceWebhookDefinition;
import ai.gradientlabs.client.model.SourceType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Parameters for updating an existing resource source.
 * <p>
 * All fields except id are optional. If a field is not provided, its value will not be changed.
 * Note: source_type cannot be updated once set. To change the source_type, you must create a new resource source.
 * When updating http_config or webhook_config, the entire object must be provided.
 * <p>
 * Requires a Management API key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateResourceSourceRequest {

    private final String id;

    @JsonProperty("display_name")
    private final String displayName;

    @JsonProperty("description")
    private final String description;

    @JsonProperty("source_type")
    private final SourceType sourceType;

    @JsonProperty("http_config")
    private final ResourceHTTPDefinition httpConfig;

    @JsonProperty("webhook_config")
    private final ResourceWebhookDefinition webhookConfig;

    @JsonProperty("attribute_descriptions")
    private final Map<String, String> attributeDescriptions;

    @JsonProperty("schema")
    private final Object schema;

    private UpdateResourceSourceRequest(Builder builder) {
        this.id = builder.id;
        this.displayName = builder.displayName;
        this.description = builder.description;
        this.sourceType = builder.sourceType;
        this.httpConfig = builder.httpConfig;
        this.webhookConfig = builder.webhookConfig;
        this.attributeDescriptions = builder.attributeDescriptions;
        this.schema = builder.schema;
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

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public ResourceHTTPDefinition getHttpConfig() {
        return httpConfig;
    }

    public ResourceWebhookDefinition getWebhookConfig() {
        return webhookConfig;
    }

    public Map<String, String> getAttributeDescriptions() {
        return attributeDescriptions;
    }

    public Object getSchema() {
        return schema;
    }

    public static class Builder {
        private String id;
        private String displayName;
        private String description;
        private SourceType sourceType;
        private ResourceHTTPDefinition httpConfig;
        private ResourceWebhookDefinition webhookConfig;
        private Map<String, String> attributeDescriptions;
        private Object schema;

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
         * Sets the human-readable name for the resource source.
         *
         * @param displayName the display name
         * @return this builder
         */
        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        /**
         * Sets the description for the resource source.
         *
         * @param description the description
         * @return this builder
         */
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the source type.
         * Note: source_type cannot be updated once set.
         *
         * @param sourceType the source type
         * @return this builder
         */
        public Builder sourceType(SourceType sourceType) {
            this.sourceType = sourceType;
            return this;
        }

        /**
         * Sets the HTTP configuration.
         * When updating, the entire object must be provided.
         *
         * @param httpConfig the HTTP config
         * @return this builder
         */
        public Builder httpConfig(ResourceHTTPDefinition httpConfig) {
            this.httpConfig = httpConfig;
            return this;
        }

        /**
         * Sets the webhook configuration.
         * When updating, the entire object must be provided.
         *
         * @param webhookConfig the webhook config
         * @return this builder
         */
        public Builder webhookConfig(ResourceWebhookDefinition webhookConfig) {
            this.webhookConfig = webhookConfig;
            return this;
        }

        /**
         * Sets the attribute descriptions map.
         *
         * @param attributeDescriptions the attribute descriptions
         * @return this builder
         */
        public Builder attributeDescriptions(Map<String, String> attributeDescriptions) {
            this.attributeDescriptions = attributeDescriptions;
            return this;
        }

        /**
         * Sets the schema.
         *
         * @param schema the schema
         * @return this builder
         */
        public Builder schema(Object schema) {
            this.schema = schema;
            return this;
        }

        /**
         * Builds the request.
         *
         * @return a new request instance
         * @throws IllegalStateException if required fields are missing
         */
        public UpdateResourceSourceRequest build() {
            if (id == null || id.isBlank()) {
                throw new IllegalStateException("id is required");
            }
            return new UpdateResourceSourceRequest(this);
        }
    }
}
