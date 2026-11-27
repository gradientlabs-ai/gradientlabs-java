package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.Map;

/**
 * An upload started with {@code beginFileUpload}. PUT the file to {@link #getUploadUrl()} with {@link #getHeaders()}, then call {@code createFile}.
 */
public class FileUpload {

    @JsonProperty("upload_id")
    private String uploadId;

    @JsonProperty("upload_url")
    private String uploadUrl;

    @JsonProperty("headers")
    private Map<String, String> headers;

    @JsonProperty("content_type")
    private String contentType;

    @JsonProperty("max_bytes")
    private Long maxBytes;

    @JsonProperty("expires_at")
    private Instant expiresAt;

    /**
     * Default constructor for Jackson.
     */
    public FileUpload() {
    }

    /**
     * Returns the upload's ID, passed to {@code createFile}.
     *
     * @return the upload id
     */
    public String getUploadId() {
        return uploadId;
    }

    /**
     * Returns the URL to PUT the file to.
     *
     * @return the upload url
     */
    public String getUploadUrl() {
        return uploadUrl;
    }

    /**
     * Returns the headers to send with the PUT.
     *
     * @return the headers
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * Returns the content type to upload with.
     *
     * @return the content type
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Returns the largest file allowed, in bytes.
     *
     * @return the max bytes
     */
    public Long getMaxBytes() {
        return maxBytes;
    }

    /**
     * Returns when the upload URL stops working.
     *
     * @return the expires at
     */
    public Instant getExpiresAt() {
        return expiresAt;
    }
}
