package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for starting a file upload.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BeginFileUploadRequest {

    @JsonProperty("content_type")
    private final String contentType;

    private BeginFileUploadRequest(Builder builder) {
        this.contentType = builder.contentType;
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
     * Returns the content type of the file to upload.
     *
     * @return the content type
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Builder for {@link BeginFileUploadRequest}.
     */
    public static class Builder {
        private String contentType;

        private Builder() {
        }

        /**
         * Sets the content type of the file to upload.
         *
         * @param contentType the content type
         * @return this builder
         */
        public Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        /**
         * Builds the request.
         *
         * @return the request
         * @throws IllegalStateException if a required field is missing
         */
        public BeginFileUploadRequest build() {
            return new BeginFileUploadRequest(this);
        }
    }
}
