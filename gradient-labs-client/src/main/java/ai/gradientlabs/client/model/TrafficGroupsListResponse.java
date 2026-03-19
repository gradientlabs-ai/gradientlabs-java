package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Response wrapper for listing traffic groups.
 * <p>
 * Used internally for API responses.
 */
public class TrafficGroupsListResponse {

    @JsonProperty("traffic_groups")
    private List<TrafficGroup> trafficGroups;

    /**
     * Default constructor for Jackson.
     */
    public TrafficGroupsListResponse() {
    }

    /**
     * Gets the list of traffic groups.
     *
     * @return the list of traffic groups
     */
    public List<TrafficGroup> getTrafficGroups() {
        return trafficGroups;
    }
}
