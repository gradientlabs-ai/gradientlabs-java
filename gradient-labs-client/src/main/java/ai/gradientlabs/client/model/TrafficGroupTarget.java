package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a target within a traffic group.
 * <p>
 * Traffic group targets define which procedures belong to a specific traffic group.
 */
public class TrafficGroupTarget {

    @JsonProperty("target_type")
    private String targetType;

    @JsonProperty("target_id")
    private String targetId;

    /**
     * Default constructor for Jackson.
     */
    public TrafficGroupTarget() {
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
     * Gets the unique identifier of the target.
     *
     * @return the target ID
     */
    public String getTargetId() {
        return targetId;
    }

    @Override
    public String toString() {
        return "TrafficGroupTarget{" +
                "targetType='" + targetType + '\'' +
                ", targetId='" + targetId + '\'' +
                '}';
    }
}
