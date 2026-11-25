package ai.gradientlabs.client;

import ai.gradientlabs.client.exception.GradientLabsException;
import ai.gradientlabs.client.internal.HttpClientWrapper;
import ai.gradientlabs.client.model.*;
import ai.gradientlabs.client.request.*;
import ai.gradientlabs.client.response.StartOutboundConversationResponse;
import ai.gradientlabs.client.webhook.Webhook;
import ai.gradientlabs.client.webhook.WebhookVerifier;

import jakarta.servlet.http.HttpServletRequest;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        this.httpClient = new HttpClientWrapper(httpClient, baseUrl, apiKey, getUserAgent());
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
     * Starts an outbound live chat conversation.
     * <p>
     * Creates and starts a new outbound live chat conversation in which the AI agent
     * proactively initiates contact with a customer, following the instructions defined in
     * the specified outbound procedure.
     * <p>
     * If a body is provided, that message will be sent as the opening message. Otherwise,
     * the AI agent will generate one based on the procedure.
     * <p>
     * The customer is created, or matched to an existing record, from the customer ID and
     * any customer support platform identifiers you supply. The platform the chat is
     * delivered on needs an identifier for that customer.
     *
     * @param request the outbound chat conversation parameters
     * @return the response containing the conversation ID
     * @throws GradientLabsException if the request fails
     */
    public StartOutboundConversationResponse startOutboundChatConversation(StartOutboundChatConversationRequest request) {
        return httpClient.post("/outbound/conversations/chat", request, StartOutboundConversationResponse.class);
    }

    /**
     * Starts an outbound email conversation.
     * <p>
     * Creates and starts a new outbound email conversation in which the AI agent
     * proactively initiates contact with a customer, following the instructions defined in
     * the specified outbound procedure.
     * <p>
     * If a subject and body are provided, that email will be sent as the opening message.
     * Otherwise, the AI agent will write one based on the procedure.
     * <p>
     * The customer is created, or matched to an existing record, from the customer ID and
     * any customer support platform identifiers you supply. The platform the email is sent
     * from needs an identifier for that customer, so sending from Zendesk needs a Zendesk
     * identifier, and so on.
     *
     * @param request the outbound email conversation parameters
     * @return the response containing the conversation ID
     * @throws GradientLabsException if the request fails
     */
    public StartOutboundConversationResponse startOutboundEmailConversation(StartOutboundEmailConversationRequest request) {
        return httpClient.post("/outbound/conversations/email", request, StartOutboundConversationResponse.class);
    }

    /**
     * Places an outbound phone call.
     * <p>
     * Places an outbound phone call in which the AI agent proactively contacts a customer,
     * following the instructions defined in the specified outbound procedure.
     * <p>
     * The number the call is placed from must already be provisioned for your company.
     * <p>
     * The customer is created, or matched to an existing record, from the customer ID and
     * any customer support platform identifiers you supply. The dialled number is recorded
     * against that same customer.
     *
     * @param request the outbound phone conversation parameters
     * @return the response containing the conversation ID
     * @throws GradientLabsException if the request fails
     */
    public StartOutboundConversationResponse startOutboundPhoneConversation(StartOutboundPhoneConversationRequest request) {
        return httpClient.post("/outbound/conversations/phone", request, StartOutboundConversationResponse.class);
    }

    /**
     * Retrieves a conversation by ID.
     *
     * @param conversationId the conversation ID
     * @param request        optional read parameters, or null
     * @return the conversation
     * @throws GradientLabsException if the request fails
     */
    public Conversation readConversation(String conversationId, ReadConversationRequest request) {
        String path = String.format("/conversations/%s/read", seg(conversationId));
        Map<String, Object> query = new LinkedHashMap<>();
        if (request != null) {
            query.put("support_platform", request.getSupportPlatform());
        }
        return httpClient.get(path, query, Conversation.class);
    }

    /**
     * Retrieves a conversation by ID.
     *
     * @param conversationId the conversation ID
     * @return the conversation
     * @throws GradientLabsException if the request fails
     */
    public Conversation readConversation(String conversationId) {
        return readConversation(conversationId, null);
    }

    /**
     * Retrieves a conversation by ID using the older {@code GET /conversations/{id}} endpoint.
     *
     * @param conversationId the conversation ID
     * @return the conversation
     * @throws GradientLabsException if the request fails
     * @deprecated use {@link #readConversation(String)}
     */
    @Deprecated
    public Conversation readConversationDeprecated(String conversationId) {
        String path = String.format("/conversations/%s", seg(conversationId));
        return httpClient.get(path, null, Conversation.class);
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
        String path = String.format("/conversations/%s/messages", seg(conversationId));
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
        String path = String.format("/conversations/%s/assignee", seg(conversationId));
        httpClient.put(path, request, Void.class);
    }

    /**
     * Adds an event to a conversation.
     *
     * @param conversationId the conversation ID
     * @param request        the event parameters
     * @throws GradientLabsException if the request fails
     */
    public void addConversationEvent(String conversationId, EventRequest request) {
        String path = String.format("/conversations/%s/events", seg(conversationId));
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
        String path = String.format("/conversations/%s/finish", seg(conversationId));
        httpClient.put(path, request, Void.class);
    }

    /**
     * Cancels a conversation.
     *
     * @param conversationId the conversation ID
     * @param request        optional cancel parameters (timestamp, reason)
     * @throws GradientLabsException if the request fails
     */
    public void cancelConversation(String conversationId, CancelConversationRequest request) {
        String path = String.format("/conversations/%s/cancel", seg(conversationId));
        httpClient.put(path, request, Void.class);
    }

    /**
     * Resumes a finished conversation by reassigning it.
     *
     * @param conversationId the conversation ID
     * @param request        resume parameters (assigneeType required)
     * @throws GradientLabsException if the request fails
     */
    public void resumeConversation(String conversationId, ResumeConversationRequest request) {
        String path = String.format("/conversations/%s/resume", seg(conversationId));
        httpClient.put(path, request, Void.class);
    }

    /**
     * Submits a customer (CSAT) rating for a conversation.
     *
     * @param conversationId the conversation ID
     * @param request        the rating parameters (type, value, maxValue, minValue required)
     * @throws GradientLabsException if the request fails
     */
    public void rateConversation(String conversationId, RateConversationRequest request) {
        String path = String.format("/conversations/%s/rate", seg(conversationId));
        httpClient.put(path, request, Void.class);
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
        String path = String.format("/conversations/%s/return-async-tool-result", seg(conversationId));
        httpClient.put(path, request, Void.class);
    }

    /**
     * Deletes a conversation.
     * <p>
     * Deletion is performed asynchronously; a successful call is acknowledged
     * with a 202 Accepted response.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param conversationId the conversation ID
     * @throws GradientLabsException if the request fails
     */
    public void deleteConversation(String conversationId) {
        String path = String.format("/conversations/%s", seg(conversationId));
        httpClient.delete(path, Void.class);
    }

    // ==================== Customer Memory Operations ====================

    /**
     * Experimental. Available to selected workspaces.
     * <p>
     * Lists the memories stored for a customer.
     *
     * @param customerId your identifier for the customer
     * @return the customer's memories
     * @throws GradientLabsException if the request fails
     */
    public List<CustomerMemory> listCustomerMemories(String customerId) {
        String path = String.format("/customers/%s/memories", seg(customerId));
        CustomerMemoriesListResponse response = httpClient.get(path, null, CustomerMemoriesListResponse.class);
        return response.getMemories();
    }

    /**
     * Experimental. Available to selected workspaces.
     * <p>
     * Batch-creates a set of memories scoped to a customer.
     * <p>
     * Each memory's {@code data} payload is stored verbatim as arbitrary JSON for
     * the AI agent to search over on demand. The batch is created asynchronously:
     * the call returns as soon as the job is accepted and yields no result. A
     * {@link GradientLabsException} carrying HTTP 409 is thrown when a batch is
     * already being created for the same customer.
     *
     * @param customerId the customer ID
     * @param request    the memories to create
     * @throws GradientLabsException if the request fails
     * @deprecated use {@link #batchCreateCustomerMemoriesV2(String, BatchCreateCustomerMemoriesV2Request)},
     * which also takes a category description
     */
    @Deprecated
    public void batchCreateCustomerMemories(String customerId, BatchCreateCustomerMemoriesRequest request) {
        String path = String.format("/customers/%s/memories", seg(customerId));
        httpClient.post(path, request, Void.class);
    }

    /**
     * Experimental. Available to selected workspaces.
     * <p>
     * Batch-creates a set of memories scoped to a customer, each with a category name and description.
     * <p>
     * The batch is created asynchronously: the call returns as soon as the job is accepted. A
     * {@link GradientLabsException} carrying HTTP 409 is thrown when a batch is already being
     * created for the same customer.
     *
     * @param customerId your identifier for the customer
     * @param request    the memories to create
     * @throws GradientLabsException if the request fails
     */
    public void batchCreateCustomerMemoriesV2(String customerId, BatchCreateCustomerMemoriesV2Request request) {
        String path = String.format("/customers/%s/memories/v2", seg(customerId));
        httpClient.post(path, request, Void.class);
    }

    // ==================== Tool Operations ====================

    /**
     * Lists all tools.
     *
     * @return list of tools
     * @throws GradientLabsException if the request fails
     */
    public List<Tool> listTools() {
        ToolsListResponse response = httpClient.get("/tools", null, ToolsListResponse.class);
        return response.getTools();
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
        return readTool(toolId, null);
    }

    /**
     * Retrieves a given version of a tool.
     *
     * @param toolId  the tool ID
     * @param version the version to read, or null for the latest
     * @return the tool
     * @throws GradientLabsException if the request fails
     */
    public Tool readTool(String toolId, Long version) {
        String path = String.format("/tools/%s", seg(toolId));
        Map<String, Object> query = new LinkedHashMap<>();
        query.put("version", version);
        return httpClient.get(path, query, Tool.class);
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
        String path = String.format("/tools/%s", seg(toolId));
        return httpClient.put(path, request, Tool.class);
    }

    /**
     * Deletes a tool.
     *
     * @param toolId the tool ID
     * @throws GradientLabsException if the request fails
     */
    public void deleteTool(String toolId) {
        String path = String.format("/tools/%s", seg(toolId));
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
        String path = String.format("/tools/%s/execute", seg(toolId));
        return httpClient.post(path, request, ToolExecuteResult.class);
    }

    // ==================== Note Operations ====================

    /**
     * Lists the first page of notes.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @return the first page of notes
     * @throws GradientLabsException if the request fails
     */
    public NoteListResponse listNotes() {
        return listNotes(null);
    }

    /**
     * Lists a page of notes.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param cursor the cursor from a previous page's pagination, or null for the first page
     * @return the page of notes
     * @throws GradientLabsException if the request fails
     */
    public NoteListResponse listNotes(String cursor) {
        Map<String, Object> query = new LinkedHashMap<>();
        query.put("cursor", cursor);
        return httpClient.get("/notes", query, NoteListResponse.class);
    }

    /**
     * Reads a note.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param noteId your identifier for the note
     * @return the note
     * @throws GradientLabsException if the request fails
     */
    public Note readNote(String noteId) {
        String path = String.format("/notes/%s", seg(noteId));
        return httpClient.get(path, null, Note.class);
    }

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
        String path = String.format("/notes/%s", seg(noteId));
        return httpClient.post(path, request, Note.class);
    }

    /**
     * Marks a note as deleted.
     *
     * @param noteId the note ID
     * @throws GradientLabsException if the request fails
     */
    public void deleteNote(String noteId) {
        String path = String.format("/notes/%s", seg(noteId));
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
        String path = String.format("/notes/%s/status", seg(noteId));
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
        String path = String.format("/secrets/%s", seg(request.getName()));
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
        String path = String.format("/secrets/%s", seg(request.getName()));
        httpClient.delete(path, Void.class);
    }

    // ==================== Resource Source Operations ====================

    /**
     * Creates a new resource source.
     * <p>
     * Resource sources define how to fetch the data for a resource. They can be configured
     * to fetch data via HTTP requests or webhooks.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param request the resource source parameters
     * @return the created resource source
     * @throws GradientLabsException if the request fails
     */
    public ResourceSource createResourceSource(CreateResourceSourceRequest request) {
        return httpClient.post("/resource-sources", request, ResourceSource.class);
    }

    /**
     * Lists all resource sources.
     * <p>
     * Returns all resource sources configured in your organization.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @return list of resource sources
     * @throws GradientLabsException if the request fails
     */
    public List<ResourceSource> listResourceSources() {
        ResourceSourcesListResponse response = httpClient.get("/resource-sources", null, ResourceSourcesListResponse.class);
        return response.getResourceSources();
    }

    /**
     * Reads a specific resource source by ID.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param request the request containing the resource source ID
     * @return the resource source
     * @throws GradientLabsException if the request fails
     */
    public ResourceSource readResourceSource(ReadResourceSourceRequest request) {
        String path = String.format("/resource-sources/%s", seg(request.getId()));
        return httpClient.get(path, null, ResourceSource.class);
    }

    /**
     * Updates an existing resource source.
     * <p>
     * All fields except id are optional. If a field is not provided, its value will not be changed.
     * Note: source_type cannot be updated once set. To change the source_type, you must create a new resource source.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param request the update parameters
     * @return the updated resource source
     * @throws GradientLabsException if the request fails
     */
    public ResourceSource updateResourceSource(UpdateResourceSourceRequest request) {
        String path = String.format("/resource-sources/%s", seg(request.getId()));
        return httpClient.put(path, request, ResourceSource.class);
    }

    /**
     * Updates a resource source schema by providing example data payloads.
     * <p>
     * Instead of manually defining the JSON schema structure, you send representative examples
     * of the data your resource source returns, and the system automatically infers the schema
     * from these examples.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param request the request containing examples and update strategy
     * @return the updated resource source
     * @throws GradientLabsException if the request fails
     */
    public ResourceSource updateResourceSourceSchemaByExamples(UpdateResourceSourceSchemaByExamplesRequest request) {
        return updateResourceSourceSchemaByExamples(request.getId(), request);
    }

    /** Updates a resource source schema from example payloads. */
    public ResourceSource updateResourceSourceSchemaByExamples(String id, UpdateResourceSourceSchemaByExamplesRequest request) {
        String path = String.format("/resource-sources/%s/schema-by-examples", seg(id));
        return httpClient.post(path, request, ResourceSource.class);
    }

    /**
     * Deletes a resource source.
     * <p>
     * This will permanently delete the resource source and cannot be undone.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param request the request containing the resource source ID to delete
     * @throws GradientLabsException if the request fails
     */
    public void deleteResourceSource(DeleteResourceSourceRequest request) {
        String path = String.format("/resource-sources/%s", seg(request.getId()));
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
        String path = String.format("/resource-types/%s", seg(request.getId()));
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
        String path = String.format("/resource-types/%s", seg(request.getId()));
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
        String path = String.format("/resource-types/%s", seg(request.getId()));
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
        httpClient.delete("/hand-off-targets", Map.of("id", request.getId()), Void.class);
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
        return httpClient.get("/hand-off-targets/default", Map.of("channel", request.getChannel().getValue()),
                GetDefaultHandOffTargetResponse.class);
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
        String path = String.format("/articles/%s/usage-status", seg(articleId));
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
        String path = String.format("/articles/%s", seg(articleId));
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
        Map<String, Object> query = new LinkedHashMap<>();
        if (request != null) {
            query.put("support_platform", request.getSupportPlatform());
        }
        return httpClient.get("/topics", query, ListTopicsResponse.class);
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
        String path = String.format("/topic/%s", seg(topicId));
        Map<String, Object> query = new LinkedHashMap<>();
        if (request != null) {
            query.put("support_platform", request.getSupportPlatform());
        }
        return httpClient.get(path, query, Topic.class);
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
        Map<String, Object> query = new LinkedHashMap<>();
        if (request != null) {
            query.put("cursor", request.getCursor());
            query.put("status", request.getStatus() == null ? null : request.getStatus().getValue());
        }
        return httpClient.get("/procedures", query, ProcedureListResponse.class);
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
        String path = String.format("/procedure/%s", seg(procedureId));
        return httpClient.get(path, null, Procedure.class);
    }

    /**
     * Sets the daily usage limit for a procedure.
     * <p>
     * Use this to configure gated procedures to have limited usage per day.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param procedureId the procedure ID
     * @param request the limit configuration
     * @return the updated procedure
     * @throws GradientLabsException if the request fails
     */
    public Procedure setProcedureLimit(String procedureId, SetProcedureLimitRequest request) {
        String path = String.format("/procedure/%s/limit", seg(procedureId));
        return httpClient.post(path, request, ProcedureLimitResponse.class).procedure();
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
        String path = String.format("/procedures/%s/versions", seg(procedureId));
        return httpClient.get(path, null, ListProcedureVersionsResponse.class);
    }

    /**
     * Sets a gated version for a procedure.
     * <p>
     * Gated versions allow gradual rollout of new procedure versions with
     * daily conversation limits.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param procedureId the procedure ID
     * @param version the version number to set as gated
     * @param request the gated version configuration
     * @throws GradientLabsException if the request fails
     */
    public void setProcedureGatedVersion(String procedureId, int version, SetProcedureGatedVersionRequest request) {
        String path = String.format("/procedures/%s/versions/%d/set-gated", seg(procedureId), version);
        httpClient.post(path, request, Void.class);
    }

    /**
     * Unsets the gated version for a procedure.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param procedureId the procedure ID
     * @param version the version number to unset
     * @throws GradientLabsException if the request fails
     */
    public void unsetProcedureGatedVersion(String procedureId, int version) {
        String path = String.format("/procedures/%s/versions/%d/unset-gated", seg(procedureId), version);
        httpClient.post(path, null, Void.class);
    }

    /**
     * Sets the live (production) version for a procedure.
     * <p>
     * The live version is used by the agent by default when there are no
     * gated versions or all have exceeded their limits.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param procedureId the procedure ID
     * @param version the version number to set as live
     * @throws GradientLabsException if the request fails
     */
    public void setProcedureLiveVersion(String procedureId, int version) {
        String path = String.format("/procedures/%s/versions/%d/set-live", seg(procedureId), version);
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
        String path = String.format("/procedures/%s/versions/%d/unset-live", seg(procedureId), version);
        httpClient.post(path, null, Void.class);
    }

    // ==================== Traffic Group Operations ====================

    /**
     * Creates a new traffic group.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param request the traffic group creation parameters
     * @return the created traffic group
     * @throws GradientLabsException if the request fails
     */
    public TrafficGroup createTrafficGroup(CreateTrafficGroupRequest request) {
        return httpClient.post("/traffic-groups", request, TrafficGroup.class);
    }

    /**
     * Lists all traffic groups.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @return list of traffic groups
     * @throws GradientLabsException if the request fails
     */
    public List<TrafficGroup> listTrafficGroups() {
        TrafficGroupsListResponse response = httpClient.get("/traffic-groups", null, TrafficGroupsListResponse.class);
        return response.getTrafficGroups();
    }

    /**
     * Updates an existing traffic group.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param request the update parameters
     * @return the updated traffic group
     * @throws GradientLabsException if the request fails
     */
    public TrafficGroup updateTrafficGroup(UpdateTrafficGroupRequest request) {
        String path = String.format("/traffic-groups/%s", seg(request.getId()));
        return httpClient.put(path, request, TrafficGroup.class);
    }

    /**
     * Deletes a traffic group and all associated targets.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param trafficGroupId the traffic group ID to delete
     * @throws GradientLabsException if the request fails
     */
    public void deleteTrafficGroup(String trafficGroupId) {
        String path = String.format("/traffic-groups/%s", seg(trafficGroupId));
        httpClient.delete(path, Void.class);
    }

    /**
     * Adds a target to a traffic group.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param request the target creation parameters
     * @return the created traffic group target
     * @throws GradientLabsException if the request fails
     */
    public TrafficGroupTarget createTrafficGroupTarget(CreateTrafficGroupTargetRequest request) {
        return createTrafficGroupTarget(request.getGroupId(), request);
    }

    /** Adds a target to a traffic group. */
    public TrafficGroupTarget createTrafficGroupTarget(String groupId, CreateTrafficGroupTargetRequest request) {
        String path = String.format("/traffic-groups/%s/targets", seg(groupId));
        return httpClient.post(path, request, TrafficGroupTarget.class);
    }

    /**
     * Removes a target from a traffic group.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param trafficGroupId the traffic group ID
     * @param targetId       the target ID to remove
     * @throws GradientLabsException if the request fails
     */
    public void deleteTrafficGroupTarget(String trafficGroupId, String targetId) {
        String path = String.format("/traffic-groups/%s/targets/%s", seg(trafficGroupId), seg(targetId));
        httpClient.delete(path, Void.class);
    }

    /**
     * Adds an exclusion to a traffic group.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param groupId the traffic group ID
     * @param request the exclusion parameters
     * @return the created traffic group target representing the exclusion
     * @throws GradientLabsException if the request fails
     */
    public TrafficGroupTarget createTrafficGroupExclusion(String groupId, CreateTrafficGroupExclusionRequest request) {
        String path = String.format("/traffic-groups/%s/exclusions", seg(groupId));
        return httpClient.post(path, request, TrafficGroupTarget.class);
    }

    /**
     * Removes an exclusion from a traffic group.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param groupId  the traffic group ID
     * @param targetId the exclusion target ID to remove
     * @throws GradientLabsException if the request fails
     */
    public void deleteTrafficGroupExclusion(String groupId, String targetId) {
        String path = String.format("/traffic-groups/%s/exclusions/%s", seg(groupId), seg(targetId));
        httpClient.delete(path, Void.class);
    }

    // ==================== IP Address Operations ====================

    /**
     * Lists the IP address ranges used by the Gradient Labs platform.
     * <p>
     * Use this to configure your firewall or allowlist to permit traffic from Gradient Labs.
     *
     * @return the platform IP addresses (api ranges and egress ranges)
     * @throws GradientLabsException if the request fails
     */
    public IPAddresses listIpAddresses() {
        return httpClient.get("/ip-addresses", null, IPAddresses.class);
    }

    // ==================== Knowledge Base Operations ====================

    /**
     * Lists the knowledge bases created through the API.
     * <p>
     * <strong>Note:</strong> Works with an Integration or a Management API key.
     *
     * @return the knowledge bases
     * @throws GradientLabsException if the request fails
     */
    public List<KnowledgeBase> listKnowledgeBases() {
        KnowledgeBasesListResponse response = httpClient.get("/knowledge-bases", null, KnowledgeBasesListResponse.class);
        return response.getKnowledgeBases();
    }

    /**
     * Creates a knowledge base that articles and notes can be attached to.
     * <p>
     * <strong>Note:</strong> Works with an Integration or a Management API key.
     *
     * @param request the knowledge base parameters
     * @return the created knowledge base
     * @throws GradientLabsException if the request fails
     */
    public KnowledgeBase createKnowledgeBase(CreateKnowledgeBaseRequest request) {
        return httpClient.post("/knowledge-bases", request, KnowledgeBase.class);
    }

    /**
     * Reads a knowledge base by your identifier for it.
     * <p>
     * <strong>Note:</strong> Works with an Integration or a Management API key.
     *
     * @param knowledgeBaseId your identifier for the knowledge base
     * @return the knowledge base
     * @throws GradientLabsException if the request fails
     */
    public KnowledgeBase readKnowledgeBase(String knowledgeBaseId) {
        String path = String.format("/knowledge-bases/%s", seg(knowledgeBaseId));
        return httpClient.get(path, null, KnowledgeBase.class);
    }

    /**
     * Renames a knowledge base.
     * <p>
     * <strong>Note:</strong> Works with an Integration or a Management API key.
     *
     * @param knowledgeBaseId your identifier for the knowledge base
     * @param request         the update parameters
     * @return the updated knowledge base
     * @throws GradientLabsException if the request fails
     */
    public KnowledgeBase updateKnowledgeBase(String knowledgeBaseId, UpdateKnowledgeBaseRequest request) {
        String path = String.format("/knowledge-bases/%s", seg(knowledgeBaseId));
        return httpClient.post(path, request, KnowledgeBase.class);
    }

    // ==================== File Operations ====================

    /**
     * Starts uploading a file to the workspace.
     * <p>
     * PUT the file to the returned {@link FileUpload#getUploadUrl()} with its headers, then call
     * {@link #createFile(CreateFileRequest)} with the upload ID.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param request the upload parameters
     * @return where and how to upload the file
     * @throws GradientLabsException if the request fails
     */
    public FileUpload beginFileUpload(BeginFileUploadRequest request) {
        return httpClient.post("/file-uploads", request, FileUpload.class);
    }

    /**
     * Turns a finished upload into a workspace file.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param request the file parameters
     * @return the created file
     * @throws GradientLabsException if the request fails
     */
    public WorkspaceFile createFile(CreateFileRequest request) {
        return httpClient.post("/files", request, WorkspaceFile.class);
    }

    /**
     * Lists the first page of workspace files.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @return the first page of files
     * @throws GradientLabsException if the request fails
     */
    public FileListResponse listFiles() {
        return listFiles(null);
    }

    /**
     * Lists a page of workspace files.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param cursor the cursor from a previous page's pagination, or null for the first page
     * @return the page of files
     * @throws GradientLabsException if the request fails
     */
    public FileListResponse listFiles(String cursor) {
        Map<String, Object> query = new LinkedHashMap<>();
        query.put("cursor", cursor);
        return httpClient.get("/files", query, FileListResponse.class);
    }

    // ==================== Terminology Substitution Operations ====================

    /**
     * Creates a new terminology substitution rule.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param request the substitution parameters
     * @return the created terminology substitution
     * @throws GradientLabsException if the request fails
     */
    public TerminologySubstitution createTerminologySubstitution(CreateTerminologySubstitutionRequest request) {
        return httpClient.post("/terminology-substitutions", request, TerminologySubstitution.class);
    }

    /**
     * Lists all terminology substitution rules.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @return list of terminology substitutions
     * @throws GradientLabsException if the request fails
     */
    public List<TerminologySubstitution> listTerminologySubstitutions() {
        TerminologySubstitutionsListResponse response = httpClient.get("/terminology-substitutions", null,
                TerminologySubstitutionsListResponse.class);
        return response.getSubstitutions();
    }

    /**
     * Retrieves a terminology substitution rule by ID.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param substitutionId the substitution ID
     * @return the terminology substitution
     * @throws GradientLabsException if the request fails
     */
    public TerminologySubstitution readTerminologySubstitution(String substitutionId) {
        String path = String.format("/terminology-substitutions/%s", seg(substitutionId));
        return httpClient.get(path, null, TerminologySubstitution.class);
    }

    /**
     * Updates an existing terminology substitution rule.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param substitutionId the substitution ID
     * @param request        the update parameters
     * @return the updated terminology substitution
     * @throws GradientLabsException if the request fails
     */
    public TerminologySubstitution updateTerminologySubstitution(String substitutionId, UpdateTerminologySubstitutionRequest request) {
        String path = String.format("/terminology-substitutions/%s", seg(substitutionId));
        return httpClient.put(path, request, TerminologySubstitution.class);
    }

    /**
     * Deletes a terminology substitution rule.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param substitutionId the substitution ID to delete
     * @throws GradientLabsException if the request fails
     */
    public void deleteTerminologySubstitution(String substitutionId) {
        String path = String.format("/terminology-substitutions/%s", seg(substitutionId));
        httpClient.delete(path, Void.class);
    }

    // ==================== Back Office Task Operations ====================

    /**
     * Creates a new back office task for an AI agent to process.
     *
     * @param request the task creation parameters
     * @return the created back office task
     * @throws GradientLabsException if the request fails
     */
    public BackOfficeTask createBackOfficeTask(CreateBackOfficeTaskRequest request) {
        return httpClient.post("/back-office-tasks", request, BackOfficeTask.class);
    }

    /**
     * Retrieves a back office task by ID.
     *
     * @param taskId the task ID
     * @return the back office task
     * @throws GradientLabsException if the request fails
     */
    public BackOfficeTask readBackOfficeTask(String taskId) {
        String path = String.format("/back-office-tasks/%s/read", seg(taskId));
        return httpClient.get(path, null, BackOfficeTask.class);
    }

    /**
     * Deletes a back office task.
     * <p>
     * Deletion is performed asynchronously; a successful call is acknowledged
     * with a 202 Accepted response.
     * <p>
     * <strong>Note:</strong> Requires a Management API key.
     *
     * @param taskId the task ID
     * @throws GradientLabsException if the request fails
     */
    public void deleteBackOfficeTask(String taskId) {
        String path = String.format("/back-office-tasks/%s", seg(taskId));
        httpClient.delete(path, Void.class);
    }

    // ==================== Voice Call Context Operations ====================

    /**
     * Retrieves the context from the most recent voice call for a given phone number.
     *
     * @param phoneNumber the phone number to look up
     * @param request     optional query parameters (lookbackSeconds, includeLargeFields)
     * @return the voice call context
     * @throws GradientLabsException if the request fails
     */
    public VoiceCallContext readVoiceCallContext(String phoneNumber, ReadVoiceCallContextRequest request) {
        String path = String.format("/voice/latest-call-context/%s", seg(phoneNumber));
        Map<String, Object> query = new LinkedHashMap<>();
        if (request != null) {
            query.put("lookback_seconds", request.getLookbackSeconds());
            query.put("include_large_fields", request.getIncludeLargeFields());
        }
        return httpClient.get(path, query, VoiceCallContext.class);
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

    private record ProcedureLimitResponse(Procedure procedure) {
    }

    private static String seg(String value) {
        return HttpClientWrapper.pathSegment(value);
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

}
