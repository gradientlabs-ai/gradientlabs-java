package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Contains metadata from the agent about a conversation.
 */
public class AgentMetadata {

    @JsonProperty("intent")
    private String intent;

    @JsonProperty("intent_handoff_target")
    private String intentHandoffTarget;

    @JsonProperty("handoff_reason")
    private String handoffReason;

    @JsonProperty("handoff_note")
    private String handoffNote;

    /**
     * Default constructor for Jackson.
     */
    public AgentMetadata() {
    }

    /**
     * Returns the name of the latest intent that the agent has classified
     * for this conversation.
     *
     * @return the intent, or null if not available
     */
    public String getIntent() {
        return intent;
    }

    /**
     * Returns the ID of the handoff target that is currently associated
     * with the latest intent.
     *
     * @return the handoff target ID, or null if not available
     */
    public String getIntentHandoffTarget() {
        return intentHandoffTarget;
    }

    /**
     * Returns the coded reason why the agent has handed off the conversation.
     *
     * @return the handoff reason, or null if not handed off
     */
    public String getHandoffReason() {
        return handoffReason;
    }

    /**
     * Returns a free-text note that the agent generated to summarize what
     * it has done so far when handing off the conversation.
     *
     * @return the handoff note, or null if not handed off
     */
    public String getHandoffNote() {
        return handoffNote;
    }

    @Override
    public String toString() {
        return "AgentMetadata{" +
                "intent='" + intent + '\'' +
                ", intentHandoffTarget='" + intentHandoffTarget + '\'' +
                ", handoffReason='" + handoffReason + '\'' +
                ", handoffNote='" + handoffNote + '\'' +
                '}';
    }
}
