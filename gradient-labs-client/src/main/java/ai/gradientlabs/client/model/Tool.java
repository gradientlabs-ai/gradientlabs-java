package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A tool the AI agent can use.
 */
public class Tool {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("parameters")
    private List<ToolParameter> parameters;

    @JsonProperty("parameter_sets")
    private List<ToolParameterSet> parameterSets;

    @JsonProperty("http")
    private HTTPDefinition http;

    @JsonProperty("webhook")
    private ToolWebhookDefinition webhook;

    @JsonProperty("async")
    private ToolAsyncDefinition async;

    @JsonProperty("mcp")
    private ToolMcpDefinition mcp;

    @JsonProperty("draft")
    private Boolean draft;

    @JsonProperty("branch_id")
    private String branchId;

    @JsonProperty("branch_name")
    private String branchName;

    /**
     * Default constructor for Jackson.
     */
    public Tool() {
    }

    /**
     * Returns the tool's ID.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the tool's name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns what the tool does and when to use it.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the tool's input parameters.
     *
     * @return the parameters
     */
    public List<ToolParameter> getParameters() {
        return parameters;
    }

    /**
     * Returns conditional groups of parameters.
     *
     * @return the parameter sets
     */
    public List<ToolParameterSet> getParameterSets() {
        return parameterSets;
    }

    /**
     * Returns the HTTP request that runs the tool, if it runs over HTTP.
     *
     * @return the http
     */
    public HTTPDefinition getHttp() {
        return http;
    }

    /**
     * Returns the webhook that runs the tool, if it runs through a webhook.
     *
     * @return the webhook
     */
    public ToolWebhookDefinition getWebhook() {
        return webhook;
    }

    /**
     * Returns the asynchronous configuration, if the tool runs asynchronously.
     *
     * @return the async
     */
    public ToolAsyncDefinition getAsync() {
        return async;
    }

    /**
     * Returns the MCP server configuration, if the tool runs on an MCP server.
     *
     * @return the mcp
     */
    public ToolMcpDefinition getMcp() {
        return mcp;
    }

    /**
     * Returns whether the tool is still a draft.
     *
     * @return the draft
     */
    public Boolean getDraft() {
        return draft;
    }

    /**
     * Returns the ID of the branch the tool was created on.
     *
     * @return the branch id
     */
    public String getBranchId() {
        return branchId;
    }

    /**
     * Returns the name of the branch the tool was created on.
     *
     * @return the branch name
     */
    public String getBranchName() {
        return branchName;
    }
}
