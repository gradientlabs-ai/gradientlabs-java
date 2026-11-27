package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * A knowledge base that articles and notes can be attached to.
 */
public class KnowledgeBase {

    @JsonProperty("id")
    private String id;

    @JsonProperty("gradient_labs_id")
    private String gradientLabsId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("created")
    private Instant created;

    @JsonProperty("updated")
    private Instant updated;

    /**
     * Default constructor for Jackson.
     */
    public KnowledgeBase() {
    }

    /**
     * Returns your identifier for the knowledge base.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the identifier Gradient Labs gave the knowledge base.
     *
     * @return the gradient labs id
     */
    public String getGradientLabsId() {
        return gradientLabsId;
    }

    /**
     * Returns the knowledge base's name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns when the knowledge base was created.
     *
     * @return the created
     */
    public Instant getCreated() {
        return created;
    }

    /**
     * Returns when the knowledge base was last updated.
     *
     * @return the updated
     */
    public Instant getUpdated() {
        return updated;
    }
}
