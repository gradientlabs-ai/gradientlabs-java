package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.ParticipantType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for assigning a conversation to a participant.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssignmentRequest {

    @JsonProperty("assignee_id")
    private final String assigneeId;

    @JsonProperty("assignee_type")
    private final ParticipantType assigneeType;

    private AssignmentRequest(Builder builder) {
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

    public static class Builder {
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

        public AssignmentRequest build() {
            return new AssignmentRequest(this);
        }
    }
}
