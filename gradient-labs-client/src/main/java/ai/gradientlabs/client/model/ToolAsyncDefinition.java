package ai.gradientlabs.client.model;

import ai.gradientlabs.client.internal.NanosDurationDeserializer;
import ai.gradientlabs.client.internal.NanosDurationSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.Duration;

/**
 * Configuration for running a tool asynchronously.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ToolAsyncDefinition {

    @JsonProperty("start_execution_tool")
    private ToolExecutionDefinition startExecutionTool;

    @JsonProperty("cancel_execution_tool")
    private ToolExecutionDefinition cancelExecutionTool;

    @JsonSerialize(using = NanosDurationSerializer.class)
    @JsonDeserialize(using = NanosDurationDeserializer.class)
    @JsonProperty("timeout")
    private Duration timeout;

    /**
     * Default constructor for Jackson.
     */
    public ToolAsyncDefinition() {
    }

    /**
     * Creates a new instance.
     *
     * @param startExecutionTool how the operation is started.
     * @param cancelExecutionTool how the operation is cancelled, or null if it cannot be.
     * @param timeout the longest to wait for the operation to finish.
     */
    public ToolAsyncDefinition(ToolExecutionDefinition startExecutionTool, ToolExecutionDefinition cancelExecutionTool, Duration timeout) {
        this.startExecutionTool = startExecutionTool;
        this.cancelExecutionTool = cancelExecutionTool;
        this.timeout = timeout;
    }

    /**
     * Returns how the operation is started.
     *
     * @return the start execution tool
     */
    public ToolExecutionDefinition getStartExecutionTool() {
        return startExecutionTool;
    }

    /**
     * Sets how the operation is started.
     *
     * @param startExecutionTool the start execution tool
     */
    public void setStartExecutionTool(ToolExecutionDefinition startExecutionTool) {
        this.startExecutionTool = startExecutionTool;
    }

    /**
     * Returns how the operation is cancelled, or null if it cannot be.
     *
     * @return the cancel execution tool
     */
    public ToolExecutionDefinition getCancelExecutionTool() {
        return cancelExecutionTool;
    }

    /**
     * Sets how the operation is cancelled, or null if it cannot be.
     *
     * @param cancelExecutionTool the cancel execution tool
     */
    public void setCancelExecutionTool(ToolExecutionDefinition cancelExecutionTool) {
        this.cancelExecutionTool = cancelExecutionTool;
    }

    /**
     * Returns the longest to wait for the operation to finish.
     *
     * @return the timeout
     */
    public Duration getTimeout() {
        return timeout;
    }

    /**
     * Sets the longest to wait for the operation to finish.
     *
     * @param timeout the timeout
     */
    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }
}
