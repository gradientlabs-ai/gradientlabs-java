package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * A specific version of a procedure.
 * <p>
 * Procedures can have multiple versions, with one marked as "live" (production)
 * and optionally one marked as "gated" for controlled testing.
 */
public class ProcedureVersion {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("version")
    private int version;

    @JsonProperty("author")
    private String author;

    @JsonProperty("created")
    private Instant created;

    @JsonProperty("gated")
    private boolean gated;

    @JsonProperty("gated_config")
    private GatedConfig gatedConfig;

    @JsonProperty("live")
    private boolean live;

    /**
     * Gets the user-given name of the procedure at the time of this version.
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
     * Gets the description of the procedure at the time of this version.
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
     * Gets the numeric identifier for this version.
     * <p>
     * Version numbers are incremented every time a new version of the procedure is saved.
     *
     * @return the version number
     */
    public int getVersion() {
        return version;
    }

    /**
     * Sets the version number.
     *
     * @param version the version number
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * Gets the ID of the user who created this version of the procedure.
     *
     * @return the author ID
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author ID.
     *
     * @param author the author ID
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the time at which this version of the procedure was created.
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
     * Checks if this is a gated version.
     * <p>
     * Gated versions are used before "live" versions, within the daily
     * limit defined in the gated config.
     *
     * @return true if this is a gated version
     */
    public boolean isGated() {
        return gated;
    }

    /**
     * Sets whether this is a gated version.
     *
     * @param gated true for gated versions
     */
    public void setGated(boolean gated) {
        this.gated = gated;
    }

    /**
     * Gets the configuration for the gated version.
     * <p>
     * Only relevant if the version is gated.
     *
     * @return the gated configuration, or null if the version is not gated
     */
    public GatedConfig getGatedConfig() {
        return gatedConfig;
    }

    /**
     * Sets the gated configuration.
     *
     * @param gatedConfig the gated configuration
     */
    public void setGatedConfig(GatedConfig gatedConfig) {
        this.gatedConfig = gatedConfig;
    }

    /**
     * Checks if this is the "production" live version.
     * <p>
     * The live version is used by the agent by default, if there are no
     * gated versions or all of them have exceeded their limit.
     *
     * @return true if this is the live version
     */
    public boolean isLive() {
        return live;
    }

    /**
     * Sets whether this is the live version.
     *
     * @param live true for the live version
     */
    public void setLive(boolean live) {
        this.live = live;
    }

    @Override
    public String toString() {
        return "ProcedureVersion{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", version=" + version +
                ", author='" + author + '\'' +
                ", created=" + created +
                ", gated=" + gated +
                ", gatedConfig=" + gatedConfig +
                ", live=" + live +
                '}';
    }
}
