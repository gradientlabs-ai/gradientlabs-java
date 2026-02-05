package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.Map;

/**
 * Represents a series of messages between a customer, human agent, and the AI Agent.
 */
public class Conversation {

    @JsonProperty("id")
    private String id;

    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("channel")
    private Channel channel;

    @JsonProperty("metadata")
    private Map<String, Object> metadata;

    @JsonProperty("created")
    private Instant created;

    @JsonProperty("updated")
    private Instant updated;

    @JsonProperty("status")
    private ConversationStatus status;

    @JsonProperty("agent_is_active")
    private boolean agentIsActive;

    @JsonProperty("latest_agent_metadata")
    private AgentMetadata agentMetadata;

    /**
     * Default constructor for Jackson.
     */
    public Conversation() {
    }

    /**
     * Returns the unique identifier for this conversation.
     * <p>
     * Can be anything consisting of letters, numbers, or any of the following
     * characters: _ - + =
     * <p>
     * Tip: use something meaningful to your business (e.g. a ticket number).
     *
     * @return the conversation ID
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the unique identifier for the customer.
     * <p>
     * Used to build historical context of conversations the agent has had with this customer.
     *
     * @return the customer ID
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Returns the communication channel for this conversation.
     *
     * @return the channel
     */
    public Channel getChannel() {
        return channel;
    }

    /**
     * Returns arbitrary metadata attached to the conversation.
     * <p>
     * This metadata will be passed along with webhooks and can be used as action parameters.
     *
     * @return the metadata, or null if none
     */
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    /**
     * Returns the time at which the conversation was created.
     *
     * @return the creation time
     */
    public Instant getCreated() {
        return created;
    }

    /**
     * Returns the time at which the conversation was last updated.
     *
     * @return the last update time
     */
    public Instant getUpdated() {
        return updated;
    }

    /**
     * Returns the current state of the conversation.
     *
     * @return the status
     */
    public ConversationStatus getStatus() {
        return status;
    }

    /**
     * Returns whether the AI agent is currently assigned to respond in this conversation.
     *
     * @return true if the agent is active
     */
    public boolean isAgentActive() {
        return agentIsActive;
    }

    /**
     * Returns metadata from the agent about this conversation.
     *
     * @return the agent metadata, or null if none
     */
    public AgentMetadata getAgentMetadata() {
        return agentMetadata;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", channel=" + channel +
                ", status=" + status +
                ", agentIsActive=" + agentIsActive +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
