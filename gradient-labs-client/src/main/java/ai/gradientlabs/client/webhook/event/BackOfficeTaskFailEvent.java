package ai.gradientlabs.client.webhook.event;

/**
 * Event data for a {@code back-office-task.fail} webhook.
 * <p>
 * Indicates the back-office task has failed. Read why from {@link ai.gradientlabs.client.model.BackOfficeTask#getFailureReasons()}.
 */
public class BackOfficeTaskFailEvent extends BackOfficeTaskEvent {

    /**
     * Default constructor for Jackson.
     */
    public BackOfficeTaskFailEvent() {
    }
}
