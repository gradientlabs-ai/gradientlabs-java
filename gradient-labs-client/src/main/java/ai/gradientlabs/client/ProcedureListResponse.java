package ai.gradientlabs.client;

import ai.gradientlabs.client.model.PaginationInfo;
import ai.gradientlabs.client.model.Procedure;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Response from listing procedures.
 */
public class ProcedureListResponse {

    @JsonProperty("procedures")
    private List<Procedure> procedures;

    @JsonProperty("pagination")
    private PaginationInfo pagination;

    /**
     * Gets the list of procedures.
     *
     * @return the procedures
     */
    public List<Procedure> getProcedures() {
        return procedures;
    }

    /**
     * Sets the list of procedures.
     *
     * @param procedures the procedures
     */
    public void setProcedures(List<Procedure> procedures) {
        this.procedures = procedures;
    }

    /**
     * Gets the pagination information for navigating through results.
     *
     * @return the pagination info
     */
    public PaginationInfo getPagination() {
        return pagination;
    }

    /**
     * Sets the pagination information.
     *
     * @param pagination the pagination info
     */
    public void setPagination(PaginationInfo pagination) {
        this.pagination = pagination;
    }

    @Override
    public String toString() {
        return "ProcedureListResponse{" +
                "procedures=" + procedures +
                ", pagination=" + pagination +
                '}';
    }
}
