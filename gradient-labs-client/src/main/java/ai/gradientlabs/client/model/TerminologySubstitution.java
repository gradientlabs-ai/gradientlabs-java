package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * A terminology substitution rule that controls how the AI agent refers to specific terms.
 */
public class TerminologySubstitution {

    @JsonProperty("id")
    private String id;

    @JsonProperty("blocked")
    private String blocked;

    @JsonProperty("blocked_description")
    private String blockedDescription;

    @JsonProperty("replacement")
    private String replacement;

    @JsonProperty("created")
    private Instant created;

    @JsonProperty("updated")
    private Instant updated;

    @JsonProperty("resource_type_id")
    private String resourceTypeId;

    @JsonProperty("resource_attribute_json_path")
    private String resourceAttributeJsonPath;

    @JsonProperty("resource_value_to_match")
    private String resourceValueToMatch;

    public String getId() {
        return id;
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

    public Instant getCreated() {
        return created;
    }

    public Instant getUpdated() {
        return updated;
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
}
