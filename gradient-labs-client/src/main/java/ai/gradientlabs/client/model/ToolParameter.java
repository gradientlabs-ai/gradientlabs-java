package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * An input parameter of a tool.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ToolParameter {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("type")
    private ToolParameterType type;

    @JsonProperty("required")
    private Boolean required;

    @JsonProperty("options")
    private List<ToolParameterOption> options;

    @JsonProperty("allowed_sources")
    private List<ToolParameterSource> allowedSources;

    @JsonProperty("transfer_mode")
    private AttachmentTransferMode transferMode;

    /**
     * Default constructor for Jackson.
     */
    public ToolParameter() {
    }

    /**
     * Returns the parameter name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the parameter name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns what the parameter is for.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets what the parameter is for.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the parameter type.
     *
     * @return the type
     */
    public ToolParameterType getType() {
        return type;
    }

    /**
     * Sets the parameter type.
     *
     * @param type the type
     */
    public void setType(ToolParameterType type) {
        this.type = type;
    }

    /**
     * Returns whether the parameter is required.
     *
     * @return the required
     */
    public Boolean getRequired() {
        return required;
    }

    /**
     * Sets whether the parameter is required.
     *
     * @param required the required
     */
    public void setRequired(Boolean required) {
        this.required = required;
    }

    /**
     * Returns the allowed values, or null if any value is allowed.
     *
     * @return the options
     */
    public List<ToolParameterOption> getOptions() {
        return options;
    }

    /**
     * Sets the allowed values, or null if any value is allowed.
     *
     * @param options the options
     */
    public void setOptions(List<ToolParameterOption> options) {
        this.options = options;
    }

    /**
     * Returns where the parameter value may come from.
     *
     * @return the allowed sources
     */
    public List<ToolParameterSource> getAllowedSources() {
        return allowedSources;
    }

    /**
     * Sets where the parameter value may come from.
     *
     * @param allowedSources the allowed sources
     */
    public void setAllowedSources(List<ToolParameterSource> allowedSources) {
        this.allowedSources = allowedSources;
    }

    /**
     * Returns how an attachment parameter is sent to the tool.
     *
     * @return the transfer mode
     */
    public AttachmentTransferMode getTransferMode() {
        return transferMode;
    }

    /**
     * Sets how an attachment parameter is sent to the tool.
     *
     * @param transferMode the transfer mode
     */
    public void setTransferMode(AttachmentTransferMode transferMode) {
        this.transferMode = transferMode;
    }
}
