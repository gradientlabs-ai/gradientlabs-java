package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.PublicationStatus;
import ai.gradientlabs.client.model.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Parameters for upserting (inserting or updating) an article topic.
 * <p>
 * Topics enable you to categorize your help articles into groups.
 * <p>
 * <strong>Note:</strong> Requires an Integration API key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpsertArticleTopicRequest {

    @JsonProperty("id")
    private final String id;

    @JsonProperty("parent_id")
    private final String parentId;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("description")
    private final String description;

    @JsonProperty("visibility")
    private final Visibility visibility;

    @JsonProperty("status")
    private final PublicationStatus status;

    @JsonProperty("data")
    private final Object data;

    @JsonProperty("created")
    private final Instant created;

    @JsonProperty("last_edited")
    private final Instant lastEdited;

    @JsonProperty("public_url")
    private final String publicUrl;

    private UpsertArticleTopicRequest(Builder builder) {
        this.publicUrl = builder.publicUrl;
        this.id = builder.id;
        this.parentId = builder.parentId;
        this.name = builder.name;
        this.description = builder.description;
        this.visibility = builder.visibility;
        this.status = builder.status;
        this.data = builder.data;
        this.created = builder.created;
        this.lastEdited = builder.lastEdited;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public PublicationStatus getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }

    public Instant getCreated() {
        return created;
    }

    public Instant getLastEdited() {
        return lastEdited;
    }

    /**
     * Returns the topic's public web address.
     *
     * @return the public url
     */
    public String getPublicUrl() {
        return publicUrl;
    }

    public static class Builder {
        private String publicUrl;
        private String id;
        private String parentId;
        private String name;
        private String description;
        private Visibility visibility;
        private PublicationStatus status;
        private Object data;
        private Instant created;
        private Instant lastEdited;

        private Builder() {
        }

        /**
         * Sets the topic ID (required).
         * <p>
         * Your identifier for this topic. Can consist of letters, numbers,
         * or any of the following characters: _ - + =
         *
         * @param id the topic ID
         * @return this builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the identifier for this topic's parent topic (optional).
         * <p>
         * Top-level topics will have no parent, but sub-topics should
         * point back up to their parent topic.
         *
         * @param parentId the parent topic ID
         * @return this builder
         */
        public Builder parentId(String parentId) {
            this.parentId = parentId;
            return this;
        }

        /**
         * Sets the topic's name (required).
         *
         * @param name the topic name
         * @return this builder
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the topic's description/tagline (optional).
         *
         * @param description the description
         * @return this builder
         */
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets who can see this topic (required).
         *
         * @param visibility the visibility level
         * @return this builder
         */
        public Builder visibility(Visibility visibility) {
            this.visibility = visibility;
            return this;
        }

        /**
         * Sets the publication status. This has no effect on topics.
         *
         * @param status the publication status
         * @return this builder
         */
        public Builder status(PublicationStatus status) {
            this.status = status;
            return this;
        }

        /**
         * Sets additional metadata about the topic (optional).
         *
         * @param data the JSON value
         * @return this builder
         */
        public Builder data(Object data) {
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
            if (data != null && !(data instanceof Map<?, ?>)) {
                throw new IllegalStateException("Adding a field requires object data");
            }
            Map<Object, Object> fields = data instanceof Map<?, ?> existing
                    ? new HashMap<>(existing) : new HashMap<>();
            fields.put(key, value);
            data = fields;
            return this;
        }

        /**
         * Sets when the topic was first created (required).
         *
         * @param created the creation timestamp
         * @return this builder
         */
        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        /**
         * Sets when the topic was last changed (required).
         *
         * @param lastEdited the last edit timestamp
         * @return this builder
         */
        public Builder lastEdited(Instant lastEdited) {
            this.lastEdited = lastEdited;
            return this;
        }

        /**
         * Sets the topic's public web address.
         *
         * @param publicUrl the public url
         * @return this builder
         */
        public Builder publicUrl(String publicUrl) {
            this.publicUrl = publicUrl;
            return this;
        }

        /**
         * Builds the request.
         *
         * @return a new request instance
         * @throws IllegalStateException if required fields are missing
         */
        public UpsertArticleTopicRequest build() {
            if (id == null || id.isBlank()) {
                throw new IllegalStateException("id is required");
            }
            if (name == null || name.isBlank()) {
                throw new IllegalStateException("name is required");
            }
            if (visibility == null) {
                throw new IllegalStateException("visibility is required");
            }
            if (created == null) {
                throw new IllegalStateException("created is required");
            }
            if (lastEdited == null) {
                throw new IllegalStateException("lastEdited is required");
            }
            return new UpsertArticleTopicRequest(this);
        }
    }
}
