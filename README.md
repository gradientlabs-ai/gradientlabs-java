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

### Notes

```java
import ai.gradientlabs.client.model.Note;
import ai.gradientlabs.client.model.NoteStatus;
import ai.gradientlabs.client.request.CreateNoteRequest;
import ai.gradientlabs.client.request.UpdateNoteRequest;
import ai.gradientlabs.client.request.SetNoteStatusRequest;
import java.time.Duration;
import java.time.Instant;

// Create a note
Note note = client.createNote(
    CreateNoteRequest.builder()
        .externalId("note-001")
        .title("Support Hours")
        .body("Our support team is available Monday-Friday, 9am-5pm EST")
        .startTime(Instant.now())
        .endTime(Instant.now().plus(Duration.ofDays(365)))
        .build()
);

// Update a note
Note updated = client.updateNote(
    note.getId(),
    UpdateNoteRequest.builder()
        .title("Updated Support Hours")
        .body("Our support team is now available 24/7")
        .build()
);

// Set note status
client.setNoteStatus(
    note.getId(),
    SetNoteStatusRequest.builder()
        .status(NoteStatus.LIVE)
        .build()
);

// Delete a note
client.deleteNote(note.getId());
```

### Hand-Off Targets

**Note:** All hand-off target operations require a Management API key.

```java
import ai.gradientlabs.client.model.Channel;
import ai.gradientlabs.client.model.HandOffTarget;
import ai.gradientlabs.client.request.UpsertHandOffTargetRequest;
import ai.gradientlabs.client.request.DeleteHandOffTargetRequest;
import ai.gradientlabs.client.request.SetDefaultHandOffTargetRequest;
import java.util.List;

// List all hand-off targets
List<HandOffTarget> targets = client.listHandOffTargets();

// Create or update a hand-off target
client.upsertHandOffTarget(
    UpsertHandOffTargetRequest.builder()
        .id("support-team")
        .name("Customer Support Team")
        .build()
);

// Set default hand-off target for a channel
client.setDefaultHandOffTarget(
    SetDefaultHandOffTargetRequest.builder()
        .id("support-team")
        .channel(Channel.CHAT)
        .build()
);

// Clear default hand-off target (set to empty string)
client.setDefaultHandOffTarget(
    SetDefaultHandOffTargetRequest.builder()
        .id("")
        .channel(Channel.EMAIL)
        .build()
);

// Delete a hand-off target (fails if target is in use)
client.deleteHandOffTarget(
    DeleteHandOffTargetRequest.builder()
        .id("support-team")
        .build()
);
```

### Articles

**Note:** All article operations require an Integration API key.

```java
import ai.gradientlabs.client.model.*;
import ai.gradientlabs.client.request.*;
import java.time.Instant;

// Create or update a topic
client.upsertArticleTopic(
    UpsertArticleTopicRequest.builder()
        .id("account-management")
        .name("Account Management")
        .description("How to manage your account")
        .visibility(Visibility.PUBLIC)
        .status(PublicationStatus.PUBLISHED)
        .created(Instant.now())
        .lastEdited(Instant.now())
        .build()
);

// Create or update an article
client.upsertArticle(
    UpsertArticleRequest.builder()
        .id("change-address")
        .authorId("author@example.com")
        .title("Change my address")
        .description("Learn how to update your address")
        .body("Go to settings and tap 'update my address.'")
        .visibility(Visibility.PUBLIC)
        .topicId("account-management")
        .status(PublicationStatus.PUBLISHED)
        .created(Instant.now())
        .lastEdited(Instant.now())
        .addData("reading_time", "2 minutes")
        .build()
);

// Set article usage status (enable for AI agent)
client.setArticleUsageStatus(
    "change-address",
    new SetArticleUsageStatusRequest(UsageStatus.ON)
);

// Disable article from AI agent use
client.setArticleUsageStatus(
    "change-address",
    new SetArticleUsageStatusRequest(UsageStatus.OFF)
);

// Delete an article
client.deleteArticle("change-address");
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

- [Basic Conversation](examples/BasicConversationExample.java) - Starting and managing conversations
- [Webhook Handler](examples/WebhookHandlerExample.java) - Handling webhook events
- [Notes Example](examples/NotesExample.java) - Creating and managing notes
- [Hand-Off Targets Example](examples/HandOffTargetsExample.java) - Managing hand-off targets for conversation routing
- [Articles Example](examples/ArticlesExample.java) - Managing articles and topics for the knowledge base

## Contributing

Contributions are welcome! Please see [CONTRIBUTING.md](CONTRIBUTING.md) for details.

## License

MIT License - see [LICENSE](LICENSE) for details.

## Support

For issues and questions:
- [GitHub Issues](https://github.com/gradientlabs-ai/java-client/issues)
- [API Documentation](https://api-docs.gradient-labs.ai)
- Email: support@gradient-labs.ai
