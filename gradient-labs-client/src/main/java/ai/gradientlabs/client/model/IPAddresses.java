package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * The IP address ranges used by the Gradient Labs platform.
 */
public class IPAddresses {

    @JsonProperty("api")
    private List<String> api;

    @JsonProperty("egress")
    private List<String> egress;

    public List<String> getApi() {
        return api;
    }

    public List<String> getEgress() {
        return egress;
    }
}
