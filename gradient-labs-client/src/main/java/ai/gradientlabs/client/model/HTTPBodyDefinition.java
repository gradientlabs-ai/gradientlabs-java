package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Defines how the HTTP body should be encoded.
 */
public class HTTPBodyDefinition {

    @JsonProperty("encoding")
    private String encoding;

    @JsonProperty("json_template")
    private String jsonTemplate;

    @JsonProperty("form_field_templates")
    private Map<String, String> formFieldTemplates;

    /**
     * Default constructor for Jackson.
     */
    public HTTPBodyDefinition() {
    }

    public String getEncoding() {
        return encoding;
    }

    public String getJsonTemplate() {
        return jsonTemplate;
    }

    public Map<String, String> getFormFieldTemplates() {
        return formFieldTemplates;
    }
}
