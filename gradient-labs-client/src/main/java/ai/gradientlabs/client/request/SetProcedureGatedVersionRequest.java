package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for setting a gated version of a procedure.
 * <p>
 * Gated versions allow gradual rollout of new procedure versions
 * with daily conversation limits.
 * <p>
 * <strong>Note:</strong> Requires a Management API key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SetProcedureGatedVersionRequest {

    @JsonProperty("max_daily_conversations")
    private final int maxDailyConversations;

    @JsonProperty("replace")
    private final boolean replace;

    private SetProcedureGatedVersionRequest(Builder builder) {
        this.maxDailyConversations = builder.maxDailyConversations;
        this.replace = builder.replace;
    }

    /**
     * Gets the maximum number of conversations per day that can use the gated version.
     *
     * @return the maximum daily conversations
     */
    public int getMaxDailyConversations() {
        return maxDailyConversations;
    }

    /**
     * Checks if an existing gated version should be replaced.
     *
     * @return true to replace an existing gated version, false to fail if one exists
     */
    public boolean isReplace() {
        return replace;
    }

    /**
     * Creates a new builder for SetProcedureGatedVersionRequest.
     *
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int maxDailyConversations;
        private boolean replace = false;

        /**
         * Sets the maximum number of conversations per day that can use the gated version.
         * <p>
         * Setting maxDailyConversations allows gradual rollout of a new procedure version.
         *
         * @param maxDailyConversations the maximum daily conversations (required)
         * @return this builder
         */
        public Builder maxDailyConversations(int maxDailyConversations) {
            this.maxDailyConversations = maxDailyConversations;
            return this;
        }

        /**
         * Sets whether to replace an existing gated version.
         * <p>
         * If true, an existing gated version (if any) will be replaced with a new one.
         * If false (default), an error will be returned if another gated version already exists.
         *
         * @param replace true to replace existing gated versions
         * @return this builder
         */
        public Builder replace(boolean replace) {
            this.replace = replace;
            return this;
        }

        /**
         * Builds the request.
         *
         * @return the built request
         * @throws IllegalArgumentException if maxDailyConversations is not positive
         */
        public SetProcedureGatedVersionRequest build() {
            if (maxDailyConversations <= 0) {
                throw new IllegalArgumentException("maxDailyConversations must be positive");
            }
            return new SetProcedureGatedVersionRequest(this);
        }
    }
}
