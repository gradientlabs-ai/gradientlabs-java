import ai.gradientlabs.client.GradientLabsClient;
import ai.gradientlabs.client.model.*;
import ai.gradientlabs.client.request.*;

import java.time.Instant;
import java.util.Map;

/**
 * Example demonstrating basic conversation operations.
 */
public class BasicConversationExample {

    public static void main(String[] args) {
        // Create client
        GradientLabsClient client = GradientLabsClient.builder()
                .apiKey(System.getenv("GLABS_API_KEY"))
                .webhookSigningKey(System.getenv("GLABS_WEBHOOK_KEY"))
                .build();

        try {
            // Start a conversation
            System.out.println("Starting conversation...");
            StartConversationRequest startRequest = StartConversationRequest.builder()
                    .id("conversation-" + System.currentTimeMillis())
                    .customerId("user-1234")
                    .channel(Channel.CHAT)
                    .addMetadata("source", "web")
                    .addMetadata("entrypoint", "home-page")
                    .addResource("user_profile", Map.of(
                            "name", "Jane Doe",
                            "email", "jane@example.com",
                            "subscription", "premium"
                    ))
                    .addResource("transaction", Map.of(
                            "id", 123456,
                            "amount", 99.99,
                            "status", "completed"
                    ))
                    .build();

            Conversation conversation = client.startConversation(startRequest);
            System.out.println("✓ Conversation started: " + conversation.getId());
            System.out.println("  Status: " + conversation.getStatus());
            System.out.println("  Channel: " + conversation.getChannel());

            // Add a customer message
            System.out.println("\nAdding customer message...");
            AddMessageRequest messageRequest = AddMessageRequest.builder()
                    .id("message-" + System.currentTimeMillis())
                    .body("Hello! I need help with my recent order.")
                    .participantId("user-1234")
                    .participantType(ParticipantType.CUSTOMER)
                    .created(Instant.now())
                    .addMetadata("device_os", "iOS 17")
                    .addMetadata("app_version", "2.1.0")
                    .addAttachment(new Attachment(
                            AttachmentType.IMAGE,
                            "order-receipt.jpg",
                            "Receipt for order #123456"
                    ))
                    .build();

            Message message = client.addMessage(conversation.getId(), messageRequest);
            System.out.println("✓ Message added: " + message.getId());
            System.out.println("  Body: " + message.getBody());

            // Assign to AI agent
            System.out.println("\nAssigning to AI agent...");
            AssignmentRequest assignmentRequest = AssignmentRequest.builder()
                    .assigneeType(ParticipantType.AI_AGENT)
                    .build();

            client.assignConversation(conversation.getId(), assignmentRequest);
            System.out.println("✓ Assigned to AI agent");

            // Add an internal note (event)
            System.out.println("\nAdding internal note...");
            EventRequest eventRequest = EventRequest.builder()
                    .type("internal_note")
                    .participantId("system")
                    .participantType(ParticipantType.HUMAN)
                    .body("Customer has premium subscription - prioritize response")
                    .build();

            client.addConversationEvent(conversation.getId(), eventRequest);
            System.out.println("✓ Internal note added");

            // Wait a bit for the agent to process
            System.out.println("\nWaiting for agent to process...");
            Thread.sleep(2000);

            // Read the conversation
            System.out.println("\nReading conversation...");
            Conversation updatedConv = client.readConversation(
                    conversation.getId(),
                    ReadConversationRequest.builder()
                            .includeMessages(true)
                            .build()
            );
            System.out.println("✓ Conversation read");
            System.out.println("  Status: " + updatedConv.getStatus());
            System.out.println("  Agent active: " + updatedConv.isAgentActive());
            if (updatedConv.getAgentMetadata() != null) {
                System.out.println("  Intent: " + updatedConv.getAgentMetadata().getIntent());
            }

            // Finish the conversation
            System.out.println("\nFinishing conversation...");
            FinishConversationRequest finishRequest = FinishConversationRequest.builder()
                    .reason("issue_resolved")
                    .build();

            client.finishConversation(conversation.getId(), finishRequest);
            System.out.println("✓ Conversation finished");

            // Rate the conversation
            System.out.println("\nRating conversation...");
            client.rateConversation(conversation.getId(), 5);
            System.out.println("✓ Conversation rated: 5 stars");

            System.out.println("\n✅ Example completed successfully!");

        } catch (Exception e) {
            System.err.println("\n❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
