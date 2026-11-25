package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * Represents an article topic used to categorize help articles into groups.
 */
public class Topic {

    @JsonProperty("Source")
    private String source;

    @JsonProperty("ExternalID")
    private String externalId;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("Visibility")
    private Visibility visibility;

    @JsonProperty("ParentExternalID")
    private String parentExternalId;

    @JsonProperty("Created")
    private Instant created;

    @JsonProperty("LastEdited")
    private Instant lastEdited;

    @JsonProperty("LastSeen")
    private Instant lastSeen;

    @JsonProperty("Data")
    private byte[] data;

    @JsonProperty("PublicURL")
    private String publicUrl;

    /**
     * Default constructor for Jackson.
     */
    public Topic() {
    }

    /**
     * Returns the CRM or support platform that the topic comes from.
     *
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * Returns the identifier for this topic in the source platform.
     *
     * @return the external ID
     */
    public String getExternalId() {
        return externalId;
    }

    /**
     * Returns the human-readable name for this topic.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the optional subtext for the topic.
     *
     * @return the description, or null if none
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns who can see the topic.
     *
     * @return the visibility
     */
    public Visibility getVisibility() {
        return visibility;
    }

    /**
     * Returns the identifier for the parent topic that this topic is nested under.
     *
     * @return the parent external ID, or null if this is a top-level topic
     */
    public String getParentExternalId() {
        return parentExternalId;
    }

    /**
     * Returns when the topic was created in the source.
     *
     * @return the creation time
     */
    public Instant getCreated() {
        return created;
    }

    /**
     * Returns when the topic was last changed in the source.
     *
     * @return the last edited time
     */
    public Instant getLastEdited() {
        return lastEdited;
    }

    /**
     * Returns the last time we saw this topic when crawling.
     *
     * @return the last seen time
     */
    public Instant getLastSeen() {
        return lastSeen;
    }

    /**
     * Returns the topic as the support platform represents it (decoded from base64).
     *
     * @return the raw topic bytes
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Returns the public URL for this topic.
     *
     * @return the public URL, or null if none
     */
    public String getPublicUrl() {
        return publicUrl;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "source='" + source + '\'' +
                ", externalId='" + externalId + '\'' +
                ", name='" + name + '\'' +
                ", visibility=" + visibility +
                ", created=" + created +
                ", lastEdited=" + lastEdited +
                '}';
    }
}
