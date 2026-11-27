package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Runs a tool on a Model Context Protocol (MCP) server. Only returned when reading tools.
 */
public class ToolMcpDefinition {

    @JsonProperty("server_id")
    private String serverId;

    @JsonProperty("external_tool_name")
    private String externalToolName;

    /**
     * Default constructor for Jackson.
     */
    public ToolMcpDefinition() {
    }

    /**
     * Returns the ID of the MCP server.
     *
     * @return the server id
     */
    public String getServerId() {
        return serverId;
    }

    /**
     * Returns the name of the tool on the MCP server.
     *
     * @return the external tool name
     */
    public String getExternalToolName() {
        return externalToolName;
    }
}
