package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * The response from listing tools.
 */
public class ToolsListResponse {

    @JsonProperty("tools")
    private List<Tool> tools;

    /**
     * Default constructor for Jackson.
     */
    public ToolsListResponse() {
    }

    /**
     * Returns the tools.
     *
     * @return the tools
     */
    public List<Tool> getTools() {
        return tools;
    }
}
