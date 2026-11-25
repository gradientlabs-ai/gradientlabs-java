package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/** The HTTP request used to refresh a secret. */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SecretRefreshRequest(
        @JsonProperty("method") String method,
        @JsonProperty("url_template") String urlTemplate,
        @JsonProperty("header_templates") Map<String, String> headerTemplates,
        @JsonProperty("body") HTTPBodyDefinition body,
        @JsonProperty("retry") SecretRefreshRetry retry) {
}
