package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.ParticipantType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.Map;

/**
 * Parameters for resuming a finished conversation.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResumeConversationRequest {

    @JsonProperty("assignee_type")
    private final ParticipantType assigneeType;

    @JsonProperty("assignee_id")
    private final String assigneeId;

    @JsonProperty("timestamp")
    private final Instant timestamp;

    @JsonProperty("reason")
    private final String reason;

    @JsonProperty("resources")
    private final Map<String, Object> resources;

    private ResumeConversationRequest(Builder builder) {
        this.assigneeType = builder.assigneeType;
        this.assigneeId = builder.assigneeId;
        this.timestamp = builder.timestamp;
        this.reason = builder.reason;
        this.resources = builder.resources;
    }

    public static Builder builder() {
        return new Builder();
    }

    public ParticipantType getAssigneeType() {
        return assigneeType;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getReason() {
        return reason;
    }

    public Map<String, Object> getResources() {
        return resources;
    }

    public static class Builder {
        private ParticipantType assigneeType;
        private String assigneeId;
        private Instant timestamp;
        private String reason;
        private Map<String, Object> resources;

        private Builder() {
        }

        public Builder assigneeType(ParticipantType assigneeType) {
            this.assigneeType = assigneeType;
            return this;
        }

        public Builder assigneeId(String assigneeId) {
            this.assigneeId = assigneeId;
            return this;
        }

        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder resources(Map<String, Object> resources) {
            this.resources = resources;
            return this;
        }

        public ResumeConversationRequest build() {
            if (assigneeType == null) {
                throw new IllegalStateException("assigneeType is required");
            }
            return new ResumeConversationRequest(this);
        }
    }
}
