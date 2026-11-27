package ai.gradientlabs.client.webhook.event;

import ai.gradientlabs.client.model.BackOfficeTask;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Event data shared by the back-office task webhooks.
 */
public abstract class BackOfficeTaskEvent {

    @JsonProperty("back_office_task")
    private BackOfficeTask backOfficeTask;

    /**
     * Returns the back-office task the event is about.
     *
     * @return the back-office task
     */
    public BackOfficeTask getBackOfficeTask() {
        return backOfficeTask;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{backOfficeTask=" + backOfficeTask + '}';
    }
}
