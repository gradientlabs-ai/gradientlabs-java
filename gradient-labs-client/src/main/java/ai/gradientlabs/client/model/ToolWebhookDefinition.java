package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Runs a tool by sending an {@code action.execute} webhook.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ToolWebhookDefinition {

    @JsonProperty("name")
    private String name;

    /**
     * Default constructor for Jackson.
     */
    public ToolWebhookDefinition() {
    }

    /**
     * Creates a new instance.
     *
     * @param name the action name sent in the webhook.
     */
    public ToolWebhookDefinition(String name) {
        this.name = name;
    }

    /**
     * Returns the action name sent in the webhook.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the action name sent in the webhook.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }
}
