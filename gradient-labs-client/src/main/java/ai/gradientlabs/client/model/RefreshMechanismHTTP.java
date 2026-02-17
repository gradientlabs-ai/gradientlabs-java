package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Defines how to automatically refresh a secret's value using an HTTP request.
 * This is commonly used for OAuth access tokens that need periodic renewal.
 */
public class RefreshMechanismHTTP {

    @JsonProperty("request_definition")
    private HTTPDefinition requestDefinition;

    @JsonProperty("response_param_name")
    private String responseParamName;

    /**
     * Default constructor for Jackson.
     */
    public RefreshMechanismHTTP() {
    }

    /**
     * Gets the HTTP request definition for refreshing the secret.
     *
     * @return the request definition
     */
    public HTTPDefinition getRequestDefinition() {
        return requestDefinition;
    }

    /**
     * Gets the JSON field name in the response that contains the new secret value.
     *
     * @return the response parameter name
     */
    public String getResponseParamName() {
        return responseParamName;
    }
}
