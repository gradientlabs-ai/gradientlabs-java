package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.Argument;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Request to execute a tool.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ToolExecuteRequest {

    @JsonProperty("arguments")
    private List<Argument> arguments;

    @JsonProperty("token")
    private String token;

    /**
     * Creates a new tool execute request.
     */
    public ToolExecuteRequest() {
        this.arguments = new ArrayList<>();
    }

    /**
     * Creates a new tool execute request with the given arguments.
     *
     * @param arguments the arguments to execute the tool with
     */
    public ToolExecuteRequest(List<Argument> arguments) {
        this.arguments = arguments != null ? arguments : new ArrayList<>();
    }

    /**
     * Gets the arguments to execute the tool with.
     *
     * @return the arguments
     */
    public List<Argument> getArguments() {
        return arguments;
    }

    /**
     * Sets the arguments to execute the tool with.
     *
     * @param arguments the arguments
     */
    public void setArguments(List<Argument> arguments) {
        this.arguments = arguments;
    }

    /**
     * Adds an argument.
     *
     * @param name  the parameter name
     * @param value the parameter value
     * @return this request for chaining
     */
    public ToolExecuteRequest addArgument(String name, String value) {
        this.arguments.add(new Argument(name, value));
        return this;
    }

    /**
     * Returns the conversation token to run the tool with, if it needs one.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
