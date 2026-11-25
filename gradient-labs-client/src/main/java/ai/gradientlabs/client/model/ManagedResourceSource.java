package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Describes a resource source that Gradient Labs manages for you.
 */
public class ManagedResourceSource {

    @JsonProperty("enabled")
    private Boolean enabled;

    @JsonProperty("key")
    private String key;

    /**
     * Default constructor for Jackson.
     */
    public ManagedResourceSource() {
    }

    /**
     * Returns whether the managed source is enabled.
     *
     * @return the enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Returns the key of the managed source.
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }
}
