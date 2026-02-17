package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.RefreshStrategy;
import ai.gradientlabs.client.model.Scope;
import ai.gradientlabs.client.model.SourceConfig;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for updating an existing resource type.
 * <p>
 * Requires a Management API key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateResourceTypeRequest {

    private final String id;

    @JsonProperty("display_name")
    private final String displayName;

    @JsonProperty("description")
    private final String description;

    @JsonProperty("scope")
    private final Scope scope;

    @JsonProperty("refresh_strategy")
    private final RefreshStrategy refreshStrategy;

    @JsonProperty("source_config")
    private final SourceConfig sourceConfig;

    @JsonProperty("is_enabled")
    private final Boolean isEnabled;

    private UpdateResourceTypeRequest(Builder builder) {
        this.id = builder.id;
        this.displayName = builder.displayName;
        this.description = builder.description;
        this.scope = builder.scope;
        this.refreshStrategy = builder.refreshStrategy;
        this.sourceConfig = builder.sourceConfig;
        this.isEnabled = builder.isEnabled;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets the unique identifier for the resource type to update.
     * <p>
     * Note: This is used as a path parameter, not included in the request body.
     *
     * @return the resource type ID
     */
    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public Scope getScope() {
        return scope;
    }

    public RefreshStrategy getRefreshStrategy() {
        return refreshStrategy;
    }

    public SourceConfig getSourceConfig() {
        return sourceConfig;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public static class Builder {
        private String id;
        private String displayName;
        private String description;
        private Scope scope;
        private RefreshStrategy refreshStrategy;
        private SourceConfig sourceConfig;
        private Boolean isEnabled;

        private Builder() {
        }

        /**
         * Sets the unique identifier for the resource type to update (required).
         *
         * @param id the resource type ID
         * @return this builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the human-readable name for the resource type.
         *
         * @param displayName the display name
         * @return this builder
         */
        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        /**
         * Sets the description for the resource type.
         *
         * @param description the description
         * @return this builder
         */
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the scope determining when in the conversation the resource is fetched and used.
         *
         * @param scope the scope
         * @return this builder
         */
        public Builder scope(Scope scope) {
            this.scope = scope;
            return this;
        }

        /**
         * Sets the refresh strategy determining how often the resource is re-fetched.
         *
         * @param refreshStrategy the refresh strategy
         * @return this builder
         */
        public Builder refreshStrategy(RefreshStrategy refreshStrategy) {
            this.refreshStrategy = refreshStrategy;
            return this;
        }

        /**
         * Sets the source configuration for how the resource is fetched.
         *
         * @param sourceConfig the source config
         * @return this builder
         */
        public Builder sourceConfig(SourceConfig sourceConfig) {
            this.sourceConfig = sourceConfig;
            return this;
        }

        /**
         * Sets whether the resource type should be enabled.
         *
         * @param isEnabled true to enable, false to disable
         * @return this builder
         */
        public Builder isEnabled(Boolean isEnabled) {
            this.isEnabled = isEnabled;
            return this;
        }

        /**
         * Builds the request.
         *
         * @return a new request instance
         * @throws IllegalStateException if required fields are missing
         */
        public UpdateResourceTypeRequest build() {
            if (id == null || id.isBlank()) {
                throw new IllegalStateException("id is required");
            }
            return new UpdateResourceTypeRequest(this);
        }
    }
}
