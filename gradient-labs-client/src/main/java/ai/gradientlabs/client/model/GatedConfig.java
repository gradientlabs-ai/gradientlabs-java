package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Configuration for gated procedure versions.
 * <p>
 * Defines daily limits for testing new procedure versions before full rollout.
 */
public class GatedConfig {

    @JsonProperty("max_daily_conversations")
    private int maxDailyConversations;

    /**
     * Gets the maximum number of conversations per day that can use the gated version.
     *
     * @return the maximum daily conversations
     */
    public int getMaxDailyConversations() {
        return maxDailyConversations;
    }

    /**
     * Sets the maximum number of conversations per day.
     *
     * @param maxDailyConversations the maximum daily conversations
     */
    public void setMaxDailyConversations(int maxDailyConversations) {
        this.maxDailyConversations = maxDailyConversations;
    }

    @Override
    public String toString() {
        return "GatedConfig{" +
                "maxDailyConversations=" + maxDailyConversations +
                '}';
    }
}
