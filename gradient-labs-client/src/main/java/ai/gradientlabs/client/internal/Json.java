package ai.gradientlabs.client.internal;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Builds the JSON mapper shared by the HTTP client and the webhook verifier.
 * <p>
 * This class is not part of the public API and may change without notice.
 */
public final class Json {

    private Json() {
    }

    /**
     * Returns a mapper that writes timestamps as RFC 3339 strings and tolerates fields and enum
     * values the API adds later.
     *
     * @return a new mapper
     */
    public static ObjectMapper newMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
        return mapper;
    }
}
