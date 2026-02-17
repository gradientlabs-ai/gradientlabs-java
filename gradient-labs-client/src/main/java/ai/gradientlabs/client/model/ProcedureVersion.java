package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * A specific version of a procedure.
 * <p>
 * Procedures can have multiple versions, with one marked as "live" (production)
 * and optionally one marked as "experimental" for controlled testing.
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

    @JsonProperty("experimental")
    private boolean experimental;

    @JsonProperty("experimental_config")
    private ExperimentalConfig experimentalConfig;

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
     * Checks if this is an experimental version.
     * <p>
     * Experimental versions are used before "live" versions, within the daily
     * limit defined in the experimental config.
     *
     * @return true if this is an experimental version
     */
    public boolean isExperimental() {
        return experimental;
    }

    /**
     * Sets whether this is an experimental version.
     *
     * @param experimental true for experimental versions
     */
    public void setExperimental(boolean experimental) {
        this.experimental = experimental;
    }

    /**
     * Gets the configuration for this experimental version.
     * <p>
     * Only relevant if this is an experimental version.
     *
     * @return the experimental configuration, or null if not experimental
     */
    public ExperimentalConfig getExperimentalConfig() {
        return experimentalConfig;
    }

    /**
     * Sets the experimental configuration.
     *
     * @param experimentalConfig the experimental configuration
     */
    public void setExperimentalConfig(ExperimentalConfig experimentalConfig) {
        this.experimentalConfig = experimentalConfig;
    }

    /**
     * Checks if this is the "production" live version.
     * <p>
     * The live version is used by the agent by default, if there are no
     * experimental versions or all of them have exceeded their limit.
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
                ", experimental=" + experimental +
                ", experimentalConfig=" + experimentalConfig +
                ", live=" + live +
                '}';
    }
}
