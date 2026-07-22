package ai.gradientlabs.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response from a conversation memories bulk upload.
 */
public class ConversationMemoriesBulkUploadResponse {

    @JsonProperty("upload_id")
    private String uploadId;

    @JsonProperty("memories_inserted")
    private long memoriesInserted;

    /**
     * Gets the identifier for this upload.
     *
     * @return the upload ID
     */
    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    /**
     * Gets the number of memories inserted by this upload.
     *
     * @return the number of memories inserted
     */
    public long getMemoriesInserted() {
        return memoriesInserted;
    }

    public void setMemoriesInserted(long memoriesInserted) {
        this.memoriesInserted = memoriesInserted;
    }
}
