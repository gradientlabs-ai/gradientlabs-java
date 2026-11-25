package ai.gradientlabs.client.internal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Duration;

/**
 * Reads a {@link Duration} from a whole number of nanoseconds, the API's wire format for durations.
 * <p>
 * This class is not part of the public API and may change without notice.
 */
public class NanosDurationDeserializer extends StdDeserializer<Duration> {

    public NanosDurationDeserializer() {
        super(Duration.class);
    }

    @Override
    public Duration deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return Duration.ofNanos(p.getLongValue());
    }
}
