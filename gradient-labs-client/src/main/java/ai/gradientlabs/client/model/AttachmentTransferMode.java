package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * How an attachment parameter is sent to a tool.
 */
public enum AttachmentTransferMode {
    /**
     * The file contents, base64-encoded.
     */
    BASE64("base64"),

    /**
     * A temporary URL the tool can download the file from.
     */
    TEMPORARY_URL("temporary_url");

    private final String value;

    AttachmentTransferMode(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
