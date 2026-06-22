package ai.gradientlabs.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * Parameters for submitting a customer (CSAT) rating for a conversation.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RateConversationRequest {

    @JsonProperty("type")
    private final String type;

    @JsonProperty("value")
    private final int value;

    @JsonProperty("max_value")
    private final int maxValue;

    @JsonProperty("min_value")
    private final int minValue;

    @JsonProperty("comments")
    private final String comments;

    @JsonProperty("timestamp")
    private final Instant timestamp;

    private RateConversationRequest(Builder builder) {
        this.type = builder.type;
        this.value = builder.value;
        this.maxValue = builder.maxValue;
        this.minValue = builder.minValue;
        this.comments = builder.comments;
        this.timestamp = builder.timestamp;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public String getComments() {
        return comments;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public static class Builder {
        private String type;
        private int value;
        private int maxValue;
        private int minValue;
        private String comments;
        private Instant timestamp;

        private Builder() {
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder value(int value) {
            this.value = value;
            return this;
        }

        public Builder maxValue(int maxValue) {
            this.maxValue = maxValue;
            return this;
        }

        public Builder minValue(int minValue) {
            this.minValue = minValue;
            return this;
        }

        public Builder comments(String comments) {
            this.comments = comments;
            return this;
        }

        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public RateConversationRequest build() {
            if (type == null || type.isBlank()) {
                throw new IllegalStateException("type is required");
            }
            return new RateConversationRequest(this);
        }
    }
}
