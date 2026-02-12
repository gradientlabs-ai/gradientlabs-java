package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.Map;

/**
 * Represents a tool that the AI agent can use.
 */
public class Tool {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("schema")
    private Map<String, Object> schema;

    @JsonProperty("created")
    private Instant created;

    @JsonProperty("updated")
    private Instant updated;

    /**
     * Default constructor for Jackson.
     */
    public Tool() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, Object> getSchema() {
        return schema;
    }

    public Instant getCreated() {
        return created;
    }

    public Instant getUpdated() {
        return updated;
    }

    @Override
    public String toString() {
        return "Tool{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
