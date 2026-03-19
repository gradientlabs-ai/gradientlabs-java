package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents a traffic group.
 * <p>
 * Traffic groups allow segmenting conversations to specific procedures through
 * group-based access control.
 */
public class TrafficGroup {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("targets")
    private List<TrafficGroupTarget> targets;

    /**
     * Default constructor for Jackson.
     */
    public TrafficGroup() {
    }

    /**
     * Gets the unique identifier of the traffic group.
     *
     * @return the traffic group ID
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the display name of the traffic group.
     *
     * @return the traffic group name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the list of targets assigned to the traffic group.
     *
     * @return the list of traffic group targets
     */
    public List<TrafficGroupTarget> getTargets() {
        return targets;
    }

    @Override
    public String toString() {
        return "TrafficGroup{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", targets=" + targets +
                '}';
    }
}
