package ai.gradientlabs.client.webhook.event;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Event data for a {@code conversation.hand_off} webhook.
 * <p>
 * Indicates the agent is escalating or handing the conversation off to a human agent.
 */
public class ConversationHandOffEvent {

    @JsonProperty("conversation")
    private WebhookConversation conversation;

    @JsonProperty("target")
    private String target;

    @JsonProperty("reason_code")
    private String reason;

    @JsonProperty("reason")
    private String description;

    @JsonProperty("note")
    private String note;

    @JsonProperty("intent")
    private String intent;

    /**
     * Default constructor for Jackson.
     */
    public ConversationHandOffEvent() {
    }

    /**
     * Returns the conversation details.
     *
     * @return the conversation
     */
    public WebhookConversation getConversation() {
        return conversation;
    }

    /**
     * Returns where the agent wants to hand this conversation to.
     *
     * @return the target, or null if not specified
     */
    public String getTarget() {
        return target;
    }

    /**
     * Returns the code that describes why the agent wants to hand off this conversation.
     *
     * @return the reason code
     */
    public String getReason() {
        return reason;
    }

    /**
     * Returns a human-legible description of the reason code.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a human-legible summary of the conversation so far, for a smooth hand-off.
     *
     * @return the note, or null if not provided
     */
    public String getNote() {
        return note;
    }

    /**
     * Returns the most recent intent that was classified from the customer's conversation.
     *
     * @return the intent, or null if none
     */
    public String getIntent() {
        return intent;
    }

    @Override
    public String toString() {
        return "ConversationHandOffEvent{" +
                "conversation=" + conversation +
                ", target='" + target + '\'' +
                ", reason='" + reason + '\'' +
                ", description='" + description + '\'' +
                ", note='" + note + '\'' +
                ", intent='" + intent + '\'' +
                '}';
    }
}
