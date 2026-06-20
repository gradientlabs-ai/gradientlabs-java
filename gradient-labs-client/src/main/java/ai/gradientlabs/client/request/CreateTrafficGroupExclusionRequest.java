package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for adding an exclusion to a traffic group.
 * <p>
 * <strong>Note:</strong> Requires a Management API key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateTrafficGroupExclusionRequest {

    @JsonProperty("target_type")
    private final String targetType;

    @JsonProperty("target_id")
    private final String targetId;

    private CreateTrafficGroupExclusionRequest(Builder builder) {
        this.targetType = builder.targetType;
        this.targetId = builder.targetId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getTargetType() {
        return targetType;
    }

    public String getTargetId() {
        return targetId;
    }

    public static class Builder {
        private String targetType;
        private String targetId;

        private Builder() {
        }

        public Builder targetType(String targetType) {
            this.targetType = targetType;
            return this;
        }

        public Builder targetId(String targetId) {
            this.targetId = targetId;
            return this;
        }

        public CreateTrafficGroupExclusionRequest build() {
            if (targetType == null || targetType.isBlank()) {
                throw new IllegalStateException("targetType is required");
            }
            if (targetId == null || targetId.isBlank()) {
                throw new IllegalStateException("targetId is required");
            }
            return new CreateTrafficGroupExclusionRequest(this);
        }
    }
}
