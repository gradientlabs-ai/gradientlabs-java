package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Defines how a secret is refreshed with an HTTP request.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefreshMechanismHTTP {

    @JsonProperty("request_definition")
    private SecretRefreshRequest requestDefinition;

    @JsonProperty("response_param_name")
    private String responseParamName;

    /**
     * Default constructor for Jackson.
     */
    public RefreshMechanismHTTP() {
    }

    /**
     * Creates a new instance.
     *
     * @param requestDefinition the request that fetches the new secret value.
     * @param responseParamName the response field that holds the new secret value.
     */
    public RefreshMechanismHTTP(SecretRefreshRequest requestDefinition, String responseParamName) {
        this.requestDefinition = requestDefinition;
        this.responseParamName = responseParamName;
    }

    /**
     * Returns the request that fetches the new secret value.
     *
     * @return the request definition
     */
    public SecretRefreshRequest getRequestDefinition() {
        return requestDefinition;
    }

    /**
     * Sets the request that fetches the new secret value.
     *
     * @param requestDefinition the request definition
     */
    public void setRequestDefinition(SecretRefreshRequest requestDefinition) {
        this.requestDefinition = requestDefinition;
    }

    /**
     * Returns the response field that holds the new secret value.
     *
     * @return the response param name
     */
    public String getResponseParamName() {
        return responseParamName;
    }

    /**
     * Sets the response field that holds the new secret value.
     *
     * @param responseParamName the response param name
     */
    public void setResponseParamName(String responseParamName) {
        this.responseParamName = responseParamName;
    }
}
