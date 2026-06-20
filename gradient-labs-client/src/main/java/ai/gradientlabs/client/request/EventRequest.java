package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.ConversationEventType;
import ai.gradientlabs.client.model.ParticipantType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * Parameters for adding an event to a conversation.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventRequest {

    @JsonProperty("type")
    private final ConversationEventType type;

    @JsonProperty("participant_id")
    private final String participantId;

    @JsonProperty("participant_type")
    private final ParticipantType participantType;

    @JsonProperty("message_id")
    private final String messageId;

    @JsonProperty("timestamp")
    private final Instant timestamp;

    @JsonProperty("idempotency_key")
    private final String idempotencyKey;

    @JsonProperty("body")
    private final String body;

    private EventRequest(Builder builder) {
        this.type = builder.type;
        this.participantId = builder.participantId;
        this.participantType = builder.participantType;
        this.messageId = builder.messageId;
        this.timestamp = builder.timestamp;
        this.idempotencyKey = builder.idempotencyKey;
        this.body = builder.body;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ConversationEventType type;
        private String participantId;
        private ParticipantType participantType;
        private String messageId;
        private Instant timestamp;
        private String idempotencyKey;
        private String body;

        private Builder() {
        }

        public Builder type(ConversationEventType type) {
            this.type = type;
            return this;
        }

        public Builder participantId(String participantId) {
            this.participantId = participantId;
            return this;
        }

        public Builder participantType(ParticipantType participantType) {
            this.participantType = participantType;
            return this;
        }

        public Builder messageId(String messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder idempotencyKey(String idempotencyKey) {
            this.idempotencyKey = idempotencyKey;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public EventRequest build() {
            if (type == null) {
                throw new IllegalStateException("type is required");
            }
            if (participantId == null || participantId.isBlank()) {
                throw new IllegalStateException("participantId is required");
            }
            if (participantType == null) {
                throw new IllegalStateException("participantType is required");
            }
            return new EventRequest(this);
        }
    }
}
