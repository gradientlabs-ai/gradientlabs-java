package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Defines an HTTP request made by a tool.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HTTPDefinition {

    @JsonProperty("method")
    private String method;

    @JsonProperty("url_template")
    private String urlTemplate;

    @JsonProperty("header_templates")
    private Map<String, String> headerTemplates;

    @JsonProperty("body")
    private HTTPBodyDefinition body;

    @JsonProperty("retry")
    private RetryPolicy retry;

    /**
     * Default constructor for Jackson.
     */
    public HTTPDefinition() {
    }

    /**
     * Creates a new instance.
     *
     * @param method the HTTP method.
     * @param urlTemplate the URL template.
     * @param headerTemplates the header templates, by header name.
     * @param body the request body definition.
     * @param retry the retry policy override, or null for the default.
     */
    public HTTPDefinition(String method, String urlTemplate, Map<String, String> headerTemplates, HTTPBodyDefinition body, RetryPolicy retry) {
        this.method = method;
        this.urlTemplate = urlTemplate;
        this.headerTemplates = headerTemplates;
        this.body = body;
        this.retry = retry;
    }

    /**
     * Returns the HTTP method.
     *
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets the HTTP method.
     *
     * @param method the method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Returns the URL template.
     *
     * @return the url template
     */
    public String getUrlTemplate() {
        return urlTemplate;
    }

    /**
     * Sets the URL template.
     *
     * @param urlTemplate the url template
     */
    public void setUrlTemplate(String urlTemplate) {
        this.urlTemplate = urlTemplate;
    }

    /**
     * Returns the header templates, by header name.
     *
     * @return the header templates
     */
    public Map<String, String> getHeaderTemplates() {
        return headerTemplates;
    }

    /**
     * Sets the header templates, by header name.
     *
     * @param headerTemplates the header templates
     */
    public void setHeaderTemplates(Map<String, String> headerTemplates) {
        this.headerTemplates = headerTemplates;
    }

    /**
     * Returns the request body definition.
     *
     * @return the body
     */
    public HTTPBodyDefinition getBody() {
        return body;
    }

    /**
     * Sets the request body definition.
     *
     * @param body the body
     */
    public void setBody(HTTPBodyDefinition body) {
        this.body = body;
    }

    /**
     * Returns the retry policy override, or null for the default.
     *
     * @return the retry
     */
    public RetryPolicy getRetry() {
        return retry;
    }

    /**
     * Sets the retry policy override, or null for the default.
     *
     * @param retry the retry
     */
    public void setRetry(RetryPolicy retry) {
        this.retry = retry;
    }
}
