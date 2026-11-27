package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * The body of an HTTP request made by a tool or a secret refresh.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    /**
     * Creates a new instance.
     *
     * @param encoding the body encoding: {@code application/json} or {@code application/x-www-form-urlencoded}.
     * @param jsonTemplate the template for a JSON body.
     * @param formFieldTemplates the templates for form fields, by field name.
     */
    public HTTPBodyDefinition(String encoding, String jsonTemplate, Map<String, String> formFieldTemplates) {
        this.encoding = encoding;
        this.jsonTemplate = jsonTemplate;
        this.formFieldTemplates = formFieldTemplates;
    }

    /**
     * Returns the body encoding: {@code application/json} or {@code application/x-www-form-urlencoded}.
     *
     * @return the encoding
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Sets the body encoding: {@code application/json} or {@code application/x-www-form-urlencoded}.
     *
     * @param encoding the encoding
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * Returns the template for a JSON body.
     *
     * @return the json template
     */
    public String getJsonTemplate() {
        return jsonTemplate;
    }

    /**
     * Sets the template for a JSON body.
     *
     * @param jsonTemplate the json template
     */
    public void setJsonTemplate(String jsonTemplate) {
        this.jsonTemplate = jsonTemplate;
    }

    /**
     * Returns the templates for form fields, by field name.
     *
     * @return the form field templates
     */
    public Map<String, String> getFormFieldTemplates() {
        return formFieldTemplates;
    }

    /**
     * Sets the templates for form fields, by field name.
     *
     * @param formFieldTemplates the form field templates
     */
    public void setFormFieldTemplates(Map<String, String> formFieldTemplates) {
        this.formFieldTemplates = formFieldTemplates;
    }
}
