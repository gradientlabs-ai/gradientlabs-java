package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * A back office task processed by an AI agent.
 */
public class BackOfficeTask {

    @JsonProperty("id")
    private String id;

    @JsonProperty("agent_id")
    private String agentId;

    @JsonProperty("status")
    private BackOfficeTaskStatus status;

    @JsonProperty("input")
    private Object input;

    @JsonProperty("created")
    private Instant created;

    @JsonProperty("metadata")
    private Map<String, Object> metadata;

    @JsonProperty("attachments")
    private List<BackOfficeTaskAttachment> attachments;

    @JsonProperty("updated")
    private Instant updated;

    @JsonProperty("completed")
    private Instant completed;

    @JsonProperty("failed")
    private Instant failed;

    @JsonProperty("failure_reasons")
    private List<String> failureReasons;

    @JsonProperty("handed_off")
    private Instant handedOff;

    @JsonProperty("hand_off_reason")
    private String handOffReason;

    @JsonProperty("result")
    private BackOfficeTaskResult result;

    public String getId() {
        return id;
    }

    public String getAgentId() {
        return agentId;
    }

    public BackOfficeTaskStatus getStatus() {
        return status;
    }

    public Object getInput() {
        return input;
    }

    public Instant getCreated() {
        return created;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public List<BackOfficeTaskAttachment> getAttachments() {
        return attachments;
    }

    public Instant getUpdated() {
        return updated;
    }

    public Instant getCompleted() {
        return completed;
    }

    public Instant getFailed() {
        return failed;
    }

    public List<String> getFailureReasons() {
        return failureReasons;
    }

    public Instant getHandedOff() {
        return handedOff;
    }

    public String getHandOffReason() {
        return handOffReason;
    }

    public BackOfficeTaskResult getResult() {
        return result;
    }
}
