package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * Describes the structure of the resource data.
 */
public class Schema {

    @JsonProperty("raw")
    private Map<String, Object> raw;

    @JsonProperty("attributes")
    private List<Attribute> attributes;

    /**
     * Default constructor for Jackson.
     */
    public Schema() {
    }

    /**
     * Gets the raw JSON schema for the resource data.
     *
     * @return the raw schema
     */
    public Map<String, Object> getRaw() {
        return raw;
    }

    /**
     * Gets the array of attribute descriptors for the resource data.
     *
     * @return the attributes list
     */
    public List<Attribute> getAttributes() {
        return attributes;
    }
}
