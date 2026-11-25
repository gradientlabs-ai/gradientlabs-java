package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * One allowed value for a tool parameter.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ToolParameterOption {

    @JsonProperty("value")
    private String value;

    @JsonProperty("text")
    private String text;

    /**
     * Default constructor for Jackson.
     */
    public ToolParameterOption() {
    }

    /**
     * Creates a new instance.
     *
     * @param value the value passed to the tool.
     * @param text the text the AI agent sees for this option.
     */
    public ToolParameterOption(String value, String text) {
        this.value = value;
        this.text = text;
    }

    /**
     * Returns the value passed to the tool.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value passed to the tool.
     *
     * @param value the value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Returns the text the AI agent sees for this option.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text the AI agent sees for this option.
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
    }
}
