package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The result of a completed back office task.
 */
public class BackOfficeTaskResult {

    @JsonProperty("result_type")
    private String resultType;

    @JsonProperty("custom")
    private Object custom;

    public String getResultType() {
        return resultType;
    }

    public Object getCustom() {
        return custom;
    }
}
