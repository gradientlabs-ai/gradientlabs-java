package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * A procedure that the AI agent uses to resolve specific customer problems.
 * <p>
 * Procedures are instructions that guide the agent through handling particular scenarios.
 */
public class Procedure {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("status")
    private ProcedureStatus status;

    @JsonProperty("author")
    private UserDetails author;

    @JsonProperty("created")
    private Instant created;

    @JsonProperty("updated")
    private Instant updated;

    @JsonProperty("has_daily_limit")
    private boolean isDailyLimited;

    @JsonProperty("max_daily_conversations")
    private Integer maxDailyConversations;

    /**
     * Gets the unique identifier for this procedure.
     *
     * @return the procedure ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the procedure ID.
     *
     * @param id the procedure ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the user-given name of the procedure.
     *
     * @return the procedure name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the procedure name.
     *
     * @param name the procedure name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user-given description for the procedure.
     *
     * @return the procedure description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the procedure description.
     *
     * @param description the procedure description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the overall status of the procedure (draft or live).
     *
     * @return the procedure status
     */
    public ProcedureStatus getStatus() {
        return status;
    }

    /**
     * Sets the procedure status.
     *
     * @param status the procedure status
     */
    public void setStatus(ProcedureStatus status) {
        this.status = status;
    }

    /**
     * Gets the user who originally created the procedure.
     *
     * @return the author details
     */
    public UserDetails getAuthor() {
        return author;
    }

    /**
     * Sets the procedure author.
     *
     * @param author the author details
     */
    public void setAuthor(UserDetails author) {
        this.author = author;
    }

    /**
     * Gets the time at which the procedure was originally created.
     *
     * @return the creation time
     */
    public Instant getCreated() {
        return created;
    }

    /**
     * Sets the creation time.
     *
     * @param created the creation time
     */
    public void setCreated(Instant created) {
        this.created = created;
    }

    /**
     * Gets the time at which the procedure's status, metadata, or current
     * revision was last changed.
     * <p>
     * Note: This does not reflect revisions created as part of testing unsaved changes.
     *
     * @return the last update time
     */
    public Instant getUpdated() {
        return updated;
    }

    /**
     * Sets the last update time.
     *
     * @param updated the last update time
     */
    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    /**
     * Checks if this procedure has a daily usage limit.
     *
     * @return true if the procedure can only be executed for a maximum number
     *         of conversations in a given day
     */
    public boolean isDailyLimited() {
        return isDailyLimited;
    }

    /**
     * Sets whether the procedure has a daily limit.
     *
     * @param dailyLimited true to enable daily limits
     */
    public void setDailyLimited(boolean dailyLimited) {
        isDailyLimited = dailyLimited;
    }

    /**
     * Gets the maximum number of conversations that a procedure can be used in
     * on a given day, when it is rate limited.
     *
     * @return the maximum daily conversations, or null if not rate limited
     */
    public Integer getMaxDailyConversations() {
        return maxDailyConversations;
    }

    /**
     * Sets the maximum daily conversations.
     *
     * @param maxDailyConversations the maximum daily conversations
     */
    public void setMaxDailyConversations(Integer maxDailyConversations) {
        this.maxDailyConversations = maxDailyConversations;
    }

    @Override
    public String toString() {
        return "Procedure{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", author=" + author +
                ", created=" + created +
                ", updated=" + updated +
                ", isDailyLimited=" + isDailyLimited +
                ", maxDailyConversations=" + maxDailyConversations +
                '}';
    }
}
