package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a hand-off target for transferring conversations to human agents or other systems.
 * <p>
 * Hand-off targets define where conversations can be transferred when they need human intervention
 * or routing to other systems. They can be referenced in intents and procedures.
 */
public class HandOffTarget {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    /**
     * Default constructor for Jackson.
     */
    public HandOffTarget() {
    }

    /**
     * Gets the unique identifier for this hand-off target.
     * <p>
     * Can consist of letters, numbers, or any of the following characters: _ - + =
     *
     * @return the hand-off target ID
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the human-friendly name of this hand-off target.
     *
     * @return the hand-off target name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "HandOffTarget{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
