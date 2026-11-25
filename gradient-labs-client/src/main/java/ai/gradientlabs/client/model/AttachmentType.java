package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The type of a message attachment.
 */
public enum AttachmentType {
    /**
     * An image.
     */
    IMAGE("image"),

    /**
     * Any other kind of file.
     */
    FILE("file");

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
