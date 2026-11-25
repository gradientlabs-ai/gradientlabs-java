package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Cursor for fetching the next page of results. */
public class ForwardPagination {

    @JsonProperty("next")
    private String next;

    public String getNext() {
        return next;
    }
}
