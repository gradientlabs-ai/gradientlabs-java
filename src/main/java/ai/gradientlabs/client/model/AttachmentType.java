package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The type of attachment.
 */
public enum AttachmentType {
    /**
     * Image attachment.
     */
    IMAGE("image"),

    /**
     * Document attachment.
     */
    DOCUMENT("document"),

    /**
     * Video attachment.
     */
    VIDEO("video"),

    /**
     * Audio attachment.
     */
    AUDIO("audio");

    private final String value;

    AttachmentType(String value) {
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
