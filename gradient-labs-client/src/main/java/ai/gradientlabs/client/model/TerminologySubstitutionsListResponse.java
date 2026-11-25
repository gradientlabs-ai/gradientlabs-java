package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * The response from listing terminology substitutions.
 */
public class TerminologySubstitutionsListResponse {

    @JsonProperty("substitutions")
    private List<TerminologySubstitution> substitutions;

    /**
     * Default constructor for Jackson.
     */
    public TerminologySubstitutionsListResponse() {
    }

    /**
     * Returns the terminology substitutions.
     *
     * @return the substitutions
     */
    public List<TerminologySubstitution> getSubstitutions() {
        return substitutions;
    }
}
