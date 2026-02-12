package ai.gradientlabs.client;

import ai.gradientlabs.client.model.ProcedureVersion;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Response from listing procedure versions.
 */
public class ListProcedureVersionsResponse {

    @JsonProperty("versions")
    private List<ProcedureVersion> versions;

    /**
     * Gets the list of procedure versions.
     *
     * @return the versions
     */
    public List<ProcedureVersion> getVersions() {
        return versions;
    }

    /**
     * Sets the list of versions.
     *
     * @param versions the versions
     */
    public void setVersions(List<ProcedureVersion> versions) {
        this.versions = versions;
    }

    @Override
    public String toString() {
        return "ListProcedureVersionsResponse{" +
                "versions=" + versions +
                '}';
    }
}
