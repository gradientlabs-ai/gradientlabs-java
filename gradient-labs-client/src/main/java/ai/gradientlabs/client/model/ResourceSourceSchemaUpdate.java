package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/** The JSON Schema to apply to a resource source. */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResourceSourceSchemaUpdate(
        @JsonProperty("raw") Map<String, Object> raw) {
}
