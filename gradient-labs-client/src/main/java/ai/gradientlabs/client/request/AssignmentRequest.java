package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.ParticipantType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * Parameters for assigning a conversation to a participant.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssignmentRequest {

    @JsonProperty("assignee_id")
    private final String assigneeId;

    @JsonProperty("assignee_type")
    private final ParticipantType assigneeType;

    @JsonProperty("reason")
    private final String reason;

    @JsonProperty("timestamp")
    private final Instant timestamp;

    private AssignmentRequest(Builder builder) {
        this.reason = builder.reason;
        this.timestamp = builder.timestamp;
        this.assigneeId = builder.assigneeId;
        this.assigneeType = builder.assigneeType;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public ParticipantType getAssigneeType() {
        return assigneeType;
    }

    /**
     * Returns why the conversation is being assigned.
     *
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Returns when the assignment happened. Defaults to now.
     *
     * @return the timestamp
     */
    public Instant getTimestamp() {
        return timestamp;
    }

    public static class Builder {
        private String reason;
        private Instant timestamp;
        private String assigneeId;
        private ParticipantType assigneeType;

        private Builder() {
        }

        public Builder assigneeId(String assigneeId) {
            this.assigneeId = assigneeId;
            return this;
        }

        public Builder assigneeType(ParticipantType assigneeType) {
            this.assigneeType = assigneeType;
            return this;
        }

        /**
         * Sets why the conversation is being assigned.
         *
         * @param reason the reason
         * @return this builder
         */
        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        /**
         * Sets when the assignment happened. Defaults to now.
         *
         * @param timestamp the timestamp
         * @return this builder
         */
        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public AssignmentRequest build() {
            return new AssignmentRequest(this);
        }
    }
}
