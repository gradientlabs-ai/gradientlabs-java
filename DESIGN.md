# Gradient Labs Java Client - Design Document

## Overview

This document describes the design and architecture of the Gradient Labs Java client library, which provides idiomatic Java bindings for the [Gradient Labs API](https://api-docs.gradient-labs.ai). The design is based on the Go client implementation and follows Java best practices for user-friendly library design.

## Design Principles

Based on research into Java library design best practices and the existing Go client, this library follows these principles:

1. **Minimalism**: Every public API method and class must be justified. Simpler is better.
2. **Builder Pattern**: Use fluent builders for client and complex request objects with 3+ parameters.
3. **Immutability**: Prefer immutable objects where appropriate for thread safety.
4. **Type Safety**: Use enums for constants and strong typing throughout.
5. **Clear Error Handling**: Structured exception hierarchy with actionable error messages.
6. **Intuitive API**: Method names and signatures should be self-documenting.
7. **No Surprises**: Follow Java conventions and familiar patterns (e.g., java.net.http.HttpClient style).

## Package Structure

```
ai.gradientlabs.client/
├── GradientLabsClient.java          # Main client interface
├── GradientLabsClientBuilder.java   # Builder for client creation
│
├── exception/                        # Exception hierarchy
│   ├── GradientLabsException.java
│   ├── ResponseException.java
│   └── InvalidWebhookSignatureException.java
│
├── model/                            # Domain models (POJOs)
│   ├── Conversation.java
│   ├── ConversationStatus.java
│   ├── Channel.java
│   ├── Message.java
│   ├── Attachment.java
│   ├── AttachmentType.java
│   ├── Tool.java
│   ├── Procedure.java
│   ├── Article.java
│   ├── HandoffTarget.java
│   ├── ResourceSource.java
│   ├── ResourceType.java
│   ├── Participant.java
│   ├── ParticipantType.java
│   ├── PaginationInfo.java
│   └── AgentMetadata.java
│
├── request/                          # Request parameter objects
│   ├── StartConversationRequest.java
│   ├── AddMessageRequest.java
│   ├── AssignmentRequest.java
│   ├── EventRequest.java
│   ├── FinishConversationRequest.java
│   ├── ReadConversationRequest.java
│   └── ...
│
├── webhook/                          # Webhook handling
│   ├── Webhook.java
│   ├── WebhookType.java
│   ├── WebhookVerifier.java
│   └── event/
│       ├── AgentMessageEvent.java
│       ├── ConversationHandOffEvent.java
│       ├── ConversationFinishedEvent.java
│       ├── ActionExecuteEvent.java
│       └── ResourcePullEvent.java
│
└── internal/                         # Internal implementation (not public API)
    ├── HttpClientWrapper.java
    └── JsonMapper.java
```

## Key Design Decisions

### 1. Client Creation

**Pattern**: Builder with validation

```java
GradientLabsClient client = GradientLabsClient.builder()
    .apiKey(System.getenv("GLABS_API_KEY"))
    .webhookSigningKey(System.getenv("GLABS_WEBHOOK_KEY"))
    .baseUrl("https://api.gradient-labs.ai")  // Optional
    .httpClient(customHttpClient)              // Optional
    .build();
```

**Rationale**:
- Builder pattern is idiomatic in Java for objects with multiple optional parameters
- Validation happens at build() time with clear error messages
- Fluent API is easy to read and discover

### 2. Request Objects

**Pattern**: Immutable request objects with builders

```java
StartConversationRequest request = StartConversationRequest.builder()
    .id("conversation-1234")
    .customerId("user-1234")
    .channel(Channel.CHAT)
    .metadata(Map.of("source", "web"))
    .build();

Conversation conv = client.startConversation(request);
```

**Rationale**:
- Immutable objects are thread-safe
- Builder pattern for requests with many optional fields
- Clear what parameters are available via IDE autocomplete
- Matches modern Java API design (e.g., HttpRequest)

### 3. Error Handling

**Hierarchy**:
```
GradientLabsException (unchecked)
├── ResponseException
│   └── Contains: statusCode, message, details, traceId
└── InvalidWebhookSignatureException
```

**Rationale**:
- Unchecked exceptions for a client library (most errors are non-recoverable)
- Structured error information for debugging and logging
- TraceID exposed for support requests

### 4. Webhook Handling

**Pattern**: Type-safe event dispatching

```java
Webhook webhook = client.parseWebhook(request);

switch (webhook.getType()) {
    case AGENT_MESSAGE -> {
        AgentMessageEvent event = webhook.asAgentMessage();
        System.out.println("Message: " + event.getBody());
    }
    case CONVERSATION_HANDOFF -> {
        ConversationHandOffEvent event = webhook.asConversationHandOff();
        System.out.println("Handoff to: " + event.getTarget());
    }
    // ...
}
```

**Rationale**:
- Pattern matching (Java 17+) or traditional switch works well
- Type-safe access to event data
- Similar to Go's type assertion pattern but more Java-idiomatic

### 5. JSON Serialization

**Library**: Jackson (industry standard)

**Rationale**:
- Most widely used JSON library in Java
- Excellent performance
- Rich annotation support for customization
- Good integration with modern Java features (Records, Optional, etc.)

### 6. HTTP Client

**Library**: Java 11+ HttpClient

**Rationale**:
- No external dependencies for basic HTTP
- Modern, async-capable API
- Good performance
- Users can provide custom client for advanced needs (observability, etc.)

### 7. Async Support

**Pattern**: Synchronous by default, async variants available

```java
// Synchronous
Conversation conv = client.startConversation(request);

// Async
CompletableFuture<Conversation> future = client.startConversationAsync(request);
```

**Rationale**:
- Most users want synchronous API for simplicity
- Async variants for high-throughput scenarios
- Follows java.net.http.HttpClient pattern

## Model Design

### Immutability

Models are immutable where appropriate:
- Response models (Conversation, Message, etc.) are immutable
- Request models are built with builders and become immutable
- Uses `final` fields and no setters

### Null Handling

- Required fields: Non-null, validated at construction
- Optional fields: `@Nullable` annotations or `Optional<T>`
- Clear documentation of which fields can be null

### Time Handling

- Use `java.time.Instant` for timestamps (ISO-8601 compatible)
- Use `java.time.Duration` for durations (e.g., webhook leeway)

## API Conventions

### Method Naming

- CRUD operations: `create`, `read`, `update`, `delete`
- Special operations: `start`, `finish`, `assign`, `resume`, etc.
- Boolean getters: `isActive()`, `hasMetadata()`

### Parameter Ordering

```java
client.operation(resourceId, request, options)
```

1. Resource identifiers (String, required)
2. Request object (required)
3. Options (optional, varargs or separate method)

## Testing Strategy

1. **Unit Tests**: Test each component in isolation
2. **Integration Tests**: Test against mock HTTP server
3. **Example Code**: Real-world usage examples that serve as tests

## Dependencies

### Required
- Java 11+ (LTS version)
- Jackson (com.fasterxml.jackson.core:jackson-databind)

### Optional
- SLF4J for logging (user provides implementation)

### Build
- Maven or Gradle
- Standard Java project structure

## Versioning

- Follow Semantic Versioning (semver)
- Initial release: 1.0.0
- User-Agent: `Gradient-Labs-Java/{version} (Java/{javaVersion})`

## Documentation

1. **Javadoc**: Complete API documentation with examples
2. **README.md**: Quick start guide
3. **EXAMPLES.md**: Common usage patterns
4. **API Reference**: Link to api-docs.gradient-labs.ai

## Migration from Go

| Go Pattern | Java Pattern |
|------------|-------------|
| `NewClient(opts...)` | `GradientLabsClient.builder()...build()` |
| Functional options | Builder pattern |
| `context.Context` | Method overloads (with/without timeout) |
| `error` return value | Exceptions |
| Struct embedding | Composition |
| Interface{} | `Object` or generics |
| Time.Time | `Instant` |
| Time.Duration | `Duration` |

## Future Enhancements

1. **Pagination Helper**: Iterator-style API for paginated results
2. **Retry Logic**: Configurable retry with exponential backoff
3. **Metrics**: Instrumentation for observability
4. **Async Streaming**: For real-time webhook processing
5. **Spring Boot Starter**: Auto-configuration for Spring apps
