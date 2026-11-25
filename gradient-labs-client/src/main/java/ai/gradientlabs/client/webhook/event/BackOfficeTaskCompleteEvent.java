package ai.gradientlabs.client.webhook.event;

/**
 * Event data for a {@code back-office-task.complete} webhook.
 * <p>
 * Indicates the back-office task has completed. Read its result from {@link ai.gradientlabs.client.model.BackOfficeTask#getResult()}.
 */
public class BackOfficeTaskCompleteEvent extends BackOfficeTaskEvent {

    /**
     * Default constructor for Jackson.
     */
    public BackOfficeTaskCompleteEvent() {
    }
}
