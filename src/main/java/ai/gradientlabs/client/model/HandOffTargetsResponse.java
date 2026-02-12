package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Response wrapper for listing hand-off targets.
 * <p>
 * This class is used internally for API responses.
 */
public class HandOffTargetsResponse {

    @JsonProperty("targets")
    private List<HandOffTarget> targets;

    /**
     * Default constructor for Jackson.
     */
    public HandOffTargetsResponse() {
    }

    /**
     * Gets the list of hand-off targets.
     *
     * @return the list of hand-off targets
     */
    public List<HandOffTarget> getTargets() {
        return targets;
    }
}
