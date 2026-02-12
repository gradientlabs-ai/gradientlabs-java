package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.ProcedureStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for listing procedures.
 * <p>
 * <strong>Note:</strong> Requires a Management API key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListProceduresRequest {

    @JsonProperty("cursor")
    private final String cursor;

    @JsonProperty("status")
    private final ProcedureStatus status;

    private ListProceduresRequest(Builder builder) {
        this.cursor = builder.cursor;
        this.status = builder.status;
    }

    /**
     * Gets the cursor for pagination.
     *
     * @return the cursor, or null for the first page
     */
    public String getCursor() {
        return cursor;
    }

    /**
     * Gets the status filter.
     *
     * @return the status to filter by, or null to return all procedures
     */
    public ProcedureStatus getStatus() {
        return status;
    }

    /**
     * Creates a new builder for ListProceduresRequest.
     *
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Creates an empty request for listing all procedures.
     *
     * @return an empty request
     */
    public static ListProceduresRequest empty() {
        return new Builder().build();
    }

    public static class Builder {
        private String cursor;
        private ProcedureStatus status;

        /**
         * Sets the cursor for pagination.
         *
         * @param cursor the cursor from a previous response
         * @return this builder
         */
        public Builder cursor(String cursor) {
            this.cursor = cursor;
            return this;
        }

        /**
         * Sets the status filter.
         *
         * @param status the status to filter by (DRAFT or LIVE)
         * @return this builder
         */
        public Builder status(ProcedureStatus status) {
            this.status = status;
            return this;
        }

        /**
         * Builds the request.
         *
         * @return the built request
         */
        public ListProceduresRequest build() {
            return new ListProceduresRequest(this);
        }
    }
}
