package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An argument passed to a tool execution.
 */
public class Argument {

    @JsonProperty("name")
    private String name;

    @JsonProperty("value")
    private String value;

    /**
     * Creates a new argument.
     */
    public Argument() {
    }

    /**
     * Creates a new argument with the given name and value.
     *
     * @param name  the parameter name
     * @param value the parameter value
     */
    public Argument(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Gets the parameter name.
     *
     * @return the parameter name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the parameter name.
     *
     * @param name the parameter name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the parameter value.
     * <p>
     * This is a string here, but it will be converted to the appropriate type
     * when the tool is called.
     *
     * @return the parameter value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the parameter value.
     *
     * @param value the parameter value
     */
    public void setValue(String value) {
        this.value = value;
    }
}
