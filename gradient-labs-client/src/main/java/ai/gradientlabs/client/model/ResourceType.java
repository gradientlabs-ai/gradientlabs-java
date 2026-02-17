package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * Represents a resource type configuration.
 */
public class ResourceType {

    @JsonProperty("id")
    private String id;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("description")
    private String description;

    @JsonProperty("scope")
    private Scope scope;

    @JsonProperty("refresh_strategy")
    private RefreshStrategy refreshStrategy;

    @JsonProperty("source_config")
    private SourceConfig sourceConfig;

    @JsonProperty("schema")
    private Schema schema;

    @JsonProperty("is_enabled")
    private Boolean isEnabled;

    @JsonProperty("created")
    private Instant created;

    @JsonProperty("updated")
    private Instant updated;

    /**
     * Default constructor for Jackson.
     */
    public ResourceType() {
    }

    /**
     * Gets the unique identifier for the resource type.
     *
     * @return the resource type ID
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the human-readable name for the resource type.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets the optional description of the resource type.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the scope determining when in the conversation the resource is fetched and used.
     *
     * @return the scope
     */
    public Scope getScope() {
        return scope;
    }

    /**
     * Gets the refresh strategy determining how often the resource is re-fetched.
     *
     * @return the refresh strategy
     */
    public RefreshStrategy getRefreshStrategy() {
        return refreshStrategy;
    }

    /**
     * Gets the optional configuration for how the resource is fetched.
     *
     * @return the source config, or null if none
     */
    public SourceConfig getSourceConfig() {
        return sourceConfig;
    }

    /**
     * Gets the optional schema describing the structure of the data.
     *
     * @return the schema, or null if none
     */
    public Schema getSchema() {
        return schema;
    }

    /**
     * Gets whether the resource type is enabled.
     *
     * @return true if enabled, false otherwise
     */
    public Boolean getIsEnabled() {
        return isEnabled;
    }

    /**
     * Gets when the resource type was created.
     *
     * @return the creation time
     */
    public Instant getCreated() {
        return created;
    }

    /**
     * Gets when the resource type was last updated.
     *
     * @return the last update time
     */
    public Instant getUpdated() {
        return updated;
    }

    @Override
    public String toString() {
        return "ResourceType{" +
                "id='" + id + '\'' +
                ", displayName='" + displayName + '\'' +
                ", scope=" + scope +
                ", refreshStrategy=" + refreshStrategy +
                ", isEnabled=" + isEnabled +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
