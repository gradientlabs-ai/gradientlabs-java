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
 * Parameters for updating a tool.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateToolRequest {

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

    private UpdateToolRequest(Builder builder) {
        this.description = builder.description;
        this.parameters = builder.parameters;
        this.parameterSets = builder.parameterSets;
        this.http = builder.http;
        this.webhook = builder.webhook;
        this.async = builder.async;
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
     * Returns what the tool does and when to use it.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the tool's input parameters. An empty or omitted list keeps the current parameters.
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
     * Builder for {@link UpdateToolRequest}.
     */
    public static class Builder {
        private String description;
        private List<ToolParameter> parameters;
        private List<ToolParameterSet> parameterSets;
        private HTTPDefinition http;
        private ToolWebhookDefinition webhook;
        private ToolAsyncDefinition async;

        private Builder() {
        }

        /**
         * Sets a new description. Leaving it empty keeps the current description.
         *
         * @param description the description
         * @return this builder
         */
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the tool's input parameters. An empty or omitted list keeps the current parameters.
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
         * Builds the request.
         *
         * @return the request
         */
        public UpdateToolRequest build() {
            return new UpdateToolRequest(this);
        }
    }
}
