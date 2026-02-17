package ai.gradientlabs.example.springboot;

import ai.gradientlabs.client.GradientLabsClient;
import ai.gradientlabs.client.model.Channel;
import ai.gradientlabs.client.model.Conversation;
import ai.gradientlabs.client.model.Message;
import ai.gradientlabs.client.model.ParticipantType;
import ai.gradientlabs.client.request.AddMessageRequest;
import ai.gradientlabs.client.request.AssignmentRequest;
import ai.gradientlabs.client.request.StartConversationRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

/**
 * Service demonstrating usage of the auto-configured GradientLabsClient.
 * <p>
 * The client is automatically injected by Spring Boot and configured
 * from application.yml properties.
 */
@Service
public class ConversationService {

    private final GradientLabsClient client;

    /**
     * Constructor injection of the auto-configured client.
     * Spring Boot automatically provides the GradientLabsClient bean.
     */
    public ConversationService(GradientLabsClient client) {
        this.client = client;
    }

    /**
     * Starts a new conversation with the given customer ID.
     *
     * @param customerId the customer ID
     * @return the created conversation
     */
    public Conversation startConversation(String customerId) {
        StartConversationRequest request = StartConversationRequest.builder()
                .id("conv-" + UUID.randomUUID())
                .customerId(customerId)
                .channel(Channel.CHAT)
                .addMetadata("source", "spring-boot-example")
                .build();

        return client.startConversation(request);
    }

    /**
     * Adds a customer message to a conversation.
     *
     * @param conversationId the conversation ID
     * @param customerId the customer ID
     * @param messageBody the message text
     * @return the created message
     */
    public Message addCustomerMessage(String conversationId, String customerId, String messageBody) {
        AddMessageRequest request = AddMessageRequest.builder()
                .id("msg-" + UUID.randomUUID())
                .body(messageBody)
                .participantId(customerId)
                .participantType(ParticipantType.CUSTOMER)
                .created(Instant.now())
                .build();

        return client.addMessage(conversationId, request);
    }

    /**
     * Assigns the conversation to an AI agent.
     *
     * @param conversationId the conversation ID
     */
    public void assignToAI(String conversationId) {
        AssignmentRequest request = AssignmentRequest.builder()
                .assigneeType(ParticipantType.AI_AGENT)
                .build();

        client.assignConversation(conversationId, request);
    }
}
