package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.Map;

/**
 * Parameters for returning an async tool execution result.
 * <p>
 * When a tool is configured for asynchronous execution, the agent will request
 * the tool execution via an action.execute webhook event, and your system should
 * return the result using this request.
 * <p>
 * This allows your system to perform long-running operations without blocking the
 * conversation, and return results when they're ready.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReturnAsyncToolResultRequest {

    @JsonProperty("async_tool_execution_id")
    private final String asyncToolExecutionId;

    @JsonProperty("payload")
    private final Map<String, Object> payload;

    @JsonProperty("timestamp")
    private final Instant timestamp;

    private ReturnAsyncToolResultRequest(Builder builder) {
        this.asyncToolExecutionId = builder.asyncToolExecutionId;
        this.payload = builder.payload;
        this.timestamp = builder.timestamp;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getAsyncToolExecutionId() {
        return asyncToolExecutionId;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public static class Builder {
        private String asyncToolExecutionId;
        private Map<String, Object> payload;
        private Instant timestamp;

        private Builder() {
        }

        /**
         * Sets the unique identifier for the async tool execution.
         * This ID is provided in the action.execute webhook event when the agent
         * requests the tool execution.
         *
         * @param asyncToolExecutionId the async tool execution ID
         * @return this builder
         */
        public Builder asyncToolExecutionId(String asyncToolExecutionId) {
            this.asyncToolExecutionId = asyncToolExecutionId;
            return this;
        }

        /**
         * Sets the result data from the tool execution as a JSON object.
         * The structure of this object depends on your tool's implementation.
         * The agent will use AI to extract relevant information from any well-formed
         * JSON object.
         *
         * @param payload the result payload
         * @return this builder
         */
        public Builder payload(Map<String, Object> payload) {
            this.payload = payload;
            return this;
        }

        /**
         * Sets the time when the result was generated.
         * If not set, defaults to the current time.
         *
         * @param timestamp the timestamp
         * @return this builder
         */
        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ReturnAsyncToolResultRequest build() {
            if (asyncToolExecutionId == null) {
                throw new IllegalArgumentException("asyncToolExecutionId is required");
            }
            if (payload == null) {
                throw new IllegalArgumentException("payload is required");
            }
            return new ReturnAsyncToolResultRequest(this);
        }
    }
}
