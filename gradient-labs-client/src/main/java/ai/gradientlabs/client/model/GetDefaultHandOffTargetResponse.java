package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response containing the default hand-off target ID for a channel.
 */
public class GetDefaultHandOffTargetResponse {

    @JsonProperty("id")
    private String id;

    /**
     * Default constructor for Jackson.
     */
    public GetDefaultHandOffTargetResponse() {
    }

    /**
     * Returns the unique identifier for the default hand-off target.
     * Returns an empty string if no default is set.
     *
     * @return the hand-off target ID, or empty string if no default is set
     */
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "GetDefaultHandOffTargetResponse{" +
                "id='" + id + '\'' +
                '}';
    }
}
