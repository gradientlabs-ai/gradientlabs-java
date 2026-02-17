package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Response wrapper for listing resource types.
 */
public class ResourceTypesListResponse {

    @JsonProperty("resource_types")
    private List<ResourceType> resourceTypes;

    /**
     * Default constructor for Jackson.
     */
    public ResourceTypesListResponse() {
    }

    /**
     * Gets the list of resource types.
     *
     * @return the list of resource types
     */
    public List<ResourceType> getResourceTypes() {
        return resourceTypes;
    }
}
