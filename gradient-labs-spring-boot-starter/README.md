# Gradient Labs Spring Boot Starter

Spring Boot auto-configuration for the [Gradient Labs Java Client](../gradient-labs-client).

This starter provides zero-configuration setup for Spring Boot applications using the Gradient Labs API.

## Features

- **Auto-Configuration**: Automatically creates and configures a `GradientLabsClient` bean
- **Type-Safe Configuration**: Configure via `application.yml` or `application.properties` with IDE autocomplete support
- **Conditional**: Only activates when the core client library is present on the classpath
- **Overridable**: Allows custom bean definitions to override the auto-configured client
- **Spring Boot 3.x**: Built for Spring Boot 3.x and later

## Installation

### Maven

```xml
<dependency>
    <groupId>ai.gradientlabs</groupId>
    <artifactId>gradient-labs-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle

```gradle
implementation 'ai.gradientlabs:gradient-labs-spring-boot-starter:1.0.0'
```

## Configuration

Configure the client in your `application.yml`:

```yaml
gradientlabs:
  api-key: ${GLABS_API_KEY}              # Required
  base-url: https://api.gradient-labs.ai  # Optional, defaults to shown value
  webhook-signing-key: ${GLABS_WEBHOOK_KEY}  # Optional
  webhook-leeway: 10m                     # Optional, defaults to 5m
  enabled: true                           # Optional, defaults to true
```

Or in `application.properties`:

```properties
gradientlabs.api-key=${GLABS_API_KEY}
gradientlabs.base-url=https://api.gradient-labs.ai
gradientlabs.webhook-signing-key=${GLABS_WEBHOOK_KEY}
gradientlabs.webhook-leeway=10m
gradientlabs.enabled=true
```

### Configuration Properties

| Property | Type | Required | Default | Description |
|----------|------|----------|---------|-------------|
| `gradientlabs.api-key` | String | Yes | - | API key for authenticating with the Gradient Labs API |
| `gradientlabs.base-url` | String | No | `https://api.gradient-labs.ai` | Base URL for the API |
| `gradientlabs.webhook-signing-key` | String | No | - | Signing key for webhook verification |
| `gradientlabs.webhook-leeway` | Duration | No | `5m` | Maximum age of webhooks to accept |
| `gradientlabs.enabled` | Boolean | No | `true` | Whether to enable auto-configuration |

### Duration Format

The `webhook-leeway` property accepts various duration formats:

- **ISO-8601**: `PT5M`, `PT10M`, `PT1H`, `P1D`
- **Simple**: `5m`, `10m`, `1h`, `1d`

## Usage

### Basic Dependency Injection

Once configured, the `GradientLabsClient` bean is automatically available for injection:

```java
import ai.gradientlabs.client.GradientLabsClient;
import ai.gradientlabs.client.model.*;
import ai.gradientlabs.client.request.*;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {

    private final GradientLabsClient client;

    // Constructor injection (recommended)
    public ConversationService(GradientLabsClient client) {
        this.client = client;
    }

    public Conversation startConversation(String customerId) {
        return client.startConversation(
            StartConversationRequest.builder()
                .id("conv-" + UUID.randomUUID())
                .customerId(customerId)
                .channel(Channel.CHAT)
                .build()
        );
    }
}
```

### REST Controller Example

```java
import ai.gradientlabs.client.GradientLabsClient;
import ai.gradientlabs.client.webhook.*;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class GradientLabsController {

    private final GradientLabsClient client;

    public GradientLabsController(GradientLabsClient client) {
        this.client = client;
    }

    @PostMapping("/conversations")
    public Conversation createConversation(@RequestBody ConversationRequest request) {
        return client.startConversation(
            StartConversationRequest.builder()
                .id(request.id())
                .customerId(request.customerId())
                .channel(Channel.CHAT)
                .build()
        );
    }

    @PostMapping("/webhooks/gradient-labs")
    public void handleWebhook(HttpServletRequest request) {
        Webhook webhook = client.parseWebhook(request);

        switch (webhook.getType()) {
            case AGENT_MESSAGE:
                handleAgentMessage(webhook.asAgentMessage());
                break;
            case CONVERSATION_HANDOFF:
                handleHandoff(webhook.asConversationHandOff());
                break;
            case CONVERSATION_FINISHED:
                handleFinished(webhook.asConversationFinished());
                break;
            // ... handle other event types
        }
    }

    private void handleAgentMessage(AgentMessageEvent event) {
        // Send message to customer
        System.out.println("Agent says: " + event.getBody());
    }

    private void handleHandoff(ConversationHandOffEvent event) {
        // Route to human agent
        System.out.println("Handoff to: " + event.getTarget());
    }

    private void handleFinished(ConversationFinishedEvent event) {
        // Close ticket
        System.out.println("Conversation finished: " + event.getConversation().getId());
    }
}
```

## Advanced Configuration

### Custom Client Bean

To override the auto-configured client with your own custom configuration:

```java
import ai.gradientlabs.client.GradientLabsClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
@EnableConfigurationProperties(GradientLabsProperties.class)
public class CustomGradientLabsConfig {

    @Bean
    public GradientLabsClient gradientLabsClient(GradientLabsProperties properties) {
        HttpClient customHttpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

        return GradientLabsClient.builder()
            .apiKey(properties.getApiKey())
            .baseUrl(properties.getBaseUrl())
            .httpClient(customHttpClient)
            .build();
    }
}
```

### Disabling Auto-Configuration

To disable the auto-configuration:

```yaml
gradientlabs:
  enabled: false
```

Or exclude it from your Spring Boot application:

```java
@SpringBootApplication(exclude = GradientLabsAutoConfiguration.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

## Environment-Specific Configuration

Use Spring profiles for different environments:

**application-dev.yml:**
```yaml
gradientlabs:
  api-key: ${GLABS_DEV_API_KEY}
  base-url: https://dev-api.gradient-labs.ai
```

**application-prod.yml:**
```yaml
gradientlabs:
  api-key: ${GLABS_PROD_API_KEY}
  base-url: https://api.gradient-labs.ai
  webhook-signing-key: ${GLABS_WEBHOOK_KEY}
```

## Configuration Metadata

The starter includes Spring Boot configuration metadata for IDE autocomplete support. Your IDE (IntelliJ IDEA, Eclipse, VS Code) will provide:

- Property name autocomplete
- Property validation
- Property documentation on hover
- Type checking

## Requirements

- Java 11 or higher
- Spring Boot 3.2.0 or higher
- The core `gradient-labs-client` library (automatically included as a transitive dependency)

## Documentation

- [Main README](../README.md) - Core client documentation
- [API Documentation](https://api-docs.gradient-labs.ai) - Gradient Labs API reference
- [Javadoc](https://javadoc.io/doc/ai.gradientlabs/gradient-labs-client) - Client API reference

## Support

For issues and questions:
- [GitHub Issues](https://github.com/gradientlabs-ai/gradientlabs-java/issues)
- Email: support@gradient-labs.ai
