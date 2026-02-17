package ai.gradientlabs.client.webhook.event;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Event data for an {@code agent.message} webhook.
 * <p>
 * Indicates the agent wants to send the customer a message.
 */
public class AgentMessageEvent {

    @JsonProperty("conversation")
    private WebhookConversation conversation;

    @JsonProperty("body")
    private String body;

    @JsonProperty("total")
    private int total;

    @JsonProperty("sequence")
    private int sequence;

    @JsonProperty("intent")
    private String intent;

    /**
     * Default constructor for Jackson.
     */
    public AgentMessageEvent() {
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
     * Returns the text of the message the agent wants to send.
     *
     * @return the message body
     */
    public String getBody() {
        return body;
    }

    /**
     * Returns the total number of agent messages that have been produced in the current turn.
     *
     * @return the total count
     */
    public int getTotal() {
        return total;
    }

    /**
     * Returns which agent message this is in the current turn.
     *
     * @return the sequence number
     */
    public int getSequence() {
        return sequence;
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
        return "AgentMessageEvent{" +
                "conversation=" + conversation +
                ", body='" + body + '\'' +
                ", total=" + total +
                ", sequence=" + sequence +
                ", intent='" + intent + '\'' +
                '}';
    }
}
