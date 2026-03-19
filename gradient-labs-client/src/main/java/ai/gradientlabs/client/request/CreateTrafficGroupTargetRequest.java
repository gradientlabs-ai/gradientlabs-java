package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for adding a target to a traffic group.
 * <p>
 * <strong>Note:</strong> Requires a Management API key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateTrafficGroupTargetRequest {

    @JsonIgnore
    private final String groupId;

    @JsonProperty("target_type")
    private final String targetType;

    @JsonProperty("target_id")
    private final String targetId;

    private CreateTrafficGroupTargetRequest(Builder builder) {
        this.groupId = builder.groupId;
        this.targetType = builder.targetType;
        this.targetId = builder.targetId;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets the traffic group ID to add the target to.
     *
     * @return the traffic group ID
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Gets the type of target (possible values: "procedure").
     *
     * @return the target type
     */
    public String getTargetType() {
        return targetType;
    }

    /**
     * Gets the unique identifier of the target to add to the group.
     *
     * @return the target ID
     */
    public String getTargetId() {
        return targetId;
    }

    public static class Builder {
        private String groupId;
        private String targetType;
        private String targetId;

        private Builder() {
        }

        /**
         * Sets the traffic group ID (required).
         *
         * @param groupId the traffic group ID
         * @return this builder
         */
        public Builder groupId(String groupId) {
            this.groupId = groupId;
            return this;
        }

        /**
         * Sets the type of target to add (required, possible values: "procedure").
         *
         * @param targetType the target type
         * @return this builder
         */
        public Builder targetType(String targetType) {
            this.targetType = targetType;
            return this;
        }

        /**
         * Sets the unique identifier of the target to add (required).
         *
         * @param targetId the target ID
         * @return this builder
         */
        public Builder targetId(String targetId) {
            this.targetId = targetId;
            return this;
        }

        /**
         * Builds the request.
         *
         * @return a new request instance
         * @throws IllegalStateException if required fields are missing
         */
        public CreateTrafficGroupTargetRequest build() {
            if (groupId == null || groupId.isBlank()) {
                throw new IllegalStateException("groupId is required");
            }
            if (targetType == null || targetType.isBlank()) {
                throw new IllegalStateException("targetType is required");
            }
            if (targetId == null || targetId.isBlank()) {
                throw new IllegalStateException("targetId is required");
            }
            return new CreateTrafficGroupTargetRequest(this);
        }
    }
}
