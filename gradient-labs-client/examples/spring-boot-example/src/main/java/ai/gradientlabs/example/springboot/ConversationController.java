package ai.gradientlabs.example.springboot;

import ai.gradientlabs.client.GradientLabsClient;
import ai.gradientlabs.client.model.Conversation;
import ai.gradientlabs.client.model.Message;
import ai.gradientlabs.client.webhook.Webhook;
import ai.gradientlabs.client.webhook.WebhookType;
import ai.gradientlabs.client.webhook.event.AgentMessageEvent;
import ai.gradientlabs.client.webhook.event.ConversationFinishedEvent;
import ai.gradientlabs.client.webhook.event.ConversationHandOffEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * REST controller demonstrating Spring Boot integration with Gradient Labs.
 * <p>
 * Shows how to:
 * - Inject the auto-configured GradientLabsClient
 * - Use the client in REST endpoints
 * - Handle webhooks with automatic signature verification
 */
@RestController
@RequestMapping("/api")
public class ConversationController {

    private final ConversationService conversationService;
    private final GradientLabsClient client;

    public ConversationController(ConversationService conversationService, GradientLabsClient client) {
        this.conversationService = conversationService;
        this.client = client;
    }

    /**
     * Creates a new conversation.
     * Example: POST /api/conversations with body: {"customerId": "user-123"}
     */
    @PostMapping("/conversations")
    public ResponseEntity<ConversationResponse> createConversation(@RequestBody CreateConversationRequest request) {
        Conversation conversation = conversationService.startConversation(request.customerId);
        return ResponseEntity.ok(new ConversationResponse(
                conversation.getId(),
                conversation.getCustomerId(),
                conversation.getStatus().toString()
        ));
    }

    /**
     * Adds a message to a conversation.
     * Example: POST /api/conversations/{id}/messages with body: {"customerId": "user-123", "message": "Hello"}
     */
    @PostMapping("/conversations/{conversationId}/messages")
    public ResponseEntity<MessageResponse> addMessage(
            @PathVariable String conversationId,
            @RequestBody AddMessageRequest request) {

        Message message = conversationService.addCustomerMessage(
                conversationId,
                request.customerId,
                request.message
        );

        // Assign to AI after customer message
        conversationService.assignToAI(conversationId);

        return ResponseEntity.ok(new MessageResponse(
                message.getId(),
                message.getBody(),
                message.getParticipantType().toString()
        ));
    }

    /**
     * Webhook endpoint for receiving Gradient Labs events.
     * The client automatically verifies the webhook signature.
     * Example: POST /api/webhooks/gradient-labs
     */
    @PostMapping("/webhooks/gradient-labs")
    public ResponseEntity<Void> handleWebhook(HttpServletRequest request) {
        try {
            // Parse and verify webhook
            Webhook webhook = client.parseWebhook(request);

            // Handle different event types
            switch (webhook.getType()) {
                case AGENT_MESSAGE:
                    AgentMessageEvent agentMessage = webhook.asAgentMessage();
                    System.out.println("Agent message: " + agentMessage.getBody());
                    // Send message to customer via your messaging system
                    break;

                case CONVERSATION_HANDOFF:
                    ConversationHandOffEvent handoff = webhook.asConversationHandOff();
                    System.out.println("Handoff to: " + handoff.getTarget());
                    System.out.println("Note: " + handoff.getNote());
                    // Route to appropriate human agent
                    break;

                case CONVERSATION_FINISHED:
                    ConversationFinishedEvent finished = webhook.asConversationFinished();
                    System.out.println("Conversation finished: " + finished.getConversation().getId());
                    // Clean up, close ticket, etc.
                    break;

                case ACTION_EXECUTE:
                    System.out.println("Action execute event received");
                    // Execute the requested action
                    break;

                case RESOURCE_PULL:
                    System.out.println("Resource pull event received");
                    // Fetch and return requested resource
                    break;

                default:
                    System.out.println("Unknown webhook type: " + webhook.getType());
            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.err.println("Webhook error: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Request/Response DTOs

    record CreateConversationRequest(String customerId) {}

    record AddMessageRequest(String customerId, String message) {}

    record ConversationResponse(String id, String customerId, String status) {}

    record MessageResponse(String id, String body, String participantType) {}
}
