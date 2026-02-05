package ai.gradientlabs.client.webhook.event;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Event data for a {@code conversation.finished} webhook.
 * <p>
 * Indicates the agent has concluded the conversation with the customer.
 */
public class ConversationFinishedEvent {

    @JsonProperty("conversation")
    private WebhookConversation conversation;

    @JsonProperty("reason_code")
    private String reason;

    @JsonProperty("intent")
    private String intent;

    /**
     * Default constructor for Jackson.
     */
    public ConversationFinishedEvent() {
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
     * Returns the code that describes why the agent wants to finish this conversation.
     *
     * @return the reason code, or null if not provided
     */
    public String getReason() {
        return reason;
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
        return "ConversationFinishedEvent{" +
                "conversation=" + conversation +
                ", reason='" + reason + '\'' +
                ", intent='" + intent + '\'' +
                '}';
    }
}
