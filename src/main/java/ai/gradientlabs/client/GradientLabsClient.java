package ai.gradientlabs.client;

import ai.gradientlabs.client.exception.GradientLabsException;
import ai.gradientlabs.client.internal.HttpClientWrapper;
import ai.gradientlabs.client.model.*;
import ai.gradientlabs.client.request.*;
import ai.gradientlabs.client.webhook.Webhook;
import ai.gradientlabs.client.webhook.WebhookVerifier;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Main client for interacting with the Gradient Labs API.
 * <p>
 * Use {@link #builder()} to create a new client instance:
 * <pre>{@code
 * GradientLabsClient client = GradientLabsClient.builder()
 *     .apiKey(System.getenv("GLABS_API_KEY"))
 *     .webhookSigningKey(System.getenv("GLABS_WEBHOOK_KEY"))
 *     .build();
 * }</pre>
 *
 * @see <a href="https://api-docs.gradient-labs.ai">Gradient Labs API Documentation</a>
 */
public class GradientLabsClient {

    private static final String DEFAULT_BASE_URL = "https://api.gradient-labs.ai";
    private static final String USER_AGENT_FORMAT = "Gradient-Labs-Java/%s (Java/%s)";

    private final String baseUrl;
    private final String apiKey;
    private final HttpClientWrapper httpClient;
    private final WebhookVerifier webhookVerifier;
    private final String userAgent;

    /**
     * Private constructor - use {@link #builder()} instead.
     */
    private GradientLabsClient(String baseUrl, String apiKey, HttpClient httpClient,
                               WebhookVerifier webhookVerifier) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.httpClient = new HttpClientWrapper(httpClient, apiKey, getUserAgent());
        this.webhookVerifier = webhookVerifier;
        this.userAgent = getUserAgent();
    }

    private static String getUserAgent() {
        String version = GradientLabsClient.class.getPackage().getImplementationVersion();
        if (version == null) {
            version = "dev";
        }
        String javaVersion = System.getProperty("java.version");
        return String.format(USER_AGENT_FORMAT, version, javaVersion);
    }

    /**
     * Creates a new builder for constructing a {@link GradientLabsClient}.
     *
     * @return a new builder instance
     */
    public static GradientLabsClientBuilder builder() {
        return new GradientLabsClientBuilder();
    }

    // ==================== Conversation Operations ====================

    /**
     * Starts a new conversation.
     *
     * @param request the conversation parameters
     * @return the created conversation
     * @throws GradientLabsException if the request fails
     */
    public Conversation startConversation(StartConversationRequest request) {
        return httpClient.post("/conversations", request, Conversation.class);
    }

    /**
     * Starts a new conversation asynchronously.
     *
     * @param request the conversation parameters
     * @return a future that completes with the created conversation
     */
    public CompletableFuture<Conversation> startConversationAsync(StartConversationRequest request) {
        return httpClient.postAsync("/conversations", request, Conversation.class);
    }

    /**
     * Retrieves a conversation by ID.
     *
     * @param conversationId the conversation ID
     * @param request        optional read parameters
     * @return the conversation
     * @throws GradientLabsException if the request fails
     */
    public Conversation readConversation(String conversationId, ReadConversationRequest request) {
        String path = String.format("/conversations/%s", conversationId);
        return httpClient.get(path, request, Conversation.class);
    }

    /**
     * Adds a message to a conversation.
     *
     * @param conversationId the conversation ID
     * @param request        the message parameters
     * @return the created message
     * @throws GradientLabsException if the request fails
     */
    public Message addMessage(String conversationId, AddMessageRequest request) {
        String path = String.format("/conversations/%s/messages", conversationId);
        return httpClient.post(path, request, Message.class);
    }

    /**
     * Assigns a conversation to a participant.
     *
     * @param conversationId the conversation ID
     * @param request        the assignment parameters
     * @throws GradientLabsException if the request fails
     */
    public void assignConversation(String conversationId, AssignmentRequest request) {
        String path = String.format("/conversations/%s/assign", conversationId);
        httpClient.post(path, request, Void.class);
    }

    /**
     * Adds an event to a conversation.
     *
     * @param conversationId the conversation ID
     * @param request        the event parameters
     * @throws GradientLabsException if the request fails
     */
    public void addConversationEvent(String conversationId, EventRequest request) {
        String path = String.format("/conversations/%s/events", conversationId);
        httpClient.post(path, request, Void.class);
    }

    /**
     * Finishes a conversation.
     *
     * @param conversationId the conversation ID
     * @param request        finish parameters
     * @throws GradientLabsException if the request fails
     */
    public void finishConversation(String conversationId, FinishConversationRequest request) {
        String path = String.format("/conversations/%s/finish", conversationId);
        httpClient.post(path, request, Void.class);
    }

    /**
     * Cancels a conversation.
     *
     * @param conversationId the conversation ID
     * @throws GradientLabsException if the request fails
     */
    public void cancelConversation(String conversationId) {
        String path = String.format("/conversations/%s/cancel", conversationId);
        httpClient.post(path, null, Void.class);
    }

    /**
     * Resumes a conversation.
     *
     * @param conversationId the conversation ID
     * @throws GradientLabsException if the request fails
     */
    public void resumeConversation(String conversationId) {
        String path = String.format("/conversations/%s/resume", conversationId);
        httpClient.post(path, null, Void.class);
    }

    /**
     * Rates a conversation.
     *
     * @param conversationId the conversation ID
     * @param rating         the rating value
     * @throws GradientLabsException if the request fails
     */
    public void rateConversation(String conversationId, int rating) {
        String path = String.format("/conversations/%s/rate", conversationId);
        httpClient.post(path, new RatingRequest(rating), Void.class);
    }

    // ==================== Tool Operations ====================

    /**
     * Lists all tools.
     *
     * @return list of tools
     * @throws GradientLabsException if the request fails
     */
    public List<Tool> listTools() {
        return httpClient.getList("/tools", null, Tool.class);
    }

    /**
     * Creates a new tool.
     *
     * @param request the tool creation parameters
     * @return the created tool
     * @throws GradientLabsException if the request fails
     */
    public Tool createTool(CreateToolRequest request) {
        return httpClient.post("/tools", request, Tool.class);
    }

    /**
     * Retrieves a tool by ID.
     *
     * @param toolId the tool ID
     * @return the tool
     * @throws GradientLabsException if the request fails
     */
    public Tool readTool(String toolId) {
        String path = String.format("/tools/%s", toolId);
        return httpClient.get(path, null, Tool.class);
    }

    /**
     * Updates a tool.
     *
     * @param toolId  the tool ID
     * @param request the update parameters
     * @return the updated tool
     * @throws GradientLabsException if the request fails
     */
    public Tool updateTool(String toolId, UpdateToolRequest request) {
        String path = String.format("/tools/%s", toolId);
        return httpClient.put(path, request, Tool.class);
    }

    /**
     * Deletes a tool.
     *
     * @param toolId the tool ID
     * @throws GradientLabsException if the request fails
     */
    public void deleteTool(String toolId) {
        String path = String.format("/tools/%s", toolId);
        httpClient.delete(path, Void.class);
    }

    // ==================== Webhook Operations ====================

    /**
     * Parses and verifies a webhook request.
     * <p>
     * This method verifies the webhook signature and parses the webhook payload.
     * <pre>{@code
     * Webhook webhook = client.parseWebhook(request);
     * switch (webhook.getType()) {
     *     case AGENT_MESSAGE:
     *         AgentMessageEvent event = webhook.asAgentMessage();
     *         // Handle message...
     *         break;
     *     // Handle other events...
     * }
     * }</pre>
     *
     * @param request the HTTP servlet request
     * @return the parsed webhook
     * @throws GradientLabsException if verification fails or parsing fails
     */
    public Webhook parseWebhook(HttpServletRequest request) {
        return webhookVerifier.parseAndVerify(request);
    }

    /**
     * Verifies a webhook request's signature without parsing.
     *
     * @param request the HTTP servlet request
     * @throws GradientLabsException if verification fails
     */
    public void verifyWebhook(HttpServletRequest request) {
        webhookVerifier.verify(request);
    }

    /**
     * Builder for constructing {@link GradientLabsClient} instances.
     */
    public static class GradientLabsClientBuilder {
        private String baseUrl = DEFAULT_BASE_URL;
        private String apiKey;
        private HttpClient httpClient;
        private String webhookSigningKey;
        private Duration webhookLeeway = Duration.ofMinutes(5);

        private GradientLabsClientBuilder() {
        }

        /**
         * Sets the API key (required).
         *
         * @param apiKey the API key
         * @return this builder
         */
        public GradientLabsClientBuilder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        /**
         * Sets the base URL (optional, defaults to https://api.gradient-labs.ai).
         *
         * @param baseUrl the base URL
         * @return this builder
         */
        public GradientLabsClientBuilder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * Sets a custom HTTP client (optional).
         *
         * @param httpClient the HTTP client
         * @return this builder
         */
        public GradientLabsClientBuilder httpClient(HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        /**
         * Sets the webhook signing key for webhook verification (optional but recommended).
         *
         * @param webhookSigningKey the signing key
         * @return this builder
         */
        public GradientLabsClientBuilder webhookSigningKey(String webhookSigningKey) {
            this.webhookSigningKey = webhookSigningKey;
            return this;
        }

        /**
         * Sets the maximum age of webhooks to accept (optional, defaults to 5 minutes).
         *
         * @param webhookLeeway the leeway duration
         * @return this builder
         */
        public GradientLabsClientBuilder webhookLeeway(Duration webhookLeeway) {
            this.webhookLeeway = webhookLeeway;
            return this;
        }

        /**
         * Builds the client instance.
         *
         * @return a new {@link GradientLabsClient}
         * @throws IllegalStateException if required parameters are missing
         */
        public GradientLabsClient build() {
            if (apiKey == null || apiKey.isBlank()) {
                throw new IllegalStateException("API key is required");
            }

            if (httpClient == null) {
                httpClient = HttpClient.newBuilder()
                        .connectTimeout(Duration.ofSeconds(30))
                        .build();
            }

            WebhookVerifier verifier = webhookSigningKey != null
                    ? new WebhookVerifier(webhookSigningKey, webhookLeeway)
                    : null;

            return new GradientLabsClient(baseUrl, apiKey, httpClient, verifier);
        }
    }

    private static class RatingRequest {
        private final int rating;

        public RatingRequest(int rating) {
            this.rating = rating;
        }

        public int getRating() {
            return rating;
        }
    }
}
