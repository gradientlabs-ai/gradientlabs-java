package ai.gradientlabs.client.webhook.event;

import ai.gradientlabs.client.model.Channel;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Event data for a {@code conversation.start} webhook.
 * <p>
 * Indicates the agent is starting an outbound conversation and wants you to send the first message.
 */
public class ConversationStartEvent {

    @JsonProperty("body")
    private String body;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("channel")
    private Channel channel;

    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("initiator_id")
    private String initiatorId;

    /**
     * Default constructor for Jackson.
     */
    public ConversationStartEvent() {
    }

    /**
     * Returns the first message to send to the customer.
     *
     * @return the message body
     */
    public String getBody() {
        return body;
    }

    /**
     * Returns the subject of the first message, for emails.
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Returns the channel to start the conversation on.
     *
     * @return the channel
     */
    public Channel getChannel() {
        return channel;
    }

    /**
     * Returns your identifier for the customer.
     *
     * @return the customer ID
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Returns the identifier of whoever started the outbound conversation.
     *
     * @return the initiator ID
     */
    public String getInitiatorId() {
        return initiatorId;
    }

    @Override
    public String toString() {
        return "ConversationStartEvent{" +
                "channel=" + channel +
                ", customerId='" + customerId + '\'' +
                ", initiatorId='" + initiatorId + '\'' +
                '}';
    }
}
