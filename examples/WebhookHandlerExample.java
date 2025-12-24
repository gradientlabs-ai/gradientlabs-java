import ai.gradientlabs.client.GradientLabsClient;
import ai.gradientlabs.client.exception.InvalidWebhookSignatureException;
import ai.gradientlabs.client.webhook.Webhook;
import ai.gradientlabs.client.webhook.event.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Example webhook handler for processing Gradient Labs webhook events.
 * <p>
 * This example uses the Servlet API, but the same principles apply to
 * other frameworks like Spring Boot, Micronaut, etc.
 */
public class WebhookHandlerExample extends HttpServlet {

    private final GradientLabsClient client;

    public WebhookHandlerExample() {
        this.client = GradientLabsClient.builder()
                .apiKey(System.getenv("GLABS_API_KEY"))
                .webhookSigningKey(System.getenv("GLABS_WEBHOOK_KEY"))
                .build();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            // Parse and verify the webhook
            Webhook webhook = client.parseWebhook(request);

            System.out.println("Received webhook: " + webhook.getType());
            System.out.println("Event ID: " + webhook.getId());
            System.out.println("Sequence: " + webhook.getSequenceNumber());

            // Handle different webhook types
            switch (webhook.getType()) {
                case AGENT_MESSAGE:
                    handleAgentMessage(webhook.asAgentMessage());
                    break;

                case CONVERSATION_HANDOFF:
                    handleConversationHandOff(webhook.asConversationHandOff());
                    break;

                case CONVERSATION_FINISHED:
                    handleConversationFinished(webhook.asConversationFinished());
                    break;

                case ACTION_EXECUTE:
                    handleActionExecute(webhook.asActionExecute());
                    break;

                case RESOURCE_PULL:
                    handleResourcePull(webhook.asResourcePull());
                    break;

                default:
                    System.out.println("Unknown webhook type: " + webhook.getType());
            }

            // Always return 200 OK for successfully processed webhooks
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"status\":\"ok\"}");

        } catch (InvalidWebhookSignatureException e) {
            // Invalid signature - respond with 401
            System.err.println("Invalid webhook signature: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"invalid_signature\"}");

        } catch (Exception e) {
            // Other errors - respond with 500
            System.err.println("Error processing webhook: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"internal_error\"}");
        }
    }

    private void handleAgentMessage(AgentMessageEvent event) {
        System.out.println("\n=== Agent Message ===");
        System.out.println("Conversation: " + event.getConversation().getId());
        System.out.println("Customer: " + event.getConversation().getCustomerId());
        System.out.println("Message: " + event.getBody());
        System.out.println("Sequence: " + event.getSequence() + " of " + event.getTotal());

        if (event.getIntent() != null) {
            System.out.println("Intent: " + event.getIntent());
        }

        // TODO: Send this message to your customer via your chat system
        // sendMessageToCustomer(
        //     event.getConversation().getCustomerId(),
        //     event.getBody()
        // );
    }

    private void handleConversationHandOff(ConversationHandOffEvent event) {
        System.out.println("\n=== Conversation Hand-Off ===");
        System.out.println("Conversation: " + event.getConversation().getId());
        System.out.println("Target: " + event.getTarget());
        System.out.println("Reason: " + event.getReason());
        System.out.println("Description: " + event.getDescription());

        if (event.getNote() != null) {
            System.out.println("Note: " + event.getNote());
        }

        // TODO: Route this conversation to a human agent
        // routeToHumanAgent(
        //     event.getConversation().getId(),
        //     event.getTarget(),
        //     event.getNote()
        // );
    }

    private void handleConversationFinished(ConversationFinishedEvent event) {
        System.out.println("\n=== Conversation Finished ===");
        System.out.println("Conversation: " + event.getConversation().getId());

        if (event.getReason() != null) {
            System.out.println("Reason: " + event.getReason());
        }

        // TODO: Close the ticket/conversation in your system
        // closeTicket(event.getConversation().getId());
    }

    private void handleActionExecute(ActionExecuteEvent event) {
        System.out.println("\n=== Action Execute ===");
        System.out.println("Conversation: " + event.getConversation().getId());
        System.out.println("Action: " + event.getAction());
        System.out.println("Parameters: " + event.getParams());

        // TODO: Execute the requested action
        // String result = executeAction(event.getAction(), event.getParams());
        //
        // Then report the result back:
        // client.addMessage(
        //     event.getConversation().getId(),
        //     AddMessageRequest.builder()
        //         .id("action-result-" + System.currentTimeMillis())
        //         .body("Action result: " + result)
        //         .participantType(ParticipantType.HUMAN)
        //         .build()
        // );
    }

    private void handleResourcePull(ResourcePullEvent event) {
        System.out.println("\n=== Resource Pull ===");
        System.out.println("Conversation: " + event.getConversation().getId());
        System.out.println("Resource Type: " + event.getResourceType());

        // TODO: Fetch the requested resource and add it to the conversation
        // Object resource = fetchResource(event.getResourceType());
        //
        // client.addConversationResource(
        //     event.getConversation().getId(),
        //     event.getResourceType(),
        //     resource
        // );
    }
}
