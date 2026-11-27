package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * The response from listing a customer's memories.
 */
public class CustomerMemoriesListResponse {

    @JsonProperty("memories")
    private List<CustomerMemory> memories;

    /**
     * Default constructor for Jackson.
     */
    public CustomerMemoriesListResponse() {
    }

    /**
     * Returns the customer's memories.
     *
     * @return the memories
     */
    public List<CustomerMemory> getMemories() {
        return memories;
    }
}
