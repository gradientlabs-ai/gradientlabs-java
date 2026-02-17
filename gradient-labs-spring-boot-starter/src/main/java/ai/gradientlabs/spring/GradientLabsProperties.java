package ai.gradientlabs.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * Configuration properties for Gradient Labs API client.
 * <p>
 * Properties can be configured in application.yml or application.properties:
 * <pre>
 * gradientlabs:
 *   api-key: ${GLABS_API_KEY}
 *   base-url: https://api.gradient-labs.ai
 *   webhook-signing-key: ${GLABS_WEBHOOK_KEY}
 *   webhook-leeway: 10m
 *   enabled: true
 * </pre>
 *
 * @see ai.gradientlabs.client.GradientLabsClient
 */
@ConfigurationProperties(prefix = "gradientlabs")
public class GradientLabsProperties {

    /**
     * API key for authenticating with the Gradient Labs API (required).
     */
    private String apiKey;

    /**
     * Base URL for the Gradient Labs API (optional, defaults to https://api.gradient-labs.ai).
     */
    private String baseUrl = "https://api.gradient-labs.ai";

    /**
     * Signing key for webhook signature verification (optional but recommended for webhook handling).
     */
    private String webhookSigningKey;

    /**
     * Maximum age of webhooks to accept (optional, defaults to 5 minutes).
     * <p>
     * Can be specified in various formats:
     * <ul>
     *   <li>ISO-8601 duration format: PT5M, PT10M, P1D</li>
     *   <li>Simple format: 5m, 10m, 1h</li>
     * </ul>
     */
    @DurationUnit(ChronoUnit.MINUTES)
    private Duration webhookLeeway = Duration.ofMinutes(5);

    /**
     * Whether to enable auto-configuration of the Gradient Labs client (optional, defaults to true).
     */
    private boolean enabled = true;

    /**
     * Gets the API key.
     *
     * @return the API key
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Sets the API key.
     *
     * @param apiKey the API key
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Gets the base URL.
     *
     * @return the base URL
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Sets the base URL.
     *
     * @param baseUrl the base URL
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Gets the webhook signing key.
     *
     * @return the webhook signing key
     */
    public String getWebhookSigningKey() {
        return webhookSigningKey;
    }

    /**
     * Sets the webhook signing key.
     *
     * @param webhookSigningKey the webhook signing key
     */
    public void setWebhookSigningKey(String webhookSigningKey) {
        this.webhookSigningKey = webhookSigningKey;
    }

    /**
     * Gets the webhook leeway duration.
     *
     * @return the webhook leeway
     */
    public Duration getWebhookLeeway() {
        return webhookLeeway;
    }

    /**
     * Sets the webhook leeway duration.
     *
     * @param webhookLeeway the webhook leeway
     */
    public void setWebhookLeeway(Duration webhookLeeway) {
        this.webhookLeeway = webhookLeeway;
    }

    /**
     * Checks if auto-configuration is enabled.
     *
     * @return true if enabled, false otherwise
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets whether auto-configuration is enabled.
     *
     * @param enabled true to enable, false to disable
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
