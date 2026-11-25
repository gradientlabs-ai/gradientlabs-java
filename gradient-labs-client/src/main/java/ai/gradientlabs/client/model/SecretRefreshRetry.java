package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Timeout for each attempt to refresh a secret. */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SecretRefreshRetry(
        @JsonProperty("request_timeout_seconds") Integer requestTimeoutSeconds) {
}
