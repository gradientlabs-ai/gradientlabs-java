# Changelog

## 2.0.1

Tool lists include enabled MCP tools. Tool updates support webhook, HTTP, async and draft tools.

## 2.0.0

Brings the client in line with the public API (contract
`3e9c6fa0a27454d8d5300c42e4215c475c3954b085dae9901f429d0c531e50f3`, recorded in `PUBLIC_API_VERSION`).

### Added

- Customer memories: `listCustomerMemories`, `batchCreateCustomerMemoriesV2`, and the deprecated
  `batchCreateCustomerMemories`.
- Files: `beginFileUpload`, `createFile`, `listFiles`.
- Knowledge bases: `listKnowledgeBases`, `createKnowledgeBase`, `readKnowledgeBase`, `updateKnowledgeBase`.
- Notes: `listNotes`, `readNote`.
- `readConversation(String)`, and `readConversationDeprecated` for the older `GET /conversations/{id}`.
- `readTool(String, Long)` to read a given tool version.
- Webhook events `conversation.start`, `back-office-task.complete`, `back-office-task.hand-off` and
  `back-office-task.fail`. Unknown event types now return `WebhookType.UNKNOWN` with the raw JSON as data.
- Tools now carry their full definition: parameters, parameter sets, HTTP, webhook, async and MCP settings,
  draft state and branch.
- New fields across conversations, messages, notes, articles, topics, hand-off targets, resource sources,
  traffic groups, voice call context, back-office task attachments and webhook events.
- Enum values: `Channel.VOICE`, `ParticipantType.BOT`, `ProcedureStatus.UNSAVED` and `ARCHIVED`,
  `Visibility.UNKNOWN`, `PublicationStatus.DELETED` and `EXCLUDED`, two Salesforce email identifier
  types, and every support platform the API accepts.

### Fixed

- Requests now go to the configured base URL.
- Query parameters are sent and URL-encoded; path parameters are URL-encoded.
- `readConversation` calls `GET /conversations/{id}/read`.
- `listTools` and `listTerminologySubstitutions` read the API's wrapped list responses.
- `deleteHandOffTarget` sends the ID as a query parameter.
- Setting the daily limit returns the updated procedure.
- Resource fetches arrive as `action.execute` events.
- Topic reads accept an omitted support platform.
- Timestamps are sent as RFC 3339 strings, not numbers.
- `ParticipantType` sends the values the API accepts (`Customer`, `Agent`, `AI Agent`, `Bot`).
- Response fields and enum values the client does not know no longer fail parsing.
- Topics and procedure versions read the field names the API returns.
- `conversation.hand_off` webhooks parse again.
- Error responses to GET requests throw `ResponseException`, like other methods.
- Back-office task attachments send `base_64_contents`.

### Breaking changes

- Tool creation uses the returned ID. Omit the ID when building a create request.
- Read the public response fields on procedures, messages and back-office task attachments.
  Procedure responses omit author details. Message responses omit the subject and conversation token.
  Task attachments expose their download URL.
- Memory data, article and topic data, back-office task input and asynchronous tool results accept any JSON value.
- Use `SecretRefreshRequest` when configuring secret refreshes. The retry option sets the timeout per attempt.
- Use `ResourceSourceSchemaUpdate` to send a new raw schema for a resource source.
- File and note pagination expose the next cursor. Procedure pagination also exposes the previous cursor.

- `ParticipantType.HUMAN` is renamed `AGENT`.
- `AttachmentType.DOCUMENT`, `VIDEO` and `AUDIO` are removed; use `FILE`.
- Removed fields the API does not have: `Conversation.metadata`, `Message.metadata`,
  `Message.conversationId`, `StartConversationRequest` and `AddMessageRequest` metadata, and
  `ReadConversationRequest.includeMessages` (replaced by `supportPlatform`).
- `Tool`, `CreateToolRequest` and `UpdateToolRequest` follow the API's tool shape; `schema`, `created` and
  `updated` are gone, and `UpdateToolRequest` no longer takes a name.
- `Topic.getData()` returns `byte[]`.
- `Memory` (v1 batch create) now requires `customType`, as the API does.
