package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Overrides how an HTTP request is retried.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RetryPolicy {

    @JsonProperty("max_attempts")
    private Integer maxAttempts;

    @JsonProperty("request_timeout_seconds")
    private Integer requestTimeoutSeconds;

    /**
     * Default constructor for Jackson.
     */
    public RetryPolicy() {
    }

    /**
     * Creates a new instance.
     *
     * @param maxAttempts the maximum number of attempts, including the first.
     * @param requestTimeoutSeconds how long to wait for each attempt, in seconds.
     */
    public RetryPolicy(Integer maxAttempts, Integer requestTimeoutSeconds) {
        this.maxAttempts = maxAttempts;
        this.requestTimeoutSeconds = requestTimeoutSeconds;
    }

    /**
     * Returns the maximum number of attempts, including the first.
     *
     * @return the max attempts
     */
    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    /**
     * Sets the maximum number of attempts, including the first.
     *
     * @param maxAttempts the max attempts
     */
    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    /**
     * Returns how long to wait for each attempt, in seconds.
     *
     * @return the request timeout seconds
     */
    public Integer getRequestTimeoutSeconds() {
        return requestTimeoutSeconds;
    }

    /**
     * Sets how long to wait for each attempt, in seconds.
     *
     * @param requestTimeoutSeconds the request timeout seconds
     */
    public void setRequestTimeoutSeconds(Integer requestTimeoutSeconds) {
        this.requestTimeoutSeconds = requestTimeoutSeconds;
    }
}
