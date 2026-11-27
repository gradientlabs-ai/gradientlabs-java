package ai.gradientlabs.client;

import ai.gradientlabs.client.exception.ResponseException;
import ai.gradientlabs.client.model.Attachment;
import ai.gradientlabs.client.model.AttachmentType;
import ai.gradientlabs.client.model.Channel;
import ai.gradientlabs.client.model.Conversation;
import ai.gradientlabs.client.model.CustomerMemory;
import ai.gradientlabs.client.model.FileListResponse;
import ai.gradientlabs.client.model.KnowledgeBase;
import ai.gradientlabs.client.model.MemoryCustomType;
import ai.gradientlabs.client.model.NoteListResponse;
import ai.gradientlabs.client.model.ParticipantType;
import ai.gradientlabs.client.model.Procedure;
import ai.gradientlabs.client.model.ProcedureStatus;
import ai.gradientlabs.client.model.TerminologySubstitution;
import ai.gradientlabs.client.model.Tool;
import ai.gradientlabs.client.model.ToolAsyncDefinition;
import ai.gradientlabs.client.model.ToolExecutionDefinition;
import ai.gradientlabs.client.model.ToolParameter;
import ai.gradientlabs.client.model.ToolParameterType;
import ai.gradientlabs.client.model.ToolWebhookDefinition;
import ai.gradientlabs.client.model.Topic;
import ai.gradientlabs.client.request.AddMessageRequest;
import ai.gradientlabs.client.request.AssignmentRequest;
import ai.gradientlabs.client.request.BatchCreateCustomerMemoriesV2Request;
import ai.gradientlabs.client.request.CreateToolRequest;
import ai.gradientlabs.client.request.UpdateToolRequest;
import ai.gradientlabs.client.request.CreateTrafficGroupTargetRequest;
import ai.gradientlabs.client.request.UpdateResourceSourceSchemaByExamplesRequest;
import ai.gradientlabs.client.request.DeleteHandOffTargetRequest;
import ai.gradientlabs.client.request.ListProceduresRequest;
import ai.gradientlabs.client.request.ListTopicsRequest;
import ai.gradientlabs.client.request.SetProcedureLimitRequest;
import ai.gradientlabs.client.request.ReadConversationRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientRequestsTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private final StubHttpClient http = new StubHttpClient();
    private final GradientLabsClient client = GradientLabsClient.builder()
            .apiKey("test-key")
            .baseUrl("https://api.example.com/")
            .httpClient(http)
            .build();

    private String lastUri() {
        return http.lastRequest().uri().toString();
    }

    @Test
    void sendsRequestsToTheBaseUrlWithAuthAndUserAgent() {
        http.respond(200, "{\"tools\":[]}");

        client.listTools();

        assertEquals("https://api.example.com/tools", lastUri());
        assertEquals("Bearer test-key", http.lastRequest().headers().firstValue("Authorization").orElse(null));
        assertTrue(http.lastRequest().headers().firstValue("User-Agent").orElse("")
                .matches("Gradient-Labs-Java/\\S+ \\(Java/\\S+\\)"));
    }

    @Test
    void readConversationUsesTheReadPathAndSupportPlatformQuery() {
        http.respond(200, "{\"id\":\"conv-1\",\"channel\":\"voice\",\"latest_intent\":\"refund\",\"new_field\":1}");

        Conversation conversation = client.readConversation("conv 1",
                ReadConversationRequest.builder().supportPlatform("intercom").build());

        assertEquals("GET", http.lastRequest().method());
        assertEquals("https://api.example.com/conversations/conv%201/read?support_platform=intercom", lastUri());
        assertEquals(Channel.VOICE, conversation.getChannel());
        assertEquals("refund", conversation.getLatestIntent());

        client.readConversation("conv-1");
        assertEquals("https://api.example.com/conversations/conv-1/read", lastUri());
    }

    @Test
    @SuppressWarnings("deprecation")
    void readConversationDeprecatedUsesTheOldPath() {
        http.respond(200, "{\"id\":\"conv-1\"}");

        client.readConversationDeprecated("conv-1");

        assertEquals("https://api.example.com/conversations/conv-1", lastUri());
    }

    @Test
    void queryParametersAreSentAndEncoded() {
        http.respond(200, "{\"procedures\":[],\"pagination\":{}}");
        client.listProcedures(ListProceduresRequest.builder().cursor("a b&c").status(ProcedureStatus.LIVE).build());
        assertEquals("https://api.example.com/procedures?cursor=a+b%26c&status=live", lastUri());

        http.respond(200, "{\"Topics\":[]}");
        client.listTopics(ListTopicsRequest.builder().supportPlatform("public-api").build());
        assertEquals("https://api.example.com/topics?support_platform=public-api", lastUri());

        http.respond(200, "{\"id\":\"t\"}");
        client.readTool("tool-1", 3L);
        assertEquals("https://api.example.com/tools/tool-1?version=3", lastUri());
    }

    @Test
    void deleteHandOffTargetSendsTheIdAsAQueryParameter() {
        http.respond(200, "");

        client.deleteHandOffTarget(DeleteHandOffTargetRequest.builder().id("target-1").build());

        assertEquals("DELETE", http.lastRequest().method());
        assertEquals("https://api.example.com/hand-off-targets?id=target-1", lastUri());
        assertEquals("", http.lastBody());
    }

    @Test
    void listToolsReturnsEnabledMcpTool() {
        http.respond(200, "{\"tools\":[{\"id\":\"tool_1\",\"name\":\"lookup\",\"description\":\"Look up an order\",\"parameters\":[],\"mcp\":{\"server_id\":\"mcpserver_1\",\"external_tool_name\":\"lookup_order\"}}]}");
        Tool tool = client.listTools().get(0);
        assertEquals("mcpserver_1", tool.getMcp().getServerId());
        assertEquals("lookup_order", tool.getMcp().getExternalToolName());
    }

    @Test
    void listToolsAndSubstitutionsReadTheWrappedLists() {
        http.respond(200, "{\"tools\":[{\"id\":\"t1\",\"name\":\"Lookup\",\"parameters\":[{\"name\":\"order_id\","
                + "\"type\":\"string\",\"allowed_sources\":[\"llm\"]}],\"async\":{\"timeout\":30000000000,"
                + "\"start_execution_tool\":{\"webhook\":{\"name\":\"start\"}}}}]}");
        List<Tool> tools = client.listTools();
        assertEquals(1, tools.size());
        assertEquals(ToolParameterType.STRING, tools.get(0).getParameters().get(0).getType());
        assertEquals(Duration.ofSeconds(30), tools.get(0).getAsync().getTimeout());
        assertEquals("start", tools.get(0).getAsync().getStartExecutionTool().getWebhook().getName());

        http.respond(200, "{\"substitutions\":[{\"id\":\"s1\",\"blocked\":\"foo\"}]}");
        List<TerminologySubstitution> substitutions = client.listTerminologySubstitutions();
        assertEquals("s1", substitutions.get(0).getId());
    }

    @Test
    void createToolWritesTheContractShape() throws Exception {
        http.respond(200, "{\"id\":\"t1\"}");
        ToolParameter parameter = new ToolParameter();
        parameter.setName("order_id");
        parameter.setDescription("The order");
        parameter.setType(ToolParameterType.STRING);
        ToolAsyncDefinition async = new ToolAsyncDefinition(
                new ToolExecutionDefinition(null, new ToolWebhookDefinition("start")), null,
                Duration.ofSeconds(30));

        client.createTool(CreateToolRequest.builder()
                .name("Lookup order").description("Looks up an order")
                .parameters(List.of(parameter)).async(async).branchName("main")
                .build());

        JsonNode body = mapper.readTree(http.lastBody());
        assertTrue(body.path("id").isMissingNode());
        assertEquals("string", body.get("parameters").get(0).get("type").asText());
        assertEquals(30_000_000_000L, body.get("async").get("timeout").asLong());
        assertEquals("start", body.get("async").get("start_execution_tool").get("webhook").get("name").asText());
        assertTrue(body.get("async").get("start_execution_tool").path("http").isMissingNode());
    }

    @Test
    void createToolSendsEmptyParametersWhenNoneAreSet() throws Exception {
        http.respond(200, "{\"id\":\"t1\"}");
        client.createTool(CreateToolRequest.builder().name("n").description("d").draft(true).build());
        assertTrue(mapper.readTree(http.lastBody()).get("parameters").isArray());
    }

    @Test
    void updateToolSendsOnlyTheSuppliedFields() throws Exception {
        http.respond(200, "{}");
        client.updateTool("lookup", UpdateToolRequest.builder().description("Look up an order").build());
        assertEquals(mapper.readTree("{\"description\":\"Look up an order\"}"), mapper.readTree(http.lastBody()));
        client.updateTool("lookup", UpdateToolRequest.builder().parameters(List.of()).build());
        assertEquals(mapper.readTree("{\"parameters\":[]}"), mapper.readTree(http.lastBody()));
    }

    @Test
    void replayAttachmentOnlyAiMessage() throws Exception {
        http.respond(200, "{}");
        client.addMessage("chat", AddMessageRequest.builder().id("message").participantType(ParticipantType.AI_AGENT)
                .addAttachment(new Attachment(AttachmentType.FILE, "receipt.pdf")).build());
        JsonNode body = mapper.readTree(http.lastBody());
        assertEquals("receipt.pdf", body.path("attachments").get(0).path("file_name").asText());
        assertTrue(body.path("body").isMissingNode());
        assertTrue(body.path("participant_id").isMissingNode());
    }

    @Test
    void setProcedureLimitReadsTheReturnedProcedure() {
        http.respond(200, "{\"procedure\":{\"id\":\"billing\",\"has_daily_limit\":true,\"max_daily_conversations\":25}}");
        Procedure procedure = client.setProcedureLimit("billing", SetProcedureLimitRequest.builder()
                .hasDailyLimit(true).maxDailyConversations(25).build());
        assertEquals("billing", procedure.getId());
        assertEquals(25, procedure.getMaxDailyConversations());
    }

    @Test
    void usesPathIdsWithoutSendingThemInTheRequestBody() throws Exception {
        http.respond(200, "{}");
        client.createTrafficGroupTarget("group 1", CreateTrafficGroupTargetRequest.builder()
                .targetType("procedure").targetId("billing").build());
        assertEquals("https://api.example.com/traffic-groups/group%201/targets", lastUri());
        assertEquals(mapper.readTree("{\"target_type\":\"procedure\",\"target_id\":\"billing\"}"),
                mapper.readTree(http.lastBody()));

        client.updateResourceSourceSchemaByExamples("orders", UpdateResourceSourceSchemaByExamplesRequest.builder()
                .examples(List.of(Map.of("id", "example"))).build());
        assertEquals("https://api.example.com/resource-sources/orders/schema-by-examples", lastUri());
        assertEquals(mapper.readTree("{\"examples\":[{\"id\":\"example\"}]}"), mapper.readTree(http.lastBody()));
    }

    @Test
    void timestampsAndParticipantTypesUseTheWireFormat() throws Exception {
        http.respond(200, "");

        client.assignConversation("conv-1", AssignmentRequest.builder()
                .assigneeType(ParticipantType.AGENT)
                .timestamp(Instant.parse("2026-01-02T03:04:05Z"))
                .reason("needs a human")
                .build());

        JsonNode body = mapper.readTree(http.lastBody());
        assertEquals("Agent", body.get("assignee_type").asText());
        assertEquals("2026-01-02T03:04:05Z", body.get("timestamp").asText());
        assertEquals("AI Agent", ParticipantType.AI_AGENT.getValue());
    }

    @Test
    void customerMemoriesUseTheCustomerPaths() throws Exception {
        http.respond(200, "");
        client.batchCreateCustomerMemoriesV2("cust-1", BatchCreateCustomerMemoriesV2Request.builder()
                .memories(List.of(new CustomerMemory("order_1", new MemoryCustomType("order", "Orders"),
                        Instant.parse("2026-07-01T10:00:00Z"), List.of(42, true))))
                .build());
        assertEquals("https://api.example.com/customers/cust-1/memories/v2", lastUri());
        JsonNode memory = mapper.readTree(http.lastBody()).get("memories").get(0);
        assertEquals(mapper.readTree("[42,true]"), memory.get("data"));
        assertEquals("order", memory.get("custom_type").get("name").asText());
        assertEquals("2026-07-01T10:00:00Z", memory.get("created_at").asText());

        http.respond(200, "{\"memories\":[{\"external_id\":\"order_1\",\"custom_type\":{\"name\":\"order\"},"
                + "\"created_at\":\"2026-07-01T10:00:00Z\",\"data\":{}}]}");
        List<CustomerMemory> memories = client.listCustomerMemories("cust-1");
        assertEquals("GET", http.lastRequest().method());
        assertEquals("order", memories.get(0).getCustomType().getName());
    }

    @Test
    void notesFilesAndKnowledgeBasesUseTheirPaths() {
        http.respond(200, "{\"notes\":[{\"id\":\"n1\",\"attributes\":{\"region\":[\"uk\"]}}],"
                + "\"pagination\":{\"next\":\"c2\"}}");
        NoteListResponse notes = client.listNotes("c1");
        assertEquals("https://api.example.com/notes?cursor=c1", lastUri());
        assertEquals(List.of("uk"), notes.getNotes().get(0).getAttributes().get("region"));
        assertEquals("c2", notes.getPagination().getNext());

        http.respond(200, "{\"id\":\"n1\"}");
        client.readNote("n1");
        assertEquals("https://api.example.com/notes/n1", lastUri());

        http.respond(200, "{\"files\":[{\"id\":\"f1\",\"size_bytes\":10}],\"pagination\":{}}");
        FileListResponse files = client.listFiles();
        assertEquals("https://api.example.com/files", lastUri());
        assertEquals(10L, files.getFiles().get(0).getSizeBytes());

        http.respond(200, "{\"id\":\"kb1\",\"gradient_labs_id\":\"g1\",\"name\":\"Help\"}");
        KnowledgeBase kb = client.readKnowledgeBase("kb1");
        assertEquals("https://api.example.com/knowledge-bases/kb1", lastUri());
        assertEquals("g1", kb.getGradientLabsId());
    }

    @Test
    void topicsAndProcedureVersionsReadTheirFieldNames() {
        http.respond(200, "{\"ExternalID\":\"billing\",\"Name\":\"Billing\",\"Visibility\":\"unknown\","
                + "\"Data\":\"aGk=\"}");
        Topic topic = client.readTopic("billing", null);
        assertEquals("billing", topic.getExternalId());
        assertArrayEquals("hi".getBytes(), topic.getData());

        http.respond(200, "{\"Versions\":[{\"Version\":3,\"Live\":true,\"GatedConfig\":{\"MaxDailyConversations\":5}}]}");
        ListProcedureVersionsResponse versions = client.listProcedureVersions("proc-1");
        assertEquals(3, versions.getVersions().get(0).getVersion());
        assertEquals(5, versions.getVersions().get(0).getGatedConfig().getMaxDailyConversations());
    }

    @Test
    void errorResponsesOnGetBecomeResponseExceptions() {
        http.respond(404, "{\"code\":\"not_found\",\"message\":\"no such note\",\"details\":{\"trace_id\":\"abc\"}}");

        ResponseException e = assertThrows(ResponseException.class, () -> client.readNote("missing"));

        assertEquals(404, e.getStatusCode());
    }
}
