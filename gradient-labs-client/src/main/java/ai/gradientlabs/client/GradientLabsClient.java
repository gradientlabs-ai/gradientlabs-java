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
     * Starts an outbound conversation.
     * <p>
     * Creates and starts a new outbound conversation where the AI agent proactively
     * initiates contact with a customer. The conversation follows the instructions
     * defined in the specified outbound procedure.
     * <p>
     * If support_platform is not provided, the system will automatically select the
     * highest priority platform that has integration settings configured for your company.
     * <p>
     * If body and subject are provided, that message will be sent as the initial message.
     * Otherwise, the AI agent will generate an appropriate initial message based on the procedure.
     *
     * @param request the outbound conversation parameters
     * @return the response containing the conversation ID
     * @throws GradientLabsException if the request fails
     */
    public ai.gradientlabs.client.response.StartOutboundConversationResponse startOutboundConversation(StartOutboundConversationRequest request) {
        return httpClient.post("/outbound/conversations", request, ai.gradientlabs.client.response.StartOutboundConversationResponse.class);
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

    /**
     * Returns the result of an async tool execution.
     * <p>
     * When a tool is configured for asynchronous execution, the agent will request
     * the tool execution via an action.execute webhook event, and your system should
     * return the result by calling this method.
     * <p>
     * This allows your system to perform long-running operations without blocking the
     * conversation, and return results when they're ready.
     * <p>
     * Important Notes:
     * <ul>
     *   <li>The conversation must be in an ongoing state. You cannot return async tool
     *       results to conversations that are finished, failed, or cancelled.</li>
     *   <li>Make sure to use the correct async tool execution ID from the action.execute
     *       webhook event.</li>
     *   <li>The result payload should be a valid JSON object containing the data the
     *       agent needs to continue the conversation.</li>
     * </ul>
     *
     * @param conversationId the conversation ID
     * @param request        the async tool result parameters
     * @throws GradientLabsException if the request fails
     */
    public void returnAsyncToolResult(String conversationId, ReturnAsyncToolResultRequest request) {
        String path = String.format("/conversations/%s/return-async-tool-result", conversationId);
        httpClient.put(path, request, Void.class);
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

    /**
     * Executes a tool to enable testing.
     * <p>
     * Note: requires a Management API key.
     *
     * @param toolId  the tool ID
     * @param request the execution parameters
     * @return the execution result (check {@link ToolExecuteResult#isSuccess()} to see if it succeeded)
     * @throws GradientLabsException if the request fails
     */
    public ToolExecuteResult executeTool(String toolId, ToolExecuteRequest request) {
        String path = String.format("/tools/%s/execute", toolId);
        return httpClient.post(path, request, ToolExecuteResult.class);
    }

    // ==================== Note Operations ====================

    /**
     * Creates a new note.
     *
     * @param request the note creation parameters
     * @return the created note
     * @throws GradientLabsException if the request fails
     */
    public Note createNote(CreateNoteRequest request) {
        return httpClient.post("/notes", request, Note.class);
    }

    /**
     * Updates an existing note's contents.
     *
     * @param noteId  the note ID
     * @param request the update parameters
     * @return the updated note
     * @throws GradientLabsException if the request fails
     */
    public Note updateNote(String noteId, UpdateNoteRequest request) {
        String path = String.format("/notes/%s", noteId);
        return httpClient.post(path, request, Note.class);
    }

    /**
     * Marks a note as deleted.
     *
     * @param noteId the note ID
     * @throws GradientLabsException if the request fails
     */
    public void deleteNote(String noteId) {
        String path = String.format("/notes/%s", noteId);
        httpClient.delete(path, Void.class);
    }

    /**
     * Updates a note's status.
     *
     * @param noteId  the note ID
     * @param request the status update parameters
     * @throws GradientLabsException if the request fails
     */
    public void setNoteStatus(String noteId, SetNoteStatusRequest request) {
        String path = String.format("/notes/%s/status", noteId);
        httpClient.post(path, request, Void.class);
    }

    // ==================== Secret Operations ====================

    /**
     * Writes (creates or updates) a secret.
     * <p>
     * Secrets provide secure storage for sensitive data like API credentials.
     * If a secret with the given name already exists, it will be updated.
     * Otherwise, a new secret will be created.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param request the secret parameters
     * @return the created or updated secret
     * @throws GradientLabsException if the request fails
     */
    public Secret writeSecret(WriteSecretRequest request) {
        String path = String.format("/secrets/%s", request.getName());
        return httpClient.put(path, request, Secret.class);
    }

    /**
     * Lists all secrets.
     * <p>
     * Returns metadata about all secrets in your organization.
     * Note that the actual secret values are not returned.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @return list of secrets
     * @throws GradientLabsException if the request fails
     */
    public List<Secret> listSecrets() {
        SecretsListResponse response = httpClient.get("/secrets", null, SecretsListResponse.class);
        return response.getSecrets();
    }

    /**
     * Revokes (deletes) a secret.
     * <p>
     * This permanently deletes the secret and cannot be undone.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param request the revoke parameters
     * @throws GradientLabsException if the request fails
     */
    public void revokeSecret(RevokeSecretRequest request) {
        String path = String.format("/secrets/%s", request.getName());
        httpClient.delete(path, Void.class);
    }

    // ==================== Resource Type Operations ====================

    /**
     * Creates a new resource type.
     * <p>
     * Resource types define when and how a resource source's data should be used during
     * a conversation. They determine the scope, refresh strategy, and which attributes
     * from the source should be included.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param request the resource type parameters
     * @return the created resource type
     * @throws GradientLabsException if the request fails
     */
    public ResourceType createResourceType(CreateResourceTypeRequest request) {
        return httpClient.post("/resource-types", request, ResourceType.class);
    }

    /**
     * Lists all resource types.
     * <p>
     * Returns all resource types configured in your organization.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @return list of resource types
     * @throws GradientLabsException if the request fails
     */
    public List<ResourceType> listResourceTypes() {
        ResourceTypesListResponse response = httpClient.get("/resource-types", null, ResourceTypesListResponse.class);
        return response.getResourceTypes();
    }

    /**
     * Retrieves a specific resource type by ID.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param request the read parameters
     * @return the resource type
     * @throws GradientLabsException if the request fails
     */
    public ResourceType readResourceType(ReadResourceTypeRequest request) {
        String path = String.format("/resource-types/%s", request.getId());
        return httpClient.get(path, null, ResourceType.class);
    }

    /**
     * Updates an existing resource type.
     * <p>
     * All fields in the request are optional. If a field is not provided, its value
     * will not be changed. When updating source_config, the entire object must be provided.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param request the update parameters
     * @return the updated resource type
     * @throws GradientLabsException if the request fails
     */
    public ResourceType updateResourceType(UpdateResourceTypeRequest request) {
        String path = String.format("/resource-types/%s", request.getId());
        return httpClient.put(path, request, ResourceType.class);
    }

    /**
     * Deletes a resource type.
     * <p>
     * This permanently deletes the resource type and cannot be undone.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param request the delete parameters
     * @throws GradientLabsException if the request fails
     */
    public void deleteResourceType(DeleteResourceTypeRequest request) {
        String path = String.format("/resource-types/%s", request.getId());
        httpClient.delete(path, Void.class);
    }

    // ==================== Hand-Off Target Operations ====================

    /**
     * Lists all hand-off targets.
     * <p>
     * Hand-off targets define where conversations can be transferred when they need
     * human intervention or routing to other systems.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @return list of hand-off targets
     * @throws GradientLabsException if the request fails
     */
    public List<HandOffTarget> listHandOffTargets() {
        HandOffTargetsResponse response = httpClient.get("/hand-off-targets", null, HandOffTargetsResponse.class);
        return response.getTargets();
    }

    /**
     * Creates or updates a hand-off target.
     * <p>
     * If a hand-off target with the given ID already exists, it will be updated.
     * Otherwise, a new hand-off target will be created.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param request the hand-off target parameters
     * @throws GradientLabsException if the request fails
     */
    public void upsertHandOffTarget(UpsertHandOffTargetRequest request) {
        httpClient.post("/hand-off-targets", request, Void.class);
    }

    /**
     * Deletes a hand-off target.
     * <p>
     * This will fail if the hand-off target is in use - either in a procedure or in an intent.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param request the delete parameters
     * @throws GradientLabsException if the request fails (including if the target is in use)
     */
    public void deleteHandOffTarget(DeleteHandOffTargetRequest request) {
        httpClient.delete("/hand-off-targets", request, Void.class);
    }

    /**
     * Sets the default hand-off target for a channel.
     * <p>
     * Sets the default hand-off target that the AI agent will use when handing off
     * the conversation, if there is no specific target for that intent or procedure.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param request the default target parameters
     * @throws GradientLabsException if the request fails
     */
    public void setDefaultHandOffTarget(SetDefaultHandOffTargetRequest request) {
        httpClient.put("/hand-off-targets/default", request, Void.class);
    }

    /**
     * Gets the current default hand-off target for the company.
     * <p>
     * Retrieves the default hand-off target that the AI agent will use when handing off
     * the conversation, if there is no specific target for that intent or procedure.
     * This can be retrieved by channel.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param request the request parameters including channel
     * @return the response containing the default hand-off target ID (empty if not set)
     * @throws GradientLabsException if the request fails
     */
    public GetDefaultHandOffTargetResponse getDefaultHandOffTarget(GetDefaultHandOffTargetRequest request) {
        String path = "/hand-off-targets/default?channel=" + request.getChannel().getValue();
        return httpClient.get(path, null, GetDefaultHandOffTargetResponse.class);
    }

    // ==================== Article Operations ====================

    /**
     * Creates or updates an article.
     * <p>
     * Articles are documents that the AI agent can work with to answer customer questions.
     * If an article with the given ID already exists, it will be updated.
     * Otherwise, a new article will be created.
     * <p>
     * <strong>Note:</strong> Requires an Integration API key.
     *
     * @param request the article parameters
     * @throws GradientLabsException if the request fails
     */
    public void upsertArticle(UpsertArticleRequest request) {
        httpClient.post("/articles", request, Void.class);
    }

    /**
     * Creates or updates an article topic.
     * <p>
     * Topics enable you to categorize your help articles into groups.
     * If a topic with the given ID already exists, it will be updated.
     * Otherwise, a new topic will be created.
     * <p>
     * <strong>Note:</strong> Requires an Integration API key.
     *
     * @param request the topic parameters
     * @throws GradientLabsException if the request fails
     */
    public void upsertArticleTopic(UpsertArticleTopicRequest request) {
        httpClient.post("/topics", request, Void.class);
    }

    /**
     * Sets an article's usage status.
     * <p>
     * Use this to make an article available or unavailable for use by the AI agent.
     * When set to ON, the article will be available for use. When set to OFF,
     * the article will not be used by the AI agent.
     * <p>
     * <strong>Note:</strong> Requires an Integration API key.
     *
     * @param articleId the article ID
     * @param request   the usage status parameters
     * @throws GradientLabsException if the request fails
     */
    public void setArticleUsageStatus(String articleId, SetArticleUsageStatusRequest request) {
        String path = String.format("/articles/%s/usage-status", articleId);
        httpClient.post(path, request, Void.class);
    }

    /**
     * Deletes an article.
     * <p>
     * Marks an article as deleted. Copies of the article are kept in case
     * they are needed to render citations.
     * <p>
     * <strong>Note:</strong> Requires an Integration API key.
     *
     * @param articleId the article ID
     * @throws GradientLabsException if the request fails
     */
    public void deleteArticle(String articleId) {
        String path = String.format("/articles/%s", articleId);
        httpClient.delete(path, Void.class);
    }

    /**
     * Lists topics.
     * <p>
     * Topics enable you to categorize your help articles into groups.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param request the list request with optional filtering
     * @return the list response containing topics
     * @throws GradientLabsException if the request fails
     */
    public ListTopicsResponse listTopics(ListTopicsRequest request) {
        StringBuilder path = new StringBuilder("/topics");

        if (request.getSupportPlatform() != null) {
            path.append("?support_platform=").append(request.getSupportPlatform());
        }

        return httpClient.get(path.toString(), null, ListTopicsResponse.class);
    }

    /**
     * Reads a topic by ID.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param topicId the topic ID
     * @param request the request with optional support platform parameter
     * @return the topic
     * @throws GradientLabsException if the request fails
     */
    public Topic readTopic(String topicId, ReadTopicRequest request) {
        StringBuilder path = new StringBuilder(String.format("/topic/%s", topicId));

        if (request.getSupportPlatform() != null) {
            path.append("?support_platform=").append(request.getSupportPlatform());
        }

        return httpClient.get(path.toString(), null, Topic.class);
    }

    // ==================== Procedure Operations ====================

    /**
     * Lists procedures.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param request the list request with optional filtering and pagination
     * @return the list response with procedures and pagination info
     * @throws GradientLabsException if the request fails
     */
    public ProcedureListResponse listProcedures(ListProceduresRequest request) {
        StringBuilder path = new StringBuilder("/procedures");
        boolean hasQueryParam = false;

        if (request.getCursor() != null) {
            path.append("?cursor=").append(request.getCursor());
            hasQueryParam = true;
        }

        if (request.getStatus() != null) {
            path.append(hasQueryParam ? "&" : "?");
            path.append("status=").append(request.getStatus().getValue());
        }

        return httpClient.get(path.toString(), null, ProcedureListResponse.class);
    }

    /**
     * Reads a specific procedure by ID.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param procedureId the procedure ID
     * @return the procedure
     * @throws GradientLabsException if the request fails
     */
    public Procedure readProcedure(String procedureId) {
        String path = String.format("/procedure/%s", procedureId);
        return httpClient.get(path, null, Procedure.class);
    }

    /**
     * Sets the daily usage limit for a procedure.
     * <p>
     * Use this to configure experimental procedures to have limited usage per day.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param procedureId the procedure ID
     * @param request the limit configuration
     * @return the updated procedure
     * @throws GradientLabsException if the request fails
     */
    public Procedure setProcedureLimit(String procedureId, SetProcedureLimitRequest request) {
        String path = String.format("/procedure/%s/limit", procedureId);
        return httpClient.post(path, request, Procedure.class);
    }

    /**
     * Lists all versions of a procedure.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param procedureId the procedure ID
     * @return the list of procedure versions
     * @throws GradientLabsException if the request fails
     */
    public ListProcedureVersionsResponse listProcedureVersions(String procedureId) {
        String path = String.format("/procedures/%s/versions", procedureId);
        return httpClient.get(path, null, ListProcedureVersionsResponse.class);
    }

    /**
     * Sets an experimental version for a procedure.
     * <p>
     * Experimental versions allow gradual rollout of new procedure versions with
     * daily conversation limits.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param procedureId the procedure ID
     * @param version the version number to set as experimental
     * @param request the experiment configuration
     * @throws GradientLabsException if the request fails
     */
    public void setProcedureExperimentVersion(String procedureId, int version, SetProcedureExperimentVersionRequest request) {
        String path = String.format("/procedures/%s/versions/%d/set-experiment", procedureId, version);
        httpClient.post(path, request, Void.class);
    }

    /**
     * Unsets the experimental version for a procedure.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param procedureId the procedure ID
     * @param version the version number to unset
     * @throws GradientLabsException if the request fails
     */
    public void unsetProcedureExperimentVersion(String procedureId, int version) {
        String path = String.format("/procedures/%s/versions/%d/unset-experiment", procedureId, version);
        httpClient.post(path, null, Void.class);
    }

    /**
     * Sets the live (production) version for a procedure.
     * <p>
     * The live version is used by the agent by default when there are no
     * experimental versions or all have exceeded their limits.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param procedureId the procedure ID
     * @param version the version number to set as live
     * @throws GradientLabsException if the request fails
     */
    public void setProcedureLiveVersion(String procedureId, int version) {
        String path = String.format("/procedures/%s/versions/%d/set-live", procedureId, version);
        httpClient.post(path, null, Void.class);
    }

    /**
     * Unsets the live version for a procedure.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param procedureId the procedure ID
     * @param version the version number to unset
     * @throws GradientLabsException if the request fails
     */
    public void unsetProcedureLiveVersion(String procedureId, int version) {
        String path = String.format("/procedures/%s/versions/%d/unset-live", procedureId, version);
        httpClient.post(path, null, Void.class);
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
