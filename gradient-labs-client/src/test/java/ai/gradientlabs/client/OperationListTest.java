package ai.gradientlabs.client;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Maps each public API operation ID this library implements to its client method.
 * Update this map whenever PUBLIC_API_VERSION changes.
 */
class OperationListTest {

    static final Map<String, String> OPERATIONS = new TreeMap<>(Map.ofEntries(
            Map.entry("ArticleDelete", "deleteArticle"),
            Map.entry("ArticleSetUsageStatus", "setArticleUsageStatus"),
            Map.entry("ArticleTopicUpsert", "upsertArticleTopic"),
            Map.entry("ArticleUpsert", "upsertArticle"),
            Map.entry("BackOfficeTaskCreate", "createBackOfficeTask"),
            Map.entry("BackOfficeTaskDelete", "deleteBackOfficeTask"),
            Map.entry("BackOfficeTaskRead", "readBackOfficeTask"),
            Map.entry("ConversationAddMessage", "addMessage"),
            Map.entry("ConversationAssign", "assignConversation"),
            Map.entry("ConversationCancel", "cancelConversation"),
            Map.entry("ConversationDelete", "deleteConversation"),
            Map.entry("ConversationEvent", "addConversationEvent"),
            Map.entry("ConversationFinish", "finishConversation"),
            Map.entry("ConversationRate", "rateConversation"),
            Map.entry("ConversationRead", "readConversation"),
            Map.entry("ConversationReadDeprecated", "readConversationDeprecated"),
            Map.entry("ConversationResume", "resumeConversation"),
            Map.entry("ConversationReturnAsyncToolResult", "returnAsyncToolResult"),
            Map.entry("ConversationStart", "startConversation"),
            Map.entry("CustomerMemoriesBatchCreate", "batchCreateCustomerMemories"),
            Map.entry("CustomerMemoriesBatchCreateV2", "batchCreateCustomerMemoriesV2"),
            Map.entry("CustomerMemoriesList", "listCustomerMemories"),
            Map.entry("FileCreate", "createFile"),
            Map.entry("FileList", "listFiles"),
            Map.entry("FileUploadBegin", "beginFileUpload"),
            Map.entry("GetDefaultHandOffTarget", "getDefaultHandOffTarget"),
            Map.entry("HandOffTargetDelete", "deleteHandOffTarget"),
            Map.entry("HandOffTargetUpsert", "upsertHandOffTarget"),
            Map.entry("IPAddressList", "listIpAddresses"),
            Map.entry("KnowledgeBaseCreate", "createKnowledgeBase"),
            Map.entry("KnowledgeBaseList", "listKnowledgeBases"),
            Map.entry("KnowledgeBaseRead", "readKnowledgeBase"),
            Map.entry("KnowledgeBaseUpdate", "updateKnowledgeBase"),
            Map.entry("ListHandOffTargets", "listHandOffTargets"),
            Map.entry("ListProcedures", "listProcedures"),
            Map.entry("ListTopics", "listTopics"),
            Map.entry("ListTrafficGroups", "listTrafficGroups"),
            Map.entry("NoteCreate", "createNote"),
            Map.entry("NoteDelete", "deleteNote"),
            Map.entry("NoteList", "listNotes"),
            Map.entry("NoteRead", "readNote"),
            Map.entry("NoteSetStatus", "setNoteStatus"),
            Map.entry("NoteUpdate", "updateNote"),
            Map.entry("OutboundChatConversationStart", "startOutboundChatConversation"),
            Map.entry("OutboundEmailConversationStart", "startOutboundEmailConversation"),
            Map.entry("OutboundPhoneConversationStart", "startOutboundPhoneConversation"),
            Map.entry("ProcedureListVersions", "listProcedureVersions"),
            Map.entry("ProcedureSetGatedVersion", "setProcedureGatedVersion"),
            Map.entry("ProcedureSetLiveVersion", "setProcedureLiveVersion"),
            Map.entry("ProcedureUnsetGatedVersion", "unsetProcedureGatedVersion"),
            Map.entry("ProcedureUnsetLiveVersion", "unsetProcedureLiveVersion"),
            Map.entry("ReadProcedure", "readProcedure"),
            Map.entry("ReadTopic", "readTopic"),
            Map.entry("ResourceSourceCreate", "createResourceSource"),
            Map.entry("ResourceSourceDelete", "deleteResourceSource"),
            Map.entry("ResourceSourceList", "listResourceSources"),
            Map.entry("ResourceSourceRead", "readResourceSource"),
            Map.entry("ResourceSourceUpdate", "updateResourceSource"),
            Map.entry("ResourceTypeCreate", "createResourceType"),
            Map.entry("ResourceTypeDelete", "deleteResourceType"),
            Map.entry("ResourceTypeList", "listResourceTypes"),
            Map.entry("ResourceTypeRead", "readResourceType"),
            Map.entry("ResourceTypeUpdate", "updateResourceType"),
            Map.entry("SecretList", "listSecrets"),
            Map.entry("SecretRevoke", "revokeSecret"),
            Map.entry("SecretWrite", "writeSecret"),
            Map.entry("SetDefaultHandOffTarget", "setDefaultHandOffTarget"),
            Map.entry("SetProcedureLimit", "setProcedureLimit"),
            Map.entry("TerminologySubstitutionCreate", "createTerminologySubstitution"),
            Map.entry("TerminologySubstitutionDelete", "deleteTerminologySubstitution"),
            Map.entry("TerminologySubstitutionList", "listTerminologySubstitutions"),
            Map.entry("TerminologySubstitutionRead", "readTerminologySubstitution"),
            Map.entry("TerminologySubstitutionUpdate", "updateTerminologySubstitution"),
            Map.entry("ToolCreate", "createTool"),
            Map.entry("ToolDelete", "deleteTool"),
            Map.entry("ToolExecute", "executeTool"),
            Map.entry("ToolList", "listTools"),
            Map.entry("ToolRead", "readTool"),
            Map.entry("ToolUpdate", "updateTool"),
            Map.entry("TrafficGroupCreate", "createTrafficGroup"),
            Map.entry("TrafficGroupDelete", "deleteTrafficGroup"),
            Map.entry("TrafficGroupExclusionCreate", "createTrafficGroupExclusion"),
            Map.entry("TrafficGroupExclusionDelete", "deleteTrafficGroupExclusion"),
            Map.entry("TrafficGroupTargetCreate", "createTrafficGroupTarget"),
            Map.entry("TrafficGroupTargetDelete", "deleteTrafficGroupTarget"),
            Map.entry("TrafficGroupUpdate", "updateTrafficGroup"),
            Map.entry("UpdateSchemaByExamples", "updateResourceSourceSchemaByExamples"),
            Map.entry("VoiceCallContextRead", "readVoiceCallContext")
    ));

    @Test
    void everyListedOperationHasAPublicClientMethod() {
        List<String> missing = new ArrayList<>();
        Method[] methods = GradientLabsClient.class.getDeclaredMethods();
        OPERATIONS.forEach((operationId, methodName) -> {
            boolean found = Arrays.stream(methods)
                    .anyMatch(m -> m.getName().equals(methodName) && Modifier.isPublic(m.getModifiers()));
            if (!found) {
                missing.add(operationId + " -> " + methodName);
            }
        });
        assertTrue(missing.isEmpty(), "Client methods missing for: " + missing);
    }
}
