# Gradient Labs Java Client
[![Maven Central](https://img.shields.io/maven-central/v/ai.gradientlabs/gradient-labs-client.svg)](https://central.sonatype.com/artifact/ai.gradientlabs/gradient-labs-client)
[![Javadoc](https://javadoc.io/badge2/ai.gradientlabs/gradient-labs-client/javadoc.svg)](https://javadoc.io/doc/ai.gradientlabs/gradient-labs-client)

Java client library for the [Gradient Labs API](https://api-docs.gradient-labs.ai).

## Features

- Idiomatic Java API with fluent builders
- Type-safe models and enums
- Webhook signature verification
- Comprehensive error handling
- Java 11+ compatible
- Zero runtime dependencies (except Jackson)

## Spring Boot Integration

For Spring Boot applications, use the starter dependency for zero-configuration setup:

### Maven

```xml
<dependency>
    <groupId>ai.gradientlabs</groupId>
    <artifactId>gradient-labs-spring-boot-starter</artifactId>
    <version>1.0.2</version>
</dependency>
```

### Gradle

```gradle
implementation 'ai.gradientlabs:gradient-labs-spring-boot-starter:1.0.2'
```

### Configuration

Configure in your `application.yml`:

```yaml
gradientlabs:
  api-key: ${GLABS_API_KEY}
  base-url: https://api.gradient-labs.ai  # optional
  webhook-signing-key: ${GLABS_WEBHOOK_KEY}  # optional
  webhook-leeway: 10m  # optional, defaults to 5m
```

Or in `application.properties`:

```properties
gradientlabs.api-key=${GLABS_API_KEY}
gradientlabs.base-url=https://api.gradient-labs.ai
gradientlabs.webhook-signing-key=${GLABS_WEBHOOK_KEY}
gradientlabs.webhook-leeway=10m
```

### Usage

The client will be automatically configured and available for dependency injection:

```java
import ai.gradientlabs.client.GradientLabsClient;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {

    private final GradientLabsClient client;

    public ConversationService(GradientLabsClient client) {
        this.client = client;
    }

    public void startConversation(String customerId) {
        Conversation conv = client.startConversation(
            StartConversationRequest.builder()
                .id("conv-" + UUID.randomUUID())
                .customerId(customerId)
                .channel(Channel.CHAT)
                .build()
        );
    }
}
```

See the [Spring Boot Starter README](gradient-labs-spring-boot-starter/README.md) for more details.

## Installation (Standalone)

For non-Spring applications, use the core client library:

### Maven

```xml
<dependency>
    <groupId>ai.gradientlabs</groupId>
    <artifactId>gradient-labs-client</artifactId>
    <version>1.0.2</version>
</dependency>
```

### Gradle

```gradle
implementation 'ai.gradientlabs:gradient-labs-client:1.0.2'
```

## Error Handling

```java
import ai.gradientlabs.client.exception.*;

try {
    Conversation conv = client.startConversation(request);
} catch (ResponseException e) {
    System.err.println("API error: " + e.getMessage());
    System.err.println("Status code: " + e.getStatusCode());
    System.err.println("Trace ID: " + e.getTraceId());

    if (e.getDetails() != null) {
        System.err.println("Details: " + e.getDetails());
    }
} catch (GradientLabsException e) {
    System.err.println("Client error: " + e.getMessage());
}
```

## Configuration

### Custom HTTP Client

```java
import java.net.http.HttpClient;
import java.time.Duration;

HttpClient customClient = HttpClient.newBuilder()
    .connectTimeout(Duration.ofSeconds(10))
    .followRedirects(HttpClient.Redirect.NORMAL)
    .build();

GradientLabsClient client = GradientLabsClient.builder()
    .apiKey(apiKey)
    .httpClient(customClient)
    .build();
```

### Custom Base URL

```java
GradientLabsClient client = GradientLabsClient.builder()
    .apiKey(apiKey)
    .baseUrl("https://custom-api.example.com")
    .build();
```

### Webhook Configuration

```java
import java.time.Duration;

GradientLabsClient client = GradientLabsClient.builder()
    .apiKey(apiKey)
    .webhookSigningKey(webhookKey)
    .webhookLeeway(Duration.ofMinutes(10))  // Accept webhooks up to 10 minutes old
    .build();
```

## Requirements

- Java 11 or higher
- Jackson 2.18+ for JSON serialization

## Documentation

- [API Documentation](https://api-docs.gradient-labs.ai)
- [Javadoc](https://javadoc.io/doc/ai.gradientlabs/gradient-labs-client)
- [Design Document](DESIGN.md)

## Examples

See the `examples/` directory for complete working examples:

- [Basic Conversation](examples/BasicConversationExample.java) - Starting and managing conversations
- [Webhook Handler](examples/WebhookHandlerExample.java) - Handling webhook events
- [Notes Example](examples/NotesExample.java) - Creating and managing notes
- [Hand-Off Targets Example](examples/HandOffTargetsExample.java) - Managing hand-off targets for conversation routing
- [Articles Example](examples/ArticlesExample.java) - Managing articles and topics for the knowledge base
- [Procedures Example](examples/ProceduresExample.java) - Managing procedures and versions for AI agent instructions

## Contributing

Contributions are welcome! Please see [CONTRIBUTING.md](CONTRIBUTING.md) for details.

## License

MIT License - see [LICENSE](LICENSE) for details.

## Support

For issues and questions:
- [GitHub Issues](https://github.com/gradientlabs-ai/java-client/issues)
- [API Documentation](https://api-docs.gradient-labs.ai)
- Email: support@gradient-labs.ai
