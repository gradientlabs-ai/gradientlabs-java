package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for setting an experimental version of a procedure.
 * <p>
 * Experimental versions allow gradual rollout of new procedure versions
 * with daily conversation limits.
 * <p>
 * <strong>Note:</strong> Requires a Management API key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SetProcedureExperimentVersionRequest {

    @JsonProperty("max_daily_conversations")
    private final int maxDailyConversations;

    @JsonProperty("replace")
    private final boolean replace;

    private SetProcedureExperimentVersionRequest(Builder builder) {
        this.maxDailyConversations = builder.maxDailyConversations;
        this.replace = builder.replace;
    }

    /**
     * Gets the maximum number of conversations per day that can use this experimental version.
     *
     * @return the maximum daily conversations
     */
    public int getMaxDailyConversations() {
        return maxDailyConversations;
    }

    /**
     * Checks if an existing experiment should be replaced.
     *
     * @return true to replace an existing experiment, false to fail if one exists
     */
    public boolean isReplace() {
        return replace;
    }

    /**
     * Creates a new builder for SetProcedureExperimentVersionRequest.
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
         * Sets the maximum number of conversations per day that can use this experimental version.
         * <p>
         * This allows gradual rollout of a new procedure version.
         *
         * @param maxDailyConversations the maximum daily conversations (required)
         * @return this builder
         */
        public Builder maxDailyConversations(int maxDailyConversations) {
            this.maxDailyConversations = maxDailyConversations;
            return this;
        }

        /**
         * Sets whether to replace an existing experiment.
         * <p>
         * If true, an existing experiment (if any) will be replaced with a new one.
         * If false (default), an error will be returned if another experiment already exists.
         *
         * @param replace true to replace existing experiments
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
        public SetProcedureExperimentVersionRequest build() {
            if (maxDailyConversations <= 0) {
                throw new IllegalArgumentException("maxDailyConversations must be positive");
            }
            return new SetProcedureExperimentVersionRequest(this);
        }
    }
}
