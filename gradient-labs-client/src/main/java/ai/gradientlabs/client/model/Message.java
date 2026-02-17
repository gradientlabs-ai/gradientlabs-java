package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents a message in a conversation.
 */
public class Message {

    @JsonProperty("id")
    private String id;

    @JsonProperty("conversation_id")
    private String conversationId;

    @JsonProperty("participant_id")
    private String participantId;

    @JsonProperty("participant_type")
    private ParticipantType participantType;

    @JsonProperty("body")
    private String body;

    @JsonProperty("created")
    private Instant created;

    @JsonProperty("metadata")
    private Map<String, Object> metadata;

    @JsonProperty("attachments")
    private List<Attachment> attachments;

    /**
     * Default constructor for Jackson.
     */
    public Message() {
    }

    public String getId() {
        return id;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getParticipantId() {
        return participantId;
    }

    public ParticipantType getParticipantType() {
        return participantType;
    }

    public String getBody() {
        return body;
    }

    public Instant getCreated() {
        return created;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", conversationId='" + conversationId + '\'' +
                ", participantId='" + participantId + '\'' +
                ", participantType=" + participantType +
                ", body='" + body + '\'' +
                ", created=" + created +
                '}';
    }
}
