package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.HTTPDefinition;
import ai.gradientlabs.client.model.ToolAsyncDefinition;
import ai.gradientlabs.client.model.ToolParameter;
import ai.gradientlabs.client.model.ToolParameterSet;
import ai.gradientlabs.client.model.ToolWebhookDefinition;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Parameters for creating a tool.
 * <p>
 * Set exactly one of {@code http}, {@code webhook} or {@code async}, or set {@code draft} to true and none of them.
 * Set {@code branchId} or {@code branchName}: the tool goes live when that branch is merged.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateToolRequest {

    @JsonProperty("name")
    private final String name;

    @JsonProperty("description")
    private final String description;

    @JsonProperty("parameters")
    private final List<ToolParameter> parameters;

    @JsonProperty("parameter_sets")
    private final List<ToolParameterSet> parameterSets;

    @JsonProperty("http")
    private final HTTPDefinition http;

    @JsonProperty("webhook")
    private final ToolWebhookDefinition webhook;

    @JsonProperty("async")
    private final ToolAsyncDefinition async;

    @JsonProperty("draft")
    private final Boolean draft;

    @JsonProperty("branch_id")
    private final String branchId;

    @JsonProperty("branch_name")
    private final String branchName;

    private CreateToolRequest(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.parameters = builder.parameters == null ? List.of() : builder.parameters;
        this.parameterSets = builder.parameterSets;
        this.http = builder.http;
        this.webhook = builder.webhook;
        this.async = builder.async;
        this.draft = builder.draft;
        this.branchId = builder.branchId;
        this.branchName = builder.branchName;
    }

    /**
     * Returns a new builder.
     *
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
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
     * Returns the tool's input parameters. Defaults to none.
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
     * Returns the HTTP request that runs the tool.
     *
     * @return the http
     */
    public HTTPDefinition getHttp() {
        return http;
    }

    /**
     * Returns the webhook that runs the tool.
     *
     * @return the webhook
     */
    public ToolWebhookDefinition getWebhook() {
        return webhook;
    }

    /**
     * Returns the asynchronous configuration.
     *
     * @return the async
     */
    public ToolAsyncDefinition getAsync() {
        return async;
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
     * Returns the ID of the branch to create the tool on.
     *
     * @return the branch id
     */
    public String getBranchId() {
        return branchId;
    }

    /**
     * Returns the git name of the branch to create the tool on.
     *
     * @return the branch name
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * Builder for {@link CreateToolRequest}.
     */
    public static class Builder {
        private String name;
        private String description;
        private List<ToolParameter> parameters;
        private List<ToolParameterSet> parameterSets;
        private HTTPDefinition http;
        private ToolWebhookDefinition webhook;
        private ToolAsyncDefinition async;
        private Boolean draft;
        private String branchId;
        private String branchName;

        private Builder() {
        }

        /**
         * Sets the tool's name. Required.
         *
         * @param name the name
         * @return this builder
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets what the tool does and when to use it. Required.
         *
         * @param description the description
         * @return this builder
         */
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the tool's input parameters. Defaults to none.
         *
         * @param parameters the parameters
         * @return this builder
         */
        public Builder parameters(List<ToolParameter> parameters) {
            this.parameters = parameters;
            return this;
        }

        /**
         * Sets conditional groups of parameters.
         *
         * @param parameterSets the parameter sets
         * @return this builder
         */
        public Builder parameterSets(List<ToolParameterSet> parameterSets) {
            this.parameterSets = parameterSets;
            return this;
        }

        /**
         * Sets the HTTP request that runs the tool.
         *
         * @param http the http
         * @return this builder
         */
        public Builder http(HTTPDefinition http) {
            this.http = http;
            return this;
        }

        /**
         * Sets the webhook that runs the tool.
         *
         * @param webhook the webhook
         * @return this builder
         */
        public Builder webhook(ToolWebhookDefinition webhook) {
            this.webhook = webhook;
            return this;
        }

        /**
         * Sets the asynchronous configuration.
         *
         * @param async the async
         * @return this builder
         */
        public Builder async(ToolAsyncDefinition async) {
            this.async = async;
            return this;
        }

        /**
         * Sets whether the tool is still a draft.
         *
         * @param draft the draft
         * @return this builder
         */
        public Builder draft(Boolean draft) {
            this.draft = draft;
            return this;
        }

        /**
         * Sets the ID of the branch to create the tool on.
         *
         * @param branchId the branch id
         * @return this builder
         */
        public Builder branchId(String branchId) {
            this.branchId = branchId;
            return this;
        }

        /**
         * Sets the git name of the branch to create the tool on.
         *
         * @param branchName the branch name
         * @return this builder
         */
        public Builder branchName(String branchName) {
            this.branchName = branchName;
            return this;
        }

        /**
         * Builds the request.
         *
         * @return the request
         * @throws IllegalStateException if a required field is missing
         */
        public CreateToolRequest build() {
            if (name == null || name.isBlank()) {
                throw new IllegalStateException("name is required");
            }
            if (description == null || description.isBlank()) {
                throw new IllegalStateException("description is required");
            }
            return new CreateToolRequest(this);
        }
    }
}
