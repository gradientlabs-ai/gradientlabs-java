package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * Context from the most recent voice call for a given phone number.
 */
public class VoiceCallContext {

    @JsonProperty("started_at")
    private Instant startedAt;

    @JsonProperty("summary")
    private String summary;

    @JsonProperty("transcript")
    private String transcript;

    @JsonProperty("handoff_reason")
    private String handoffReason;

    @JsonProperty("last_executed_procedure")
    private String lastExecutedProcedure;

    @JsonProperty("last_executed_procedure_url")
    private String lastExecutedProcedureUrl;

    @JsonProperty("gradient_labs_url")
    private String gradientLabsUrl;

    public Instant getStartedAt() {
        return startedAt;
    }

    public String getSummary() {
        return summary;
    }

    public String getTranscript() {
        return transcript;
    }

    public String getHandoffReason() {
        return handoffReason;
    }

    public String getLastExecutedProcedure() {
        return lastExecutedProcedure;
    }

    public String getLastExecutedProcedureUrl() {
        return lastExecutedProcedureUrl;
    }

    public String getGradientLabsUrl() {
        return gradientLabsUrl;
    }
}
