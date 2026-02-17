package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Contains configuration for HTTP actions.
 */
public class ResourceHTTPDefinition {

    @JsonProperty("method")
    private String method;

    @JsonProperty("url_template")
    private String urlTemplate;

    @JsonProperty("header_templates")
    private Map<String, String> headerTemplates;

    @JsonProperty("body")
    private ResourceHTTPBodyDefinition body;

    /**
     * Default constructor for Jackson.
     */
    public ResourceHTTPDefinition() {
    }

    /**
     * Constructor with all fields.
     */
    public ResourceHTTPDefinition(String method, String urlTemplate, Map<String, String> headerTemplates, ResourceHTTPBodyDefinition body) {
        this.method = method;
        this.urlTemplate = urlTemplate;
        this.headerTemplates = headerTemplates;
        this.body = body;
    }

    /**
     * Gets the HTTP request method.
     *
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets the method.
     *
     * @param method the method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Gets the URL template used to construct the request URL.
     *
     * @return the URL template
     */
    public String getUrlTemplate() {
        return urlTemplate;
    }

    /**
     * Sets the URL template.
     *
     * @param urlTemplate the URL template
     */
    public void setUrlTemplate(String urlTemplate) {
        this.urlTemplate = urlTemplate;
    }

    /**
     * Gets the header templates for the request headers.
     *
     * @return the header templates
     */
    public Map<String, String> getHeaderTemplates() {
        return headerTemplates;
    }

    /**
     * Sets the header templates.
     *
     * @param headerTemplates the header templates
     */
    public void setHeaderTemplates(Map<String, String> headerTemplates) {
        this.headerTemplates = headerTemplates;
    }

    /**
     * Gets the body configuration.
     *
     * @return the body definition
     */
    public ResourceHTTPBodyDefinition getBody() {
        return body;
    }

    /**
     * Sets the body configuration.
     *
     * @param body the body definition
     */
    public void setBody(ResourceHTTPBodyDefinition body) {
        this.body = body;
    }
}
