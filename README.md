# Gradient Labs Java Client

Java client library for the [Gradient Labs API](https://api-docs.gradient-labs.ai).

## Features

- Idiomatic Java API with fluent builders
- Type-safe models and enums
- Webhook signature verification
- Comprehensive error handling
- Java 11+ compatible
- Zero runtime dependencies (except Jackson)
- Synchronous and asynchronous APIs

## Installation

### Maven

```xml
<dependency>
    <groupId>ai.gradientlabs</groupId>
    <artifactId>gradient-labs-client</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle

```gradle
implementation 'ai.gradientlabs:gradient-labs-client:1.0.0'
```

## Quick Start

### Creating a Client

```java
import ai.gradientlabs.client.GradientLabsClient;

GradientLabsClient client = GradientLabsClient.builder()
    .apiKey(System.getenv("GLABS_API_KEY"))
    .webhookSigningKey(System.getenv("GLABS_WEBHOOK_KEY"))
    .build();
```

### Starting a Conversation

```java
import ai.gradientlabs.client.model.*;
import ai.gradientlabs.client.request.StartConversationRequest;
import java.util.Map;

StartConversationRequest request = StartConversationRequest.builder()
    .id("conversation-1234")
    .customerId("user-1234")
    .channel(Channel.CHAT)
    .addMetadata("source", "web")
    .addResource("user_profile", Map.of(
        "name", "Jane Doe",
        "subscription", "premium"
    ))
    .build();

Conversation conversation = client.startConversation(request);
System.out.println("Started conversation: " + conversation.getId());
```

### Adding a Message

```java
import ai.gradientlabs.client.request.AddMessageRequest;
import java.time.Instant;

AddMessageRequest messageRequest = AddMessageRequest.builder()
    .id("message-1234")
    .body("Hello! I need help with my order.")
    .participantId("user-1234")
    .participantType(ParticipantType.CUSTOMER)
    .created(Instant.now())
    .addMetadata("device_os", "iOS 17")
    .build();

Message message = client.addMessage(conversation.getId(), messageRequest);
```

### Assigning to AI Agent

```java
import ai.gradientlabs.client.request.AssignmentRequest;

client.assignConversation(
    conversation.getId(),
    AssignmentRequest.builder()
        .assigneeType(ParticipantType.AI_AGENT)
        .build()
);
```

### Handling Webhooks

```java
import ai.gradientlabs.client.webhook.*;
import ai.gradientlabs.client.webhook.event.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@PostMapping("/webhooks/gradient-labs")
public void handleWebhook(HttpServletRequest request, HttpServletResponse response) {
    try {
        Webhook webhook = client.parseWebhook(request);

        switch (webhook.getType()) {
            case AGENT_MESSAGE:
                AgentMessageEvent event = webhook.asAgentMessage();
                System.out.println("Agent says: " + event.getBody());
                // Send message to customer...
                break;

            case CONVERSATION_HANDOFF:
                ConversationHandOffEvent handoff = webhook.asConversationHandOff();
                System.out.println("Handoff to: " + handoff.getTarget());
                System.out.println("Note: " + handoff.getNote());
                // Route to human agent...
                break;

            case CONVERSATION_FINISHED:
                ConversationFinishedEvent finished = webhook.asConversationFinished();
                System.out.println("Conversation finished: " +
                    finished.getConversation().getId());
                // Close ticket...
                break;

            case ACTION_EXECUTE:
                ActionExecuteEvent action = webhook.asActionExecute();
                // Execute the action...
                break;

            case RESOURCE_PULL:
                ResourcePullEvent pull = webhook.asResourcePull();
                // Fetch and return resource...
                break;
        }

        response.setStatus(HttpServletResponse.SC_OK);
    } catch (InvalidWebhookSignatureException e) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
```

## API Operations

### Conversations

```java
// Start a conversation
Conversation conv = client.startConversation(request);

// Read a conversation
Conversation conv = client.readConversation("conv-123", ReadConversationRequest.empty());

// Add a message
Message msg = client.addMessage("conv-123", messageRequest);

// Assign to participant
client.assignConversation("conv-123", assignmentRequest);

// Add an event
client.addConversationEvent("conv-123", eventRequest);

// Finish a conversation
client.finishConversation("conv-123", FinishConversationRequest.empty());

// Cancel a conversation
client.cancelConversation("conv-123");

// Resume a conversation
client.resumeConversation("conv-123");

// Rate a conversation
client.rateConversation("conv-123", 5);
```

### Tools

```java
// List tools
List<Tool> tools = client.listTools();

// Create a tool
Tool tool = client.createTool(createRequest);

// Read a tool
Tool tool = client.readTool("tool-123");

// Update a tool
Tool tool = client.updateTool("tool-123", updateRequest);

// Delete a tool
client.deleteTool("tool-123");
```

## Async API

All methods that make HTTP requests have async variants:

```java
import java.util.concurrent.CompletableFuture;

CompletableFuture<Conversation> future = client.startConversationAsync(request);

future.thenAccept(conversation -> {
    System.out.println("Conversation started: " + conversation.getId());
}).exceptionally(error -> {
    System.err.println("Error: " + error.getMessage());
    return null;
});
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

- [Basic Conversation](examples/BasicConversation.java) - Starting and managing conversations
- [Webhook Handler](examples/WebhookHandler.java) - Handling webhook events
- [Tool Management](examples/ToolManagement.java) - Creating and managing tools

## Contributing

Contributions are welcome! Please see [CONTRIBUTING.md](CONTRIBUTING.md) for details.

## License

MIT License - see [LICENSE](LICENSE) for details.

## Support

For issues and questions:
- [GitHub Issues](https://github.com/gradientlabs-ai/java-client/issues)
- [API Documentation](https://api-docs.gradient-labs.ai)
- Email: support@gradient-labs.ai
