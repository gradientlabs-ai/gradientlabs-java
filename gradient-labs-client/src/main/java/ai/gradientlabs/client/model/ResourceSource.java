package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.time.Instant;
import java.util.Map;

/**
 * Represents a resource source in the system.
 */
public class ResourceSource {

    @JsonProperty("id")
    private String id;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("description")
    private String description;

    @JsonProperty("source_type")
    private SourceType sourceType;

    @JsonProperty("http_config")
    private ResourceHTTPDefinition httpConfig;

    @JsonProperty("webhook_config")
    private ResourceWebhookDefinition webhookConfig;

    @JsonProperty("attribute_descriptions")
    private Map<String, String> attributeDescriptions;

    @JsonProperty("schema")
    private Object schema;

    @JsonProperty("created")
    private Instant created;

    @JsonProperty("updated")
    private Instant updated;

    @JsonProperty("available_refresh_strategies")
    private List<RefreshStrategy> availableRefreshStrategies;

    @JsonProperty("available_scopes")
    private List<Scope> availableScopes;

    @JsonProperty("managed")
    private ManagedResourceSource managed;

    /**
     * Default constructor for Jackson.
     */
    public ResourceSource() {
    }

    /**
     * Gets the unique identifier for the resource source.
     *
     * @return the resource source ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID.
     *
     * @param id the resource source ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the human-readable name for the resource source.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the display name.
     *
     * @param displayName the display name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the description of the resource source.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the source type describing how the data is fetched.
     *
     * @return the source type
     */
    public SourceType getSourceType() {
        return sourceType;
    }

    /**
     * Sets the source type.
     *
     * @param sourceType the source type
     */
    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * Gets the HTTP configuration.
     *
     * @return the HTTP config
     */
    public ResourceHTTPDefinition getHttpConfig() {
        return httpConfig;
    }

    /**
     * Sets the HTTP configuration.
     *
     * @param httpConfig the HTTP config
     */
    public void setHttpConfig(ResourceHTTPDefinition httpConfig) {
        this.httpConfig = httpConfig;
    }

    /**
     * Gets the webhook configuration.
     *
     * @return the webhook config
     */
    public ResourceWebhookDefinition getWebhookConfig() {
        return webhookConfig;
    }

    /**
     * Sets the webhook configuration.
     *
     * @param webhookConfig the webhook config
     */
    public void setWebhookConfig(ResourceWebhookDefinition webhookConfig) {
        this.webhookConfig = webhookConfig;
    }

    /**
     * Gets the attribute descriptions map.
     *
     * @return the attribute descriptions
     */
    public Map<String, String> getAttributeDescriptions() {
        return attributeDescriptions;
    }

    /**
     * Sets the attribute descriptions.
     *
     * @param attributeDescriptions the attribute descriptions
     */
    public void setAttributeDescriptions(Map<String, String> attributeDescriptions) {
        this.attributeDescriptions = attributeDescriptions;
    }

    /**
     * Gets the schema of the resource source.
     *
     * @return the schema
     */
    public Object getSchema() {
        return schema;
    }

    /**
     * Sets the schema.
     *
     * @param schema the schema
     */
    public void setSchema(Object schema) {
        this.schema = schema;
    }

    /**
     * Gets the timestamp when the resource source was created.
     *
     * @return the created timestamp
     */
    public Instant getCreated() {
        return created;
    }

    /**
     * Sets the created timestamp.
     *
     * @param created the created timestamp
     */
    public void setCreated(Instant created) {
        this.created = created;
    }

    /**
     * Gets the timestamp when the resource source was last updated.
     *
     * @return the updated timestamp
     */
    public Instant getUpdated() {
        return updated;
    }

    /**
     * Sets the updated timestamp.
     *
     * @param updated the updated timestamp
     */
    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    /**
     * Returns the refresh strategies resource types using this source may choose.
     *
     * @return the available refresh strategies
     */
    public List<RefreshStrategy> getAvailableRefreshStrategies() {
        return availableRefreshStrategies;
    }

    /**
     * Returns the scopes resource types using this source may choose.
     *
     * @return the available scopes
     */
    public List<Scope> getAvailableScopes() {
        return availableScopes;
    }

    /**
     * Returns details of the managed source, if Gradient Labs manages it.
     *
     * @return the managed
     */
    public ManagedResourceSource getManaged() {
        return managed;
    }
}
