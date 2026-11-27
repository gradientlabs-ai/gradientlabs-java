package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * The response from listing knowledge bases.
 */
public class KnowledgeBasesListResponse {

    @JsonProperty("knowledge_bases")
    private List<KnowledgeBase> knowledgeBases;

    /**
     * Default constructor for Jackson.
     */
    public KnowledgeBasesListResponse() {
    }

    /**
     * Returns the knowledge bases.
     *
     * @return the knowledge bases
     */
    public List<KnowledgeBase> getKnowledgeBases() {
        return knowledgeBases;
    }
}
