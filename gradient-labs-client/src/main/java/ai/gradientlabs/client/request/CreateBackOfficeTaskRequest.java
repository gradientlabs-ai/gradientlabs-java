package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Parameters for creating a back office task.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateBackOfficeTaskRequest {

    @JsonProperty("id")
    private final String id;

    @JsonProperty("agent_id")
    private final String agentId;

    @JsonProperty("input")
    private final Map<String, Object> input;

    @JsonProperty("created")
    private final Instant created;

    @JsonProperty("metadata")
    private final Map<String, String> metadata;

    @JsonProperty("attachments")
    private final List<Attachment> attachments;

    private CreateBackOfficeTaskRequest(Builder builder) {
        this.id = builder.id;
        this.agentId = builder.agentId;
        this.input = builder.input;
        this.created = builder.created;
        this.metadata = builder.metadata;
        this.attachments = builder.attachments;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * An attachment to include when creating a back office task.
     * Provide either {@code url} (remote file) or {@code base64Contents} (inline upload), not both.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Attachment {

        @JsonProperty("file_name")
        private final String fileName;

        @JsonProperty("url")
        private final String url;

        @JsonProperty("base64_contents")
        private final String base64Contents;

        private Attachment(String fileName, String url, String base64Contents) {
            this.fileName = fileName;
            this.url = url;
            this.base64Contents = base64Contents;
        }

        public static Attachment fromUrl(String fileName, String url) {
            return new Attachment(fileName, url, null);
        }

        public static Attachment fromBase64(String fileName, String base64Contents) {
            return new Attachment(fileName, null, base64Contents);
        }

        public String getFileName() {
            return fileName;
        }

        public String getUrl() {
            return url;
        }

        public String getBase64Contents() {
            return base64Contents;
        }
    }

    public String getId() {
        return id;
    }

    public String getAgentId() {
        return agentId;
    }

    public Map<String, Object> getInput() {
        return input;
    }

    public Instant getCreated() {
        return created;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public static class Builder {
        private String id;
        private String agentId;
        private Map<String, Object> input;
        private Instant created;
        private Map<String, String> metadata;
        private List<Attachment> attachments;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder agentId(String agentId) {
            this.agentId = agentId;
            return this;
        }

        public Builder input(Map<String, Object> input) {
            this.input = input;
            return this;
        }

        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        public Builder metadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        public Builder attachments(List<Attachment> attachments) {
            this.attachments = attachments;
            return this;
        }

        public Builder addAttachment(Attachment attachment) {
            if (this.attachments == null) {
                this.attachments = new ArrayList<>();
            }
            this.attachments.add(attachment);
            return this;
        }

        public CreateBackOfficeTaskRequest build() {
            if (id == null || id.isBlank()) {
                throw new IllegalStateException("id is required");
            }
            if (agentId == null || agentId.isBlank()) {
                throw new IllegalStateException("agentId is required");
            }
            if (input == null) {
                throw new IllegalStateException("input is required");
            }
            return new CreateBackOfficeTaskRequest(this);
        }
    }
}
