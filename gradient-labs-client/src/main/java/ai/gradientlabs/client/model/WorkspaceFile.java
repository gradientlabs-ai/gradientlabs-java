package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * A file uploaded to your workspace.
 */
public class WorkspaceFile {

    @JsonProperty("id")
    private String id;

    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("content_type")
    private String contentType;

    @JsonProperty("size_bytes")
    private Long sizeBytes;

    @JsonProperty("ref")
    private String ref;

    @JsonProperty("created")
    private Instant created;

    /**
     * Default constructor for Jackson.
     */
    public WorkspaceFile() {
    }

    /**
     * Returns the file's ID.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the file's name.
     *
     * @return the file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Returns the file's content type.
     *
     * @return the content type
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Returns the file's size in bytes.
     *
     * @return the size bytes
     */
    public Long getSizeBytes() {
        return sizeBytes;
    }

    /**
     * Returns the reference used to point to this file from procedures and tools.
     *
     * @return the ref
     */
    public String getRef() {
        return ref;
    }

    /**
     * Returns when the file was created.
     *
     * @return the created
     */
    public Instant getCreated() {
        return created;
    }
}
