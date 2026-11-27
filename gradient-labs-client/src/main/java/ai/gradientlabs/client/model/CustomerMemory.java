package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * A memory scoped to a customer, which the AI agent can search on demand.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerMemory {

    @JsonProperty("external_id")
    private String externalId;

    @JsonProperty("custom_type")
    private MemoryCustomType customType;

    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    @JsonProperty("data")
    private Object data;

    /**
     * Default constructor for Jackson.
     */
    public CustomerMemory() {
    }

    /**
     * Creates a new instance.
     *
     * @param externalId your identifier for the memory, unique within your company.
     * @param customType the memory's category.
     * @param createdAt when the memory's underlying event happened.
     * @param data the memory's payload, stored verbatim.
     */
    public CustomerMemory(String externalId, MemoryCustomType customType, Instant createdAt, Object data) {
        this.externalId = externalId;
        this.customType = customType;
        this.createdAt = createdAt;
        this.data = data;
    }

    /**
     * Returns your identifier for the memory, unique within your company.
     *
     * @return the external id
     */
    public String getExternalId() {
        return externalId;
    }

    /**
     * Sets your identifier for the memory, unique within your company.
     *
     * @param externalId the external id
     */
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    /**
     * Returns the memory's category.
     *
     * @return the custom type
     */
    public MemoryCustomType getCustomType() {
        return customType;
    }

    /**
     * Sets the memory's category.
     *
     * @param customType the custom type
     */
    public void setCustomType(MemoryCustomType customType) {
        this.customType = customType;
    }

    /**
     * Returns when the memory's underlying event happened.
     *
     * @return the created at
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets when the memory's underlying event happened.
     *
     * @param createdAt the created at
     */
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns the memory's payload, stored verbatim.
     *
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * Sets the memory's payload, stored verbatim.
     *
     * @param data the data
     */
    public void setData(Object data) {
        this.data = data;
    }
}
