package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Response wrapper for listing secrets.
 * <p>
 * This class is used internally for API responses.
 */
public class SecretsListResponse {

    @JsonProperty("secrets")
    private List<Secret> secrets;

    /**
     * Default constructor for Jackson.
     */
    public SecretsListResponse() {
    }

    /**
     * Gets the list of secrets.
     *
     * @return the list of secrets
     */
    public List<Secret> getSecrets() {
        return secrets;
    }
}
