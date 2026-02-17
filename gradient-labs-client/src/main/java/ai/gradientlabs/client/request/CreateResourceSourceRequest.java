package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.ResourceHTTPDefinition;
import ai.gradientlabs.client.model.ResourceWebhookDefinition;
import ai.gradientlabs.client.model.SourceType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Parameters for creating a resource source.
 * <p>
 * Requires a Management API key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateResourceSourceRequest {

    @JsonProperty("display_name")
    private final String displayName;

    @JsonProperty("source_type")
    private final SourceType sourceType;

    @JsonProperty("description")
    private final String description;

    @JsonProperty("http_config")
    private final ResourceHTTPDefinition httpConfig;

    @JsonProperty("webhook_config")
    private final ResourceWebhookDefinition webhookConfig;

    @JsonProperty("attribute_descriptions")
    private final Map<String, String> attributeDescriptions;

    private CreateResourceSourceRequest(Builder builder) {
        this.displayName = builder.displayName;
        this.sourceType = builder.sourceType;
        this.description = builder.description;
        this.httpConfig = builder.httpConfig;
        this.webhookConfig = builder.webhookConfig;
        this.attributeDescriptions = builder.attributeDescriptions;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getDisplayName() {
        return displayName;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public String getDescription() {
        return description;
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

    public static class Builder {
        private String displayName;
        private SourceType sourceType;
        private String description;
        private ResourceHTTPDefinition httpConfig;
        private ResourceWebhookDefinition webhookConfig;
        private Map<String, String> attributeDescriptions;

        private Builder() {
        }

        /**
         * Sets the human-readable name for the resource source (required).
         *
         * @param displayName the display name
         * @return this builder
         */
        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        /**
         * Sets the source type describing how the data is fetched (required).
         *
         * @param sourceType the source type
         * @return this builder
         */
        public Builder sourceType(SourceType sourceType) {
            this.sourceType = sourceType;
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
         * Sets the HTTP configuration. Required if source_type is HTTP.
         *
         * @param httpConfig the HTTP config
         * @return this builder
         */
        public Builder httpConfig(ResourceHTTPDefinition httpConfig) {
            this.httpConfig = httpConfig;
            return this;
        }

        /**
         * Sets the webhook configuration. Required if source_type is WEBHOOK.
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
         * Builds the request.
         *
         * @return a new request instance
         * @throws IllegalStateException if required fields are missing
         */
        public CreateResourceSourceRequest build() {
            if (displayName == null || displayName.isBlank()) {
                throw new IllegalStateException("displayName is required");
            }
            if (sourceType == null) {
                throw new IllegalStateException("sourceType is required");
            }
            return new CreateResourceSourceRequest(this);
        }
    }
}
