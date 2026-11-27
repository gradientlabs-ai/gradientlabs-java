package ai.gradientlabs.client.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.Duration;

/**
 * Writes a {@link Duration} as a whole number of nanoseconds, the API's wire format for durations.
 * <p>
 * This class is not part of the public API and may change without notice.
 */
public class NanosDurationSerializer extends StdSerializer<Duration> {

    public NanosDurationSerializer() {
        super(Duration.class);
    }

    @Override
    public void serialize(Duration value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeNumber(value.toNanos());
    }
}
