package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A page of workspace files.
 */
public class FileListResponse {

    @JsonProperty("files")
    private List<WorkspaceFile> files;

    @JsonProperty("pagination")
    private ForwardPagination pagination;

    /**
     * Default constructor for Jackson.
     */
    public FileListResponse() {
    }

    /**
     * Returns the files on this page.
     *
     * @return the files
     */
    public List<WorkspaceFile> getFiles() {
        return files;
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
