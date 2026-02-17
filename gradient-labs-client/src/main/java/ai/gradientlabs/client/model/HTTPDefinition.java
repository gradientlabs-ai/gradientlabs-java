package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Defines an HTTP request configuration.
 */
public class HTTPDefinition {

    @JsonProperty("method")
    private String method;

    @JsonProperty("url_template")
    private String urlTemplate;

    @JsonProperty("header_templates")
    private Map<String, String> headerTemplates;

    @JsonProperty("body")
    private HTTPBodyDefinition body;

    /**
     * Default constructor for Jackson.
     */
    public HTTPDefinition() {
    }

    public String getMethod() {
        return method;
    }

    public String getUrlTemplate() {
        return urlTemplate;
    }

    public Map<String, String> getHeaderTemplates() {
        return headerTemplates;
    }

    public HTTPBodyDefinition getBody() {
        return body;
    }
}
