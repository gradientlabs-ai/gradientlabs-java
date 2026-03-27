package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for setting a procedure's daily usage limit.
 * <p>
 * Use this to configure gated procedures to have a limited usage per day.
 * <p>
 * <strong>Note:</strong> Requires a Management API key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SetProcedureLimitRequest {

    @JsonProperty("has_daily_limit")
    private final Boolean hasDailyLimit;

    @JsonProperty("max_daily_conversations")
    private final Integer maxDailyConversations;

    private SetProcedureLimitRequest(Builder builder) {
        this.hasDailyLimit = builder.hasDailyLimit;
        this.maxDailyConversations = builder.maxDailyConversations;
    }

    /**
     * Checks if the procedure should have a daily limit.
     *
     * @return true to enable daily limits, false to disable, null if not specified
     */
    public Boolean getHasDailyLimit() {
        return hasDailyLimit;
    }

    /**
     * Gets the maximum number of conversations that can use this procedure on a given day.
     *
     * @return the maximum daily conversations, or null if not specified
     */
    public Integer getMaxDailyConversations() {
        return maxDailyConversations;
    }

    /**
     * Creates a new builder for SetProcedureLimitRequest.
     *
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Boolean hasDailyLimit;
        private Integer maxDailyConversations;

        /**
         * Sets whether the procedure should have a daily limit.
         *
         * @param hasDailyLimit true to enable daily limits, false to disable
         * @return this builder
         */
        public Builder hasDailyLimit(boolean hasDailyLimit) {
            this.hasDailyLimit = hasDailyLimit;
            return this;
        }

        /**
         * Sets the maximum number of conversations that can use this procedure on a given day.
         * <p>
         * Only relevant when hasDailyLimit is true.
         *
         * @param maxDailyConversations the maximum daily conversations
         * @return this builder
         */
        public Builder maxDailyConversations(int maxDailyConversations) {
            this.maxDailyConversations = maxDailyConversations;
            return this;
        }

        /**
         * Builds the request.
         *
         * @return the built request
         */
        public SetProcedureLimitRequest build() {
            return new SetProcedureLimitRequest(this);
        }
    }
}
