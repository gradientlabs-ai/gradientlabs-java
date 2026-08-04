# Gradient Labs Spring Boot Example

This example demonstrates how to use the Gradient Labs Spring Boot Starter for zero-configuration integration.

## Features Demonstrated

- **Auto-configuration**: GradientLabsClient bean automatically configured from properties
- **Dependency injection**: Client injected into services and controllers
- **REST endpoints**: Creating conversations and messages via REST API
- **Webhook handling**: Receiving and processing Gradient Labs webhook events
- **Type-safe configuration**: Using application.yml for configuration

## Running the Example

### Prerequisites

- Java 17 or higher
- Maven
- Gradient Labs API key

### Steps

1. **Set your API key**:
   ```bash
   export GLABS_API_KEY=your-api-key-here
   export GLABS_WEBHOOK_KEY=your-webhook-key-here  # Optional
   ```

2. **Build the parent project** (from repository root):
   ```bash
   cd ../../..
   mvn clean install
   ```

3. **Run the application**:
   ```bash
   cd gradient-labs-client/examples/spring-boot-example
   mvn spring-boot:run
   ```

   The application will start on http://localhost:8080

## API Endpoints

### Create Conversation

```bash
curl -X POST http://localhost:8080/api/conversations \
  -H "Content-Type: application/json" \
  -d '{"customerId": "user-123"}'
```

Response:
```json
{
  "id": "conv-...",
  "customerId": "user-123",
  "status": "OPEN"
}
```

### Add Message

```bash
curl -X POST http://localhost:8080/api/conversations/{conversationId}/messages \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "user-123",
    "message": "Hello! I need help."
  }'
```

Response:
```json
{
  "id": "msg-...",
  "body": "Hello! I need help.",
  "participantType": "CUSTOMER"
}
```

### Webhook Endpoint

The webhook endpoint is available at:
```
POST http://localhost:8080/api/webhooks/gradient-labs
```

Configure this URL in your Gradient Labs dashboard to receive webhook events.

## Configuration

The application is configured via `application.yml`:

```yaml
gradientlabs:
  api-key: ${GLABS_API_KEY}
  base-url: https://api.gradient-labs.ai
  webhook-signing-key: ${GLABS_WEBHOOK_KEY:}
  webhook-leeway: 10m
  enabled: true
```

You can override these properties:
- Via environment variables
- Via command line: `--gradientlabs.api-key=your-key`
- Via application-{profile}.yml files

## Code Structure

- `GradientLabsSpringBootExampleApplication` - Main Spring Boot application
- `ConversationService` - Business logic demonstrating client usage
- `ConversationController` - REST API and webhook handler
- `application.yml` - Configuration properties

## Key Points

1. **Zero Configuration**: No manual bean creation needed - the starter handles it
2. **Constructor Injection**: Use constructor injection for testability
3. **Type Safety**: Configuration properties are type-safe with IDE support
4. **Webhook Verification**: Automatic signature verification via the client

## Testing Webhooks Locally

To test webhooks locally, you can use a tool like [ngrok](https://ngrok.com/):

```bash
# Start ngrok
ngrok http 8080

# Configure the ngrok URL in your Gradient Labs dashboard:
# https://your-ngrok-url.ngrok.io/api/webhooks/gradient-labs
```

## Next Steps

- Customize the conversation flow for your use case
- Add database persistence for conversations and messages
- Implement custom error handling
- Add authentication/authorization
- Deploy to production (e.g., AWS, GCP, Azure)
