package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.PublicationStatus;
import ai.gradientlabs.client.model.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Parameters for upserting (inserting or updating) an article.
 * <p>
 * Articles are documents that the AI agent can work with to answer customer questions.
 * <p>
 * <strong>Note:</strong> Requires an Integration API key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpsertArticleRequest {

    @JsonProperty("author_id")
    private final String authorId;

    @JsonProperty("id")
    private final String id;

    @JsonProperty("title")
    private final String title;

    @JsonProperty("description")
    private final String description;

    @JsonProperty("body")
    private final String body;

    @JsonProperty("visibility")
    private final Visibility visibility;

    @JsonProperty("topic_id")
    private final String topicId;

    @JsonProperty("status")
    private final PublicationStatus status;

    @JsonProperty("data")
    private final Map<String, Object> data;

    @JsonProperty("created")
    private final Instant created;

    @JsonProperty("last_edited")
    private final Instant lastEdited;

    private UpsertArticleRequest(Builder builder) {
        this.authorId = builder.authorId;
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.body = builder.body;
        this.visibility = builder.visibility;
        this.topicId = builder.topicId;
        this.status = builder.status;
        this.data = builder.data;
        this.created = builder.created;
        this.lastEdited = builder.lastEdited;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getBody() {
        return body;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public String getTopicId() {
        return topicId;
    }

    public PublicationStatus getStatus() {
        return status;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Instant getCreated() {
        return created;
    }

    public Instant getLastEdited() {
        return lastEdited;
    }

    public static class Builder {
        private String authorId;
        private String id;
        private String title;
        private String description;
        private String body;
        private Visibility visibility;
        private String topicId;
        private PublicationStatus status;
        private Map<String, Object> data;
        private Instant created;
        private Instant lastEdited;

        private Builder() {
        }

        /**
         * Sets the author ID (optional).
         * <p>
         * Identifies the user who last edited the article.
         *
         * @param authorId the author ID
         * @return this builder
         */
        public Builder authorId(String authorId) {
            this.authorId = authorId;
            return this;
        }

        /**
         * Sets the article ID (required).
         * <p>
         * Your identifier for this article. Can consist of letters, numbers,
         * or any of the following characters: _ - + =
         *
         * @param id the article ID
         * @return this builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the article's title (optional).
         * <p>
         * May be empty if the article is a draft.
         *
         * @param title the title
         * @return this builder
         */
        public Builder title(String title) {
            this.title = title;
            return this;
        }

        /**
         * Sets the article's description/tagline (optional).
         *
         * @param description the description
         * @return this builder
         */
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the main contents of the article (optional).
         * <p>
         * May be empty if the article is a draft.
         *
         * @param body the article body
         * @return this builder
         */
        public Builder body(String body) {
            this.body = body;
            return this;
        }

        /**
         * Sets who can access this article (required).
         *
         * @param visibility the visibility level
         * @return this builder
         */
        public Builder visibility(Visibility visibility) {
            this.visibility = visibility;
            return this;
        }

        /**
         * Sets the topic that this article is associated with (optional).
         * <p>
         * If given, you must have created the topic first via {@code upsertArticleTopic}.
         *
         * @param topicId the topic ID
         * @return this builder
         */
        public Builder topicId(String topicId) {
            this.topicId = topicId;
            return this;
        }

        /**
         * Sets whether this article is published or a draft (required).
         * <p>
         * The AI agent will not use draft articles.
         *
         * @param status the publication status
         * @return this builder
         */
        public Builder status(PublicationStatus status) {
            this.status = status;
            return this;
        }

        /**
         * Sets additional metadata about the article (optional).
         *
         * @param data the metadata map
         * @return this builder
         */
        public Builder data(Map<String, Object> data) {
            this.data = data;
            return this;
        }

        /**
         * Adds a single metadata entry (optional).
         *
         * @param key   the metadata key
         * @param value the metadata value
         * @return this builder
         */
        public Builder addData(String key, Object value) {
            if (this.data == null) {
                this.data = new HashMap<>();
            }
            this.data.put(key, value);
            return this;
        }

        /**
         * Sets when the article was first authored (required).
         *
         * @param created the creation timestamp
         * @return this builder
         */
        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        /**
         * Sets when the article was last changed (required).
         *
         * @param lastEdited the last edit timestamp
         * @return this builder
         */
        public Builder lastEdited(Instant lastEdited) {
            this.lastEdited = lastEdited;
            return this;
        }

        /**
         * Builds the request.
         *
         * @return a new request instance
         * @throws IllegalStateException if required fields are missing
         */
        public UpsertArticleRequest build() {
            if (id == null || id.isBlank()) {
                throw new IllegalStateException("id is required");
            }
            if (visibility == null) {
                throw new IllegalStateException("visibility is required");
            }
            if (status == null) {
                throw new IllegalStateException("status is required");
            }
            if (created == null) {
                throw new IllegalStateException("created is required");
            }
            if (lastEdited == null) {
                throw new IllegalStateException("lastEdited is required");
            }
            return new UpsertArticleRequest(this);
        }
    }
}
