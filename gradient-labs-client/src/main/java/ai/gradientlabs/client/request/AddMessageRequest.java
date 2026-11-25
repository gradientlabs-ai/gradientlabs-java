package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.Attachment;
import ai.gradientlabs.client.model.ParticipantType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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

    @JsonProperty("attachments")
    private final List<Attachment> attachments;

    @JsonProperty("conversation_token")
    private final String conversationToken;

    @JsonProperty("subject")
    private final String subject;

    private AddMessageRequest(Builder builder) {
        this.conversationToken = builder.conversationToken;
        this.subject = builder.subject;
        this.id = builder.id;
        this.body = builder.body;
        this.participantId = builder.participantId;
        this.participantType = builder.participantType;
        this.created = builder.created;
        this.attachments = builder.attachments;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the conversation token, if your support platform uses one.
     *
     * @return the conversation token
     */
    public String getConversationToken() {
        return conversationToken;
    }

    /**
     * Returns the message subject, for emails.
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    public static class Builder {
        private String conversationToken;
        private String subject;
        private String id;
        private String body;
        private String participantId;
        private ParticipantType participantType;
        private Instant created;
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

        /**
         * Sets the conversation token, if your support platform uses one.
         *
         * @param conversationToken the conversation token
         * @return this builder
         */
        public Builder conversationToken(String conversationToken) {
            this.conversationToken = conversationToken;
            return this;
        }

        /**
         * Sets the message subject, for emails.
         *
         * @param subject the subject
         * @return this builder
         */
        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public AddMessageRequest build() {
            if (id == null || id.isBlank()) {
                throw new IllegalStateException("id is required");
            }
            if ((participantType != ParticipantType.AI_AGENT || (body != null && !body.isEmpty()))
                    && (participantId == null || participantId.isBlank())) {
                throw new IllegalStateException("participantId is required");
            }
            if (participantType == null) {
                throw new IllegalStateException("participantType is required");
            }
            return new AddMessageRequest(this);
        }
    }
}
