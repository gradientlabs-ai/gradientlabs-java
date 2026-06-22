package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for creating a terminology substitution rule.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateTerminologySubstitutionRequest {

    @JsonProperty("blocked")
    private final String blocked;

    @JsonProperty("blocked_description")
    private final String blockedDescription;

    @JsonProperty("replacement")
    private final String replacement;

    @JsonProperty("resource_type_id")
    private final String resourceTypeId;

    @JsonProperty("resource_attribute_json_path")
    private final String resourceAttributeJsonPath;

    @JsonProperty("resource_value_to_match")
    private final String resourceValueToMatch;

    private CreateTerminologySubstitutionRequest(Builder builder) {
        this.blocked = builder.blocked;
        this.blockedDescription = builder.blockedDescription;
        this.replacement = builder.replacement;
        this.resourceTypeId = builder.resourceTypeId;
        this.resourceAttributeJsonPath = builder.resourceAttributeJsonPath;
        this.resourceValueToMatch = builder.resourceValueToMatch;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getBlocked() {
        return blocked;
    }

    public String getBlockedDescription() {
        return blockedDescription;
    }

    public String getReplacement() {
        return replacement;
    }

    public String getResourceTypeId() {
        return resourceTypeId;
    }

    public String getResourceAttributeJsonPath() {
        return resourceAttributeJsonPath;
    }

    public String getResourceValueToMatch() {
        return resourceValueToMatch;
    }

    public static class Builder {
        private String blocked;
        private String blockedDescription;
        private String replacement;
        private String resourceTypeId;
        private String resourceAttributeJsonPath;
        private String resourceValueToMatch;

        private Builder() {
        }

        public Builder blocked(String blocked) {
            this.blocked = blocked;
            return this;
        }

        public Builder blockedDescription(String blockedDescription) {
            this.blockedDescription = blockedDescription;
            return this;
        }

        public Builder replacement(String replacement) {
            this.replacement = replacement;
            return this;
        }

        public Builder resourceTypeId(String resourceTypeId) {
            this.resourceTypeId = resourceTypeId;
            return this;
        }

        public Builder resourceAttributeJsonPath(String resourceAttributeJsonPath) {
            this.resourceAttributeJsonPath = resourceAttributeJsonPath;
            return this;
        }

        public Builder resourceValueToMatch(String resourceValueToMatch) {
            this.resourceValueToMatch = resourceValueToMatch;
            return this;
        }

        public CreateTerminologySubstitutionRequest build() {
            if (blocked == null || blocked.isBlank()) {
                throw new IllegalStateException("blocked is required");
            }
            if (blockedDescription == null || blockedDescription.isBlank()) {
                throw new IllegalStateException("blockedDescription is required");
            }
            if (replacement == null || replacement.isBlank()) {
                throw new IllegalStateException("replacement is required");
            }
            return new CreateTerminologySubstitutionRequest(this);
        }
    }
}
