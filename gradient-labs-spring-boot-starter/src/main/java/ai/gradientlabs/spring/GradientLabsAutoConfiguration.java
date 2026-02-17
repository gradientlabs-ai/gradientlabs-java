package ai.gradientlabs.spring;

import ai.gradientlabs.client.GradientLabsClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Auto-configuration for Gradient Labs API client.
 * <p>
 * This auto-configuration will be activated when:
 * <ul>
 *   <li>{@link GradientLabsClient} is on the classpath</li>
 *   <li>The property {@code gradientlabs.enabled} is not set to {@code false}</li>
 * </ul>
 * <p>
 * The auto-configuration creates a {@link GradientLabsClient} bean configured from properties.
 * Users can override this by defining their own {@link GradientLabsClient} bean.
 * <p>
 * Example configuration in application.yml:
 * <pre>
 * gradientlabs:
 *   api-key: ${GLABS_API_KEY}
 *   base-url: https://api.gradient-labs.ai
 *   webhook-signing-key: ${GLABS_WEBHOOK_KEY}
 *   webhook-leeway: 10m
 * </pre>
 *
 * @see GradientLabsProperties
 * @see GradientLabsClient
 */
@AutoConfiguration
@ConditionalOnClass(GradientLabsClient.class)
@ConditionalOnProperty(prefix = "gradientlabs", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(GradientLabsProperties.class)
public class GradientLabsAutoConfiguration {

    /**
     * Creates a {@link GradientLabsClient} bean from the configured properties.
     * <p>
     * This bean will only be created if no other {@link GradientLabsClient} bean is already defined,
     * allowing users to provide their own custom configuration if needed.
     *
     * @param properties the Gradient Labs configuration properties
     * @return a configured {@link GradientLabsClient} instance
     * @throws IllegalStateException if the API key is not configured
     */
    @Bean
    @ConditionalOnMissingBean
    public GradientLabsClient gradientLabsClient(GradientLabsProperties properties) {
        if (properties.getApiKey() == null || properties.getApiKey().isBlank()) {
            throw new IllegalStateException(
                    "Gradient Labs API key must be configured. " +
                    "Set the property 'gradientlabs.api-key' in your application configuration or " +
                    "set the environment variable GLABS_API_KEY."
            );
        }

        GradientLabsClient.GradientLabsClientBuilder builder = GradientLabsClient.builder()
                .apiKey(properties.getApiKey());

        // Set optional base URL if provided and different from default
        if (properties.getBaseUrl() != null && !properties.getBaseUrl().isBlank()) {
            builder.baseUrl(properties.getBaseUrl());
        }

        // Set optional webhook signing key if provided
        if (properties.getWebhookSigningKey() != null && !properties.getWebhookSigningKey().isBlank()) {
            builder.webhookSigningKey(properties.getWebhookSigningKey());
        }

        // Set optional webhook leeway if provided
        if (properties.getWebhookLeeway() != null) {
            builder.webhookLeeway(properties.getWebhookLeeway());
        }

        return builder.build();
    }
}
