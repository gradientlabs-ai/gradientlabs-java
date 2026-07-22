package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * Parameters for bulk uploading a batch of memories scoped to a conversation.
 * <p>
 * Memories are stored verbatim as raw JSON payloads for the AI agent to search
 * over on demand during the conversation.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversationMemoriesBulkUploadRequest {

    @JsonProperty("idempotency_key")
    private final String idempotencyKey;

    @JsonProperty("memories")
    private final List<Map<String, Object>> memories;

    @JsonProperty("created_at_keys")
    private final List<String> createdAtKeys;

    private ConversationMemoriesBulkUploadRequest(Builder builder) {
        this.idempotencyKey = builder.idempotencyKey;
        this.memories = builder.memories;
        this.createdAtKeys = builder.createdAtKeys;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public List<Map<String, Object>> getMemories() {
        return memories;
    }

    public List<String> getCreatedAtKeys() {
        return createdAtKeys;
    }

    public static class Builder {
        private String idempotencyKey;
        private List<Map<String, Object>> memories;
        private List<String> createdAtKeys;

        private Builder() {
        }

        /**
         * Sets the idempotency key (required).
         * <p>
         * De-duplicates retries of the same upload. Re-uploading with the same key
         * returns the original upload instead of inserting again.
         *
         * @param idempotencyKey the idempotency key
         * @return this builder
         */
        public Builder idempotencyKey(String idempotencyKey) {
            this.idempotencyKey = idempotencyKey;
            return this;
        }

        /**
         * Sets the batch of memories to store (required).
         * <p>
         * Each element is an arbitrary JSON object stored verbatim as the memory's
         * raw payload.
         *
         * @param memories the memories to store
         * @return this builder
         */
        public Builder memories(List<Map<String, Object>> memories) {
            this.memories = memories;
            return this;
        }

        /**
         * Sets the JSON keys tried in order to read each memory's timestamp (optional).
         * <p>
         * The keys are tried in order against each memory's payload. When none match,
         * the upload time is used.
         *
         * @param createdAtKeys the created-at keys
         * @return this builder
         */
        public Builder createdAtKeys(List<String> createdAtKeys) {
            this.createdAtKeys = createdAtKeys;
            return this;
        }

        /**
         * Builds the request.
         *
         * @return a new request instance
         * @throws IllegalStateException if required fields are missing
         */
        public ConversationMemoriesBulkUploadRequest build() {
            if (idempotencyKey == null || idempotencyKey.isBlank()) {
                throw new IllegalStateException("idempotencyKey is required");
            }
            if (memories == null || memories.isEmpty()) {
                throw new IllegalStateException("memories is required and cannot be empty");
            }
            return new ConversationMemoriesBulkUploadRequest(this);
        }
    }
}
