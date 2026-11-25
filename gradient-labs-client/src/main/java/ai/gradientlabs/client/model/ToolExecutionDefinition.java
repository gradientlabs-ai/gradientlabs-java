package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * How one step of an asynchronous tool runs. Set exactly one of the fields.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ToolExecutionDefinition {

    @JsonProperty("http")
    private HTTPDefinition http;

    @JsonProperty("webhook")
    private ToolWebhookDefinition webhook;

    /**
     * Default constructor for Jackson.
     */
    public ToolExecutionDefinition() {
    }

    /**
     * Creates a new instance.
     *
     * @param http the HTTP request that runs the step.
     * @param webhook the webhook that runs the step.
     */
    public ToolExecutionDefinition(HTTPDefinition http, ToolWebhookDefinition webhook) {
        this.http = http;
        this.webhook = webhook;
    }

    /**
     * Returns the HTTP request that runs the step.
     *
     * @return the http
     */
    public HTTPDefinition getHttp() {
        return http;
    }

    /**
     * Sets the HTTP request that runs the step.
     *
     * @param http the http
     */
    public void setHttp(HTTPDefinition http) {
        this.http = http;
    }

    /**
     * Returns the webhook that runs the step.
     *
     * @return the webhook
     */
    public ToolWebhookDefinition getWebhook() {
        return webhook;
    }

    /**
     * Sets the webhook that runs the step.
     *
     * @param webhook the webhook
     */
    public void setWebhook(ToolWebhookDefinition webhook) {
        this.webhook = webhook;
    }
}
