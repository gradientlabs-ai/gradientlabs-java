package ai.gradientlabs.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response from starting an outbound conversation.
 */
public class StartOutboundConversationResponse {

    @JsonProperty("conversation_id")
    private String conversationId;

    /**
     * Gets the conversation ID.
     * <p>
     * The internal identifier for the created conversation.
     * You can use this ID with other conversation APIs to check status, send messages, etc.
     *
     * @return the conversation ID
     */
    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}
