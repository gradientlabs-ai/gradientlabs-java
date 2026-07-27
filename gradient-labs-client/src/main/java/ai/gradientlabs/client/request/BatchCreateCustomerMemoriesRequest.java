package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.Memory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Parameters for batch-creating a set of memories scoped to a customer.
 * <p>
 * The batch is created asynchronously: the call is accepted and returns nothing.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchCreateCustomerMemoriesRequest {

    @JsonProperty("memories")
    private final List<Memory> memories;

    private BatchCreateCustomerMemoriesRequest(Builder builder) {
        this.memories = builder.memories;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<Memory> getMemories() {
        return memories;
    }

    public static class Builder {
        private List<Memory> memories;

        private Builder() {
        }

        /**
         * Sets the batch of memories to create (required, non-empty).
         *
         * @param memories the memories to create
         * @return this builder
         */
        public Builder memories(List<Memory> memories) {
            this.memories = memories;
            return this;
        }

        /**
         * Builds the request.
         *
         * @return a new request instance
         * @throws IllegalStateException if required fields are missing
         */
        public BatchCreateCustomerMemoriesRequest build() {
            if (memories == null || memories.isEmpty()) {
                throw new IllegalStateException("memories is required and cannot be empty");
            }
            return new BatchCreateCustomerMemoriesRequest(this);
        }
    }
}
