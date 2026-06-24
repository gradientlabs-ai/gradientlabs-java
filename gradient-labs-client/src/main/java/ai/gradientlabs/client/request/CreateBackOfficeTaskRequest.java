package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Parameters for creating a back office task.
 * <p>
 * Set {@code agentId} to the agent group (prefixed {@code agent_}) that runs the task, and set
 * {@code procedureId} to the procedure (prefixed {@code proc_}) within that agent to start from.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateBackOfficeTaskRequest {

    @JsonProperty("id")
    private final String id;

    @JsonProperty("agent_id")
    private final String agentId;

    @JsonProperty("procedure_id")
    private final String procedureId;

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
        this.procedureId = builder.procedureId;
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

    /**
     * Gets the procedure within the agent to start the task from.
     *
     * @return the procedure id (prefixed {@code proc_})
     */
    public String getProcedureId() {
        return procedureId;
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
        private String procedureId;
        private Map<String, Object> input;
        private Instant created;
        private Map<String, String> metadata;
        private List<Attachment> attachments;

        private Builder() {
        }

        /**
         * Sets the external task id (required).
         * <p>
         * Must be unique within your organisation.
         *
         * @param id the external task id
         * @return this builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the agent (agent group) that runs this task (required).
         *
         * @param agentId the agent group id (prefixed {@code agent_})
         * @return this builder
         */
        public Builder agentId(String agentId) {
            this.agentId = agentId;
            return this;
        }

        /**
         * Sets the procedure within the agent to start the task from (required).
         *
         * @param procedureId the procedure id (prefixed {@code proc_})
         * @return this builder
         */
        public Builder procedureId(String procedureId) {
            this.procedureId = procedureId;
            return this;
        }

        /**
         * Sets the task input (required).
         * <p>
         * Arbitrary JSON input. The schema is defined by the agent the task is dispatched to.
         *
         * @param input the task input
         * @return this builder
         */
        public Builder input(Map<String, Object> input) {
            this.input = input;
            return this;
        }

        /**
         * Sets when the task was created (optional).
         * <p>
         * If not given, this defaults to the current time.
         *
         * @param created the creation time
         * @return this builder
         */
        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        /**
         * Sets arbitrary metadata for the task (optional).
         *
         * @param metadata the metadata
         * @return this builder
         */
        public Builder metadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Sets the attachments for the task (optional).
         *
         * @param attachments the attachments
         * @return this builder
         */
        public Builder attachments(List<Attachment> attachments) {
            this.attachments = attachments;
            return this;
        }

        /**
         * Adds a single attachment.
         *
         * @param attachment the attachment to add
         * @return this builder
         */
        public Builder addAttachment(Attachment attachment) {
            if (this.attachments == null) {
                this.attachments = new ArrayList<>();
            }
            this.attachments.add(attachment);
            return this;
        }

        /**
         * Builds the request.
         *
         * @return a new request instance
         * @throws IllegalStateException if required fields are missing
         */
        public CreateBackOfficeTaskRequest build() {
            if (id == null || id.isBlank()) {
                throw new IllegalStateException("id is required");
            }
            if (agentId == null || agentId.isBlank()) {
                throw new IllegalStateException("agentId is required");
            }
            if (procedureId == null || procedureId.isBlank()) {
                throw new IllegalStateException("procedureId is required");
            }
            if (input == null) {
                throw new IllegalStateException("input is required");
            }
            return new CreateBackOfficeTaskRequest(this);
        }
    }
}
