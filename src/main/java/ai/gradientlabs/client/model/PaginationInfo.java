package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Pagination information for list operations.
 */
public class PaginationInfo {

    @JsonProperty("next")
    private String next;

    @JsonProperty("prev")
    private String prev;

    /**
     * Gets the cursor to retrieve the next page of results.
     *
     * @return the next cursor, or null if there are no more results
     */
    public String getNext() {
        return next;
    }

    /**
     * Sets the next page cursor.
     *
     * @param next the next cursor
     */
    public void setNext(String next) {
        this.next = next;
    }

    /**
     * Gets the cursor to retrieve the previous page of results.
     *
     * @return the previous cursor, or null if there are no previous results
     */
    public String getPrev() {
        return prev;
    }

    /**
     * Sets the previous page cursor.
     *
     * @param prev the previous cursor
     */
    public void setPrev(String prev) {
        this.prev = prev;
    }

    @Override
    public String toString() {
        return "PaginationInfo{" +
                "next='" + next + '\'' +
                ", prev='" + prev + '\'' +
                '}';
    }
}
