package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The category of a customer memory.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemoryCustomType {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    /**
     * Default constructor for Jackson.
     */
    public MemoryCustomType() {
    }

    /**
     * Creates a new instance.
     *
     * @param name the category name. {@code support_conversation} is reserved for Gradient Labs.
     * @param description a description of the category, or null.
     */
    public MemoryCustomType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Returns the category name. {@code support_conversation} is reserved for Gradient Labs.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the category name. {@code support_conversation} is reserved for Gradient Labs.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a description of the category, or null.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets a description of the category, or null.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
