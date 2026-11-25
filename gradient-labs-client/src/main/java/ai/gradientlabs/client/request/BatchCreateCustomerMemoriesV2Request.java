package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.CustomerMemory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Parameters for batch-creating memories scoped to a customer.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchCreateCustomerMemoriesV2Request {

    @JsonProperty("memories")
    private final List<CustomerMemory> memories;

    private BatchCreateCustomerMemoriesV2Request(Builder builder) {
        this.memories = builder.memories;
    }

    /**
     * Returns a new builder.
     *
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the memories to create, at least one.
     *
     * @return the memories
     */
    public List<CustomerMemory> getMemories() {
        return memories;
    }

    /**
     * Builder for {@link BatchCreateCustomerMemoriesV2Request}.
     */
    public static class Builder {
        private List<CustomerMemory> memories;

        private Builder() {
        }

        /**
         * Sets the memories to create, at least one. Required.
         *
         * @param memories the memories
         * @return this builder
         */
        public Builder memories(List<CustomerMemory> memories) {
            this.memories = memories;
            return this;
        }

        /**
         * Builds the request.
         *
         * @return the request
         * @throws IllegalStateException if a required field is missing
         */
        public BatchCreateCustomerMemoriesV2Request build() {
            if (memories == null || memories.isEmpty()) {
                throw new IllegalStateException("memories is required");
            }
            return new BatchCreateCustomerMemoriesV2Request(this);
        }
    }
}
