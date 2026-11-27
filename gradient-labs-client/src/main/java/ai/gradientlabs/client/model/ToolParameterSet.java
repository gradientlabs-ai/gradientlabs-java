package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A group of parameters that applies when a discriminator parameter has a given value.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ToolParameterSet {

    @JsonProperty("discriminator_parameter_name")
    private String discriminatorParameterName;

    @JsonProperty("discriminator_value")
    private String discriminatorValue;

    @JsonProperty("parameters")
    private List<ToolParameter> parameters;

    /**
     * Default constructor for Jackson.
     */
    public ToolParameterSet() {
    }

    /**
     * Creates a new instance.
     *
     * @param discriminatorParameterName the name of the discriminator parameter.
     * @param discriminatorValue the discriminator value that activates this set.
     * @param parameters the parameters in this set.
     */
    public ToolParameterSet(String discriminatorParameterName, String discriminatorValue, List<ToolParameter> parameters) {
        this.discriminatorParameterName = discriminatorParameterName;
        this.discriminatorValue = discriminatorValue;
        this.parameters = parameters;
    }

    /**
     * Returns the name of the discriminator parameter.
     *
     * @return the discriminator parameter name
     */
    public String getDiscriminatorParameterName() {
        return discriminatorParameterName;
    }

    /**
     * Sets the name of the discriminator parameter.
     *
     * @param discriminatorParameterName the discriminator parameter name
     */
    public void setDiscriminatorParameterName(String discriminatorParameterName) {
        this.discriminatorParameterName = discriminatorParameterName;
    }

    /**
     * Returns the discriminator value that activates this set.
     *
     * @return the discriminator value
     */
    public String getDiscriminatorValue() {
        return discriminatorValue;
    }

    /**
     * Sets the discriminator value that activates this set.
     *
     * @param discriminatorValue the discriminator value
     */
    public void setDiscriminatorValue(String discriminatorValue) {
        this.discriminatorValue = discriminatorValue;
    }

    /**
     * Returns the parameters in this set.
     *
     * @return the parameters
     */
    public List<ToolParameter> getParameters() {
        return parameters;
    }

    /**
     * Sets the parameters in this set.
     *
     * @param parameters the parameters
     */
    public void setParameters(List<ToolParameter> parameters) {
        this.parameters = parameters;
    }
}
