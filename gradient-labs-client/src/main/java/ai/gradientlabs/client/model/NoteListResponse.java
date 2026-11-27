package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A page of notes.
 */
public class NoteListResponse {

    @JsonProperty("notes")
    private List<Note> notes;

    @JsonProperty("pagination")
    private ForwardPagination pagination;

    /**
     * Default constructor for Jackson.
     */
    public NoteListResponse() {
    }

    /**
     * Returns the notes on this page.
     *
     * @return the notes
     */
    public List<Note> getNotes() {
        return notes;
    }

    /**
     * Returns the cursors for the next and previous pages.
     *
     * @return the pagination
     */
    public ForwardPagination getPagination() {
        return pagination;
    }
}
