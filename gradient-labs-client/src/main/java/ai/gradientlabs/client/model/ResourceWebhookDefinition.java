package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Contains configuration for webhook actions.
 */
public class ResourceWebhookDefinition {

    @JsonProperty("name")
    private String name;

    /**
     * Default constructor for Jackson.
     */
    public ResourceWebhookDefinition() {
    }

    /**
     * Constructor with name field.
     */
    public ResourceWebhookDefinition(String name) {
        this.name = name;
    }

    /**
     * Gets the name that will be included in the webhook payload.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }
}
