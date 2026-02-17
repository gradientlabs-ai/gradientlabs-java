package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * Represents a stored secret with optional expiration and refresh configuration.
 */
public class Secret {

    @JsonProperty("name")
    private String name;

    @JsonProperty("created")
    private Instant created;

    @JsonProperty("updated")
    private Instant updated;

    @JsonProperty("expiry")
    private Instant expiry;

    @JsonProperty("refresh_mechanism_http")
    private RefreshMechanismHTTP refreshMechanismHttp;

    /**
     * Default constructor for Jackson.
     */
    public Secret() {
    }

    /**
     * Gets the unique identifier for the secret.
     *
     * @return the secret name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets when the secret was first created.
     *
     * @return the creation time
     */
    public Instant getCreated() {
        return created;
    }

    /**
     * Gets when the secret was last updated.
     *
     * @return the last update time
     */
    public Instant getUpdated() {
        return updated;
    }

    /**
     * Gets the optional expiration time for the secret.
     *
     * @return the expiry time, or null if none
     */
    public Instant getExpiry() {
        return expiry;
    }

    /**
     * Gets the optional configuration for automatically refreshing the secret value.
     *
     * @return the refresh mechanism, or null if none
     */
    public RefreshMechanismHTTP getRefreshMechanismHttp() {
        return refreshMechanismHttp;
    }

    @Override
    public String toString() {
        return "Secret{" +
                "name='" + name + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", expiry=" + expiry +
                '}';
    }
}
