package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.List;
import java.time.Instant;

/**
 * Parameters for creating a note.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateNoteRequest {

    @JsonProperty("id")
    private final String id;

    @JsonProperty("title")
    private final String title;

    @JsonProperty("body")
    private final String body;

    @JsonProperty("webpage_url")
    private final String webpageUrl;

    @JsonProperty("start_time")
    private final Instant startTime;

    @JsonProperty("end_time")
    private final Instant endTime;

    @JsonProperty("attributes")
    private final Map<String, List<String>> attributes;

    @JsonProperty("knowledge_base_ids")
    private final List<String> knowledgeBaseIds;

    private CreateNoteRequest(Builder builder) {
        this.attributes = builder.attributes;
        this.knowledgeBaseIds = builder.knowledgeBaseIds;
        this.id = builder.id;
        this.title = builder.title;
        this.body = builder.body;
        this.webpageUrl = builder.webpageUrl;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getWebpageUrl() {
        return webpageUrl;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    /**
     * Returns the note's attributes, by attribute name.
     *
     * @return the attributes
     */
    public Map<String, List<String>> getAttributes() {
        return attributes;
    }

    /**
     * Returns your IDs of the knowledge bases the note belongs to.
     *
     * @return the knowledge base ids
     */
    public List<String> getKnowledgeBaseIds() {
        return knowledgeBaseIds;
    }

    public static class Builder {
        private Map<String, List<String>> attributes;
        private List<String> knowledgeBaseIds;
        private String id;
        private String title;
        private String body;
        private String webpageUrl;
        private Instant startTime;
        private Instant endTime;

        private Builder() {
        }

        /**
         * Sets the note ID (required).
         * <p>
         * Your identifier for this note.
         *
         * @param id the note ID
         * @return this builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the note's title (required).
         *
         * @param title the title
         * @return this builder
         */
        public Builder title(String title) {
            this.title = title;
            return this;
        }

        /**
         * Sets the main contents of the note (optional).
         * <p>
         * This is mutually exclusive with webpageUrl.
         *
         * @param body the note body
         * @return this builder
         */
        public Builder body(String body) {
            this.body = body;
            return this;
        }

        /**
         * Sets a webpage URL to use as the note body (optional).
         * <p>
         * This is mutually exclusive with body.
         *
         * @param webpageUrl the webpage URL
         * @return this builder
         */
        public Builder webpageUrl(String webpageUrl) {
            this.webpageUrl = webpageUrl;
            return this;
        }

        /**
         * Sets when the note becomes relevant (optional).
         *
         * @param startTime the start time
         * @return this builder
         */
        public Builder startTime(Instant startTime) {
            this.startTime = startTime;
            return this;
        }

        /**
         * Sets when the note is no longer relevant (optional).
         *
         * @param endTime the end time
         * @return this builder
         */
        public Builder endTime(Instant endTime) {
            this.endTime = endTime;
            return this;
        }

        /**
         * Sets the note's attributes, by attribute name.
         *
         * @param attributes the attributes
         * @return this builder
         */
        public Builder attributes(Map<String, List<String>> attributes) {
            this.attributes = attributes;
            return this;
        }

        /**
         * Sets your IDs of the knowledge bases the note belongs to.
         *
         * @param knowledgeBaseIds the knowledge base ids
         * @return this builder
         */
        public Builder knowledgeBaseIds(List<String> knowledgeBaseIds) {
            this.knowledgeBaseIds = knowledgeBaseIds;
            return this;
        }

        /**
         * Builds the request.
         *
         * @return a new request instance
         * @throws IllegalStateException if required fields are missing
         */
        public CreateNoteRequest build() {
            if (id == null || id.isBlank()) {
                throw new IllegalStateException("id is required");
            }
            if (title == null || title.isBlank()) {
                throw new IllegalStateException("title is required");
            }
            return new CreateNoteRequest(this);
        }
    }
}
