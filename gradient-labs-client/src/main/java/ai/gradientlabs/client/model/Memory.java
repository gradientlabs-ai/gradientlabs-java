package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.NullNode;

import java.time.Instant;

/**
 * A single memory to create for a customer with the deprecated v1 batch-create endpoint.
 * <p>
 * Use {@link CustomerMemory} with {@code batchCreateCustomerMemoriesV2} instead, which also takes a
 * category description.
 * <p>
 * The {@code data} payload is stored verbatim as arbitrary JSON for the AI agent
 * to search over on demand.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Memory {

    @JsonProperty("external_id")
    private final String externalId;

    @JsonProperty("custom_type")
    private final String customType;

    @JsonProperty("created_at")
    private final Instant createdAt;

    @JsonProperty("data")
    private final Object data;

    private Memory(Builder builder) {
        this.externalId = builder.externalId;
        this.customType = builder.customType;
        this.createdAt = builder.createdAt;
        this.data = builder.data;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getExternalId() {
        return externalId;
    }

    public String getCustomType() {
        return customType;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Object getData() {
        return data;
    }

    public static class Builder {
        private String externalId;
        private String customType;
        private Instant createdAt;
        private Object data;

        private Builder() {
        }

        /**
         * Sets the caller's own identifier for this memory (required).
         *
         * @param externalId the external ID
         * @return this builder
         */
        public Builder externalId(String externalId) {
            this.externalId = externalId;
            return this;
        }

        /**
         * Sets the category name for this memory (required). {@code support_conversation} is reserved.
         *
         * @param customType the custom type
         * @return this builder
         */
        public Builder customType(String customType) {
            this.customType = customType;
            return this;
        }

        /**
         * Sets the time the memory was created (required).
         *
         * @param createdAt the creation timestamp
         * @return this builder
         */
        public Builder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * Sets the arbitrary JSON payload stored verbatim as the memory (required).
         *
         * @param data the memory data
         * @return this builder
         */
        public Builder data(Object data) {
            this.data = data == null ? NullNode.getInstance() : data;
            return this;
        }

        /**
         * Builds the memory.
         *
         * @return a new memory instance
         * @throws IllegalStateException if required fields are missing
         */
        public Memory build() {
            if (externalId == null || externalId.isBlank()) {
                throw new IllegalStateException("externalId is required");
            }
            if (customType == null || customType.isBlank()) {
                throw new IllegalStateException("customType is required");
            }
            if (createdAt == null) {
                throw new IllegalStateException("createdAt is required");
            }
            if (data == null) {
                throw new IllegalStateException("data is required");
            }
            return new Memory(this);
        }
    }
}
