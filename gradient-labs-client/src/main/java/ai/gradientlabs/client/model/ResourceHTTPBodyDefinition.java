package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Determines how the HTTP request body is constructed.
 */
public class ResourceHTTPBodyDefinition {

    @JsonProperty("encoding")
    private String encoding;

    @JsonProperty("json_template")
    private String jsonTemplate;

    @JsonProperty("form_field_templates")
    private Map<String, String> formFieldTemplates;

    /**
     * Default constructor for Jackson.
     */
    public ResourceHTTPBodyDefinition() {
    }

    /**
     * Constructor with all fields.
     */
    public ResourceHTTPBodyDefinition(String encoding, String jsonTemplate, Map<String, String> formFieldTemplates) {
        this.encoding = encoding;
        this.jsonTemplate = jsonTemplate;
        this.formFieldTemplates = formFieldTemplates;
    }

    /**
     * Gets the encoding type for the HTTP request body.
     *
     * @return the encoding
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Sets the encoding.
     *
     * @param encoding the encoding
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * Gets the JSON template for the HTTP request body.
     * Only used when encoding is "application/json".
     *
     * @return the JSON template
     */
    public String getJsonTemplate() {
        return jsonTemplate;
    }

    /**
     * Sets the JSON template.
     *
     * @param jsonTemplate the JSON template
     */
    public void setJsonTemplate(String jsonTemplate) {
        this.jsonTemplate = jsonTemplate;
    }

    /**
     * Gets the form field templates for the HTTP request body.
     * Only used when encoding is "application/x-www-form-urlencoded".
     *
     * @return the form field templates
     */
    public Map<String, String> getFormFieldTemplates() {
        return formFieldTemplates;
    }

    /**
     * Sets the form field templates.
     *
     * @param formFieldTemplates the form field templates
     */
    public void setFormFieldTemplates(Map<String, String> formFieldTemplates) {
        this.formFieldTemplates = formFieldTemplates;
    }
}
