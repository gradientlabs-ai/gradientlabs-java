package ai.gradientlabs.client.request;

/**
 * Optional parameters for reading the latest voice call context for a phone number.
 */
public class ReadVoiceCallContextRequest {

    private final Integer lookbackSeconds;
    private final Boolean includeLargeFields;

    private ReadVoiceCallContextRequest(Builder builder) {
        this.lookbackSeconds = builder.lookbackSeconds;
        this.includeLargeFields = builder.includeLargeFields;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getLookbackSeconds() {
        return lookbackSeconds;
    }

    public Boolean getIncludeLargeFields() {
        return includeLargeFields;
    }

    public static class Builder {
        private Integer lookbackSeconds;
        private Boolean includeLargeFields;

        private Builder() {
        }

        public Builder lookbackSeconds(Integer lookbackSeconds) {
            this.lookbackSeconds = lookbackSeconds;
            return this;
        }

        public Builder includeLargeFields(Boolean includeLargeFields) {
            this.includeLargeFields = includeLargeFields;
            return this;
        }

        public ReadVoiceCallContextRequest build() {
            return new ReadVoiceCallContextRequest(this);
        }
    }
}
