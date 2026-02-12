package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.ParticipantType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for adding an event to a conversation.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventRequest {

    @JsonProperty("type")
    private final String type;

    @JsonProperty("participant_id")
    private final String participantId;

    @JsonProperty("participant_type")
    private final ParticipantType participantType;

    @JsonProperty("body")
    private final String body;

    private EventRequest(Builder builder) {
        this.type = builder.type;
        this.participantId = builder.participantId;
        this.participantType = builder.participantType;
        this.body = builder.body;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String type;
        private String participantId;
        private ParticipantType participantType;
        private String body;

        private Builder() {
        }

        public Builder type(String type) {
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

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public EventRequest build() {
            if (type == null || type.isBlank()) {
                throw new IllegalStateException("type is required");
            }
            return new EventRequest(this);
        }
    }
}
