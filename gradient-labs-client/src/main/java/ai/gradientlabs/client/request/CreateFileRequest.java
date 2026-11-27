package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for turning a finished upload into a workspace file.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateFileRequest {

    @JsonProperty("upload_id")
    private final String uploadId;

    @JsonProperty("file_name")
    private final String fileName;

    @JsonProperty("ref")
    private final String ref;

    private CreateFileRequest(Builder builder) {
        this.uploadId = builder.uploadId;
        this.fileName = builder.fileName;
        this.ref = builder.ref;
    }

    /**
     * Returns a new builder.
     *
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the ID returned by {@code beginFileUpload}.
     *
     * @return the upload id
     */
    public String getUploadId() {
        return uploadId;
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
     * Returns the reference to point to this file from procedures and tools.
     *
     * @return the ref
     */
    public String getRef() {
        return ref;
    }

    /**
     * Builder for {@link CreateFileRequest}.
     */
    public static class Builder {
        private String uploadId;
        private String fileName;
        private String ref;

        private Builder() {
        }

        /**
         * Sets the ID returned by {@code beginFileUpload}. Required.
         *
         * @param uploadId the upload id
         * @return this builder
         */
        public Builder uploadId(String uploadId) {
            this.uploadId = uploadId;
            return this;
        }

        /**
         * Sets the file's name. Required.
         *
         * @param fileName the file name
         * @return this builder
         */
        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        /**
         * Sets the reference to point to this file from procedures and tools.
         *
         * @param ref the ref
         * @return this builder
         */
        public Builder ref(String ref) {
            this.ref = ref;
            return this;
        }

        /**
         * Builds the request.
         *
         * @return the request
         * @throws IllegalStateException if a required field is missing
         */
        public CreateFileRequest build() {
            if (uploadId == null || uploadId.isBlank()) {
                throw new IllegalStateException("uploadId is required");
            }
            if (fileName == null || fileName.isBlank()) {
                throw new IllegalStateException("fileName is required");
            }
            return new CreateFileRequest(this);
        }
    }
}
