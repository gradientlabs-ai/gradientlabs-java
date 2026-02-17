package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Response containing a list of resource sources.
 */
public class ResourceSourcesListResponse {

    @JsonProperty("resource_sources")
    private List<ResourceSource> resourceSources;

    /**
     * Default constructor for Jackson.
     */
    public ResourceSourcesListResponse() {
    }

    /**
     * Constructor with resource sources list.
     */
    public ResourceSourcesListResponse(List<ResourceSource> resourceSources) {
        this.resourceSources = resourceSources;
    }

    /**
     * Gets the list of resource sources.
     *
     * @return the resource sources list
     */
    public List<ResourceSource> getResourceSources() {
        return resourceSources;
    }

    /**
     * Sets the resource sources list.
     *
     * @param resourceSources the resource sources list
     */
    public void setResourceSources(List<ResourceSource> resourceSources) {
        this.resourceSources = resourceSources;
    }
}
