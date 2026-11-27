package ai.gradientlabs.client.webhook.event;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The back-office task a webhook relates to.
 */
public class WebhookBackOfficeTask {

    @JsonProperty("id")
    private String id;

    /**
     * Default constructor for Jackson.
     */
    public WebhookBackOfficeTask() {
    }

    /**
     * Returns your identifier for the back-office task.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }
}
