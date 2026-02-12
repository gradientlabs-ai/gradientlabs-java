package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.NoteStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for setting a note's status.
 */
public class SetNoteStatusRequest {

    @JsonProperty("status")
    private final NoteStatus status;

    /**
     * Creates a new request to set a note's status.
     *
     * @param status the new status
     */
    public SetNoteStatusRequest(NoteStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("status is required");
        }
        this.status = status;
    }

    public NoteStatus getStatus() {
        return status;
    }
}
