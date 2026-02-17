package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Defines how the resource is fetched.
 */
public class SourceConfig {

    @JsonProperty("source_id")
    private String sourceId;

    @JsonProperty("attributes")
    private List<String> attributes;

    @JsonProperty("cache")
    private String cache;

    /**
     * Default constructor for Jackson.
     */
    public SourceConfig() {
    }

    /**
     * Constructor with all fields.
     */
    public SourceConfig(String sourceId, List<String> attributes, String cache) {
        this.sourceId = sourceId;
        this.attributes = attributes;
        this.cache = cache;
    }

    /**
     * Gets the source ID which should be used to fetch the resource data.
     *
     * @return the source ID
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * Sets the source ID.
     *
     * @param sourceId the source ID
     */
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * Gets the top-level field names to be used from the source data.
     *
     * @return the attributes list
     */
    public List<String> getAttributes() {
        return attributes;
    }

    /**
     * Sets the attributes.
     *
     * @param attributes the attributes list
     */
    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    /**
     * Gets the cache duration string (e.g. "5m") or "never".
     *
     * @return the cache value
     */
    public String getCache() {
        return cache;
    }

    /**
     * Sets the cache value.
     *
     * @param cache the cache value
     */
    public void setCache(String cache) {
        this.cache = cache;
    }
}
