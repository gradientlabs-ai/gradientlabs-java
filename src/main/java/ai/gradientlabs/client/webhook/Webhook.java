package ai.gradientlabs.client.webhook;

import ai.gradientlabs.client.webhook.event.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * An event delivered to your webhook endpoint.
 */
public class Webhook {

    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private WebhookType type;

    @JsonProperty("sequence_number")
    private int sequenceNumber;

    @JsonProperty("timestamp")
    private Instant timestamp;

    private Object data;

    /**
     * Default constructor for Jackson.
     */
    public Webhook() {
    }

    /**
     * Returns the unique identifier for this event.
     *
     * @return the event ID
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the type of event.
     *
     * @return the webhook type
     */
    public WebhookType getType() {
        return type;
    }

    /**
     * Returns the sequence number.
     * <p>
     * Can be used to establish an order of webhook events.
     * See: https://api-docs.gradient-labs.ai/#sequence-numbers
     *
     * @return the sequence number
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Returns the time at which this event was generated.
     *
     * @return the timestamp
     */
    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the event data.
     * <p>
     * Use the type-safe methods like {@link #asAgentMessage()} to access specific event types.
     *
     * @return the event data
     */
    public Object getData() {
        return data;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(WebhookType type) {
        this.type = type;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * Returns the data for an {@code agent.message} event.
     *
     * @return the event data, or null if this is not an agent message event
     */
    public AgentMessageEvent asAgentMessage() {
        return data instanceof AgentMessageEvent ? (AgentMessageEvent) data : null;
    }

    /**
     * Returns the data for a {@code conversation.hand_off} event.
     *
     * @return the event data, or null if this is not a handoff event
     */
    public ConversationHandOffEvent asConversationHandOff() {
        return data instanceof ConversationHandOffEvent ? (ConversationHandOffEvent) data : null;
    }

    /**
     * Returns the data for a {@code conversation.finished} event.
     *
     * @return the event data, or null if this is not a finished event
     */
    public ConversationFinishedEvent asConversationFinished() {
        return data instanceof ConversationFinishedEvent ? (ConversationFinishedEvent) data : null;
    }

    /**
     * Returns the data for an {@code action.execute} event.
     *
     * @return the event data, or null if this is not an action execute event
     */
    public ActionExecuteEvent asActionExecute() {
        return data instanceof ActionExecuteEvent ? (ActionExecuteEvent) data : null;
    }

    /**
     * Returns the data for a {@code resource.pull} event.
     *
     * @return the event data, or null if this is not a resource pull event
     */
    public ResourcePullEvent asResourcePull() {
        return data instanceof ResourcePullEvent ? (ResourcePullEvent) data : null;
    }

    @Override
    public String toString() {
        return "Webhook{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", sequenceNumber=" + sequenceNumber +
                ", timestamp=" + timestamp +
                '}';
    }
}
