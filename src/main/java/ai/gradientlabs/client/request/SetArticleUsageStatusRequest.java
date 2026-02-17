package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.UsageStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for setting an article's usage status.
 * <p>
 * Use this to make an article available or unavailable for use by the AI agent.
 * <p>
 * <strong>Note:</strong> Requires an Integration API key.
 */
public class SetArticleUsageStatusRequest {

    @JsonProperty("usage_status")
    private final UsageStatus usageStatus;

    /**
     * Creates a request to set an article's usage status.
     *
     * @param usageStatus the usage status (ON or OFF)
     */
    public SetArticleUsageStatusRequest(UsageStatus usageStatus) {
        if (usageStatus == null) {
            throw new IllegalArgumentException("usageStatus is required");
        }
        this.usageStatus = usageStatus;
    }

    public UsageStatus getUsageStatus() {
        return usageStatus;
    }
}
