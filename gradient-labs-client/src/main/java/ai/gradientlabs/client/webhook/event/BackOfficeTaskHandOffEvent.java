package ai.gradientlabs.client.webhook.event;

/**
 * Event data for a {@code back-office-task.hand-off} webhook.
 * <p>
 * Indicates the back-office task has been handed off to a human.
 */
public class BackOfficeTaskHandOffEvent extends BackOfficeTaskEvent {

    /**
     * Default constructor for Jackson.
     */
    public BackOfficeTaskHandOffEvent() {
    }
}
