package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Response containing a list of topics.
 */
public class ListTopicsResponse {

    @JsonProperty("topics")
    private List<Topic> topics;

    /**
     * Default constructor for Jackson.
     */
    public ListTopicsResponse() {
    }

    /**
     * Returns the list of topics.
     *
     * @return the topics
     */
    public List<Topic> getTopics() {
        return topics;
    }

    @Override
    public String toString() {
        return "ListTopicsResponse{" +
                "topics=" + topics +
                '}';
    }
}
