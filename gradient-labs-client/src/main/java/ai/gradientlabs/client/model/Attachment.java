package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a file attachment in a message.
 */
public class Attachment {

    @JsonProperty("type")
    private AttachmentType type;

    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("description")
    private String description;

    /**
     * Default constructor for Jackson.
     */
    public Attachment() {
    }

    /**
     * Creates a new attachment.
     *
     * @param type     the attachment type
     * @param fileName the file name
     */
    public Attachment(AttachmentType type, String fileName) {
        this.type = type;
        this.fileName = fileName;
    }

    /**
     * Creates a new attachment with a description.
     *
     * @param type        the attachment type
     * @param fileName    the file name
     * @param description the attachment description
     */
    public Attachment(AttachmentType type, String fileName, String description) {
        this.type = type;
        this.fileName = fileName;
        this.description = description;
    }

    public AttachmentType getType() {
        return type;
    }

    public void setType(AttachmentType type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "type=" + type +
                ", fileName='" + fileName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
