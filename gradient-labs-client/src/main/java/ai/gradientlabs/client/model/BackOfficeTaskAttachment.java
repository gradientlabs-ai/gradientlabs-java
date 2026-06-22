package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An attachment associated with a back office task.
 */
public class BackOfficeTaskAttachment {

    @JsonProperty("idempotency_key")
    private String idempotencyKey;

    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("external_url")
    private String externalUrl;

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public String getFileName() {
        return fileName;
    }

    public String getExternalUrl() {
        return externalUrl;
    }
}
