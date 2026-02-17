package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.Attachment;
import ai.gradientlabs.client.model.ParticipantType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parameters for adding a message to a conversation.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddMessageRequest {

    @JsonProperty("id")
    private final String id;

    @JsonProperty("body")
    private final String body;

    @JsonProperty("participant_id")
    private final String participantId;

    @JsonProperty("participant_type")
    private final ParticipantType participantType;

    @JsonProperty("created")
    private final Instant created;

    @JsonProperty("metadata")
    private final Map<String, Object> metadata;

    @JsonProperty("attachments")
    private final List<Attachment> attachments;

    private AddMessageRequest(Builder builder) {
        this.id = builder.id;
        this.body = builder.body;
        this.participantId = builder.participantId;
        this.participantType = builder.participantType;
        this.created = builder.created;
        this.metadata = builder.metadata;
        this.attachments = builder.attachments;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String body;
        private String participantId;
        private ParticipantType participantType;
        private Instant created;
        private Map<String, Object> metadata;
        private List<Attachment> attachments;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
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

        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        public Builder metadata(Map<String, Object> metadata) {
            this.metadata = metadata;
            return this;
        }

        public Builder addMetadata(String key, Object value) {
            if (this.metadata == null) {
                this.metadata = new HashMap<>();
            }
            this.metadata.put(key, value);
            return this;
        }

        public Builder attachments(List<Attachment> attachments) {
            this.attachments = attachments;
            return this;
        }

        public Builder addAttachment(Attachment attachment) {
            if (this.attachments == null) {
                this.attachments = new ArrayList<>();
            }
            this.attachments.add(attachment);
            return this;
        }

        public AddMessageRequest build() {
            if (id == null || id.isBlank()) {
                throw new IllegalStateException("id is required");
            }
            if (body == null || body.isBlank()) {
                throw new IllegalStateException("body is required");
            }
            if (participantId == null || participantId.isBlank()) {
                throw new IllegalStateException("participantId is required");
            }
            if (participantType == null) {
                throw new IllegalStateException("participantType is required");
            }
            return new AddMessageRequest(this);
        }
    }
}
