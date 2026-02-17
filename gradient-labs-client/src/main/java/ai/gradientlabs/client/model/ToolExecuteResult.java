package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Result of a tool execution.
 */
public class ToolExecuteResult {

    @JsonProperty("id")
    private String id;

    @JsonProperty("result")
    private JsonNode result;

    @JsonProperty("error")
    private String error;

    /**
     * Creates a new tool execute result.
     */
    public ToolExecuteResult() {
    }

    /**
     * Gets the tool ID.
     *
     * @return the tool ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the tool ID.
     *
     * @param id the tool ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the JSON-encoded result of the tool execution, if it succeeded.
     *
     * @return the result, or null if the execution failed
     */
    public JsonNode getResult() {
        return result;
    }

    /**
     * Sets the result.
     *
     * @param result the result
     */
    public void setResult(JsonNode result) {
        this.result = result;
    }

    /**
     * Gets the error that occurred during the tool execution, if it failed.
     *
     * @return the error message, or null if the execution succeeded
     */
    public String getError() {
        return error;
    }

    /**
     * Sets the error.
     *
     * @param error the error message
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * Checks if the tool execution was successful.
     *
     * @return true if successful (no error), false otherwise
     */
    public boolean isSuccess() {
        return error == null;
    }

    /**
     * Checks if the tool execution failed.
     *
     * @return true if failed (has error), false otherwise
     */
    public boolean isFailure() {
        return error != null;
    }
}
