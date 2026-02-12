package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * Represents a note in the Gradient Labs system.
 */
public class Note {

    @JsonProperty("gradient_labs_id")
    private String id;

    @JsonProperty("id")
    private String externalId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String body;

    @JsonProperty("url")
    private String webpageUrl;

    @JsonProperty("valid_from")
    private Instant startTime;

    @JsonProperty("valid_to")
    private Instant endTime;

    @JsonProperty("last_modified_by")
    private String lastModifiedBy;

    @JsonProperty("created")
    private Instant created;

    @JsonProperty("updated")
    private Instant updated;

    @JsonProperty("status")
    private NoteStatus status;

    /**
     * Default constructor for Jackson.
     */
    public Note() {
    }

    /**
     * Returns the Gradient Labs ID for this note.
     * <p>
     * This is created by Gradient Labs.
     *
     * @return the Gradient Labs ID
     */
    public String getId() {
        return id;
    }

    /**
     * Returns your identifier for this note.
     *
     * @return the external ID
     */
    public String getExternalId() {
        return externalId;
    }

    /**
     * Returns the note's title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the main contents of the note.
     *
     * @return the body, or null if none
     */
    public String getBody() {
        return body;
    }

    /**
     * Returns the webpage URL to use as the note body.
     *
     * @return the webpage URL, or null if none
     */
    public String getWebpageUrl() {
        return webpageUrl;
    }

    /**
     * Returns when the note becomes relevant.
     *
     * @return the start time, or null if none
     */
    public Instant getStartTime() {
        return startTime;
    }

    /**
     * Returns when the note is no longer relevant.
     *
     * @return the end time, or null if none
     */
    public Instant getEndTime() {
        return endTime;
    }

    /**
     * Returns who last modified the note.
     *
     * @return the last modifier ID
     */
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    /**
     * Returns when the note was created.
     *
     * @return the creation time
     */
    public Instant getCreated() {
        return created;
    }

    /**
     * Returns when the note was last updated.
     *
     * @return the last update time
     */
    public Instant getUpdated() {
        return updated;
    }

    /**
     * Returns the status of the note.
     *
     * @return the status
     */
    public NoteStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", externalId='" + externalId + '\'' +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
