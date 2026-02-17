package ai.gradientlabs.client.request;

import ai.gradientlabs.client.model.RefreshStrategy;
import ai.gradientlabs.client.model.Scope;
import ai.gradientlabs.client.model.SourceConfig;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for creating a new resource type.
 * <p>
 * Requires a Management API key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateResourceTypeRequest {

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

    private CreateResourceTypeRequest(Builder builder) {
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
        private String displayName;
        private String description;
        private Scope scope;
        private RefreshStrategy refreshStrategy;
        private SourceConfig sourceConfig;
        private Boolean isEnabled;

        private Builder() {
        }

        /**
         * Sets the human-readable name for the resource type (required).
         *
         * @param displayName the display name
         * @return this builder
         */
        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        /**
         * Sets the optional description for the resource type.
         *
         * @param description the description
         * @return this builder
         */
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the scope determining when in the conversation the resource is fetched and used (required).
         *
         * @param scope the scope
         * @return this builder
         */
        public Builder scope(Scope scope) {
            this.scope = scope;
            return this;
        }

        /**
         * Sets the refresh strategy determining how often the resource is re-fetched (required).
         *
         * @param refreshStrategy the refresh strategy
         * @return this builder
         */
        public Builder refreshStrategy(RefreshStrategy refreshStrategy) {
            this.refreshStrategy = refreshStrategy;
            return this;
        }

        /**
         * Sets the optional source configuration for how the resource is fetched.
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
        public CreateResourceTypeRequest build() {
            if (displayName == null || displayName.isBlank()) {
                throw new IllegalStateException("displayName is required");
            }
            if (scope == null) {
                throw new IllegalStateException("scope is required");
            }
            if (refreshStrategy == null) {
                throw new IllegalStateException("refreshStrategy is required");
            }
            return new CreateResourceTypeRequest(this);
        }
    }
}
