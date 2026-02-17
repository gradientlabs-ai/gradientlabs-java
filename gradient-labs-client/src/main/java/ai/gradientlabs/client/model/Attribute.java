package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Describes a single attribute in the resource schema.
 */
public class Attribute {

    @JsonProperty("path")
    private String path;

    @JsonProperty("type")
    private String type;

    @JsonProperty("cardinality")
    private String cardinality;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("is_root")
    private Boolean isRoot;

    /**
     * Default constructor for Jackson.
     */
    public Attribute() {
    }

    /**
     * Gets the JSON path to the attribute.
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Gets the data type of the attribute.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets whether the attribute can have one value ("one") or multiple values ("many").
     *
     * @return the cardinality
     */
    public String getCardinality() {
        return cardinality;
    }

    /**
     * Gets the name of the attribute (the last part of the path).
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the optional description of the attribute.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets whether the attribute is a top-level field in the resource data.
     *
     * @return true if root, false otherwise
     */
    public Boolean getIsRoot() {
        return isRoot;
    }
}
