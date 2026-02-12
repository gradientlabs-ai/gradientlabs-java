package ai.gradientlabs.client.exception;

import java.util.Map;

/**
 * Exception thrown when the API returns an error response.
 * <p>
 * Contains the HTTP status code, error message, and optional details including
 * a trace ID that can be provided to Gradient Labs support for investigation.
 */
public class ResponseException extends GradientLabsException {

    private final int statusCode;
    private final String message;
    private final Map<String, Object> details;

    public ResponseException(int statusCode, String message, Map<String, Object> details) {
        super(formatMessage(statusCode, message, details));
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;
    }

    private static String formatMessage(int statusCode, String message, Map<String, Object> details) {
        StringBuilder sb = new StringBuilder();

        if (message == null || message.isBlank()) {
            sb.append("Unexpected response status: ").append(statusCode);
        } else {
            sb.append(message);
        }

        String traceId = extractTraceId(details);
        if (traceId != null) {
            sb.append(" (trace id: ").append(traceId).append(")");
        }

        return sb.toString();
    }

    private static String extractTraceId(Map<String, Object> details) {
        if (details != null && details.containsKey("trace_id")) {
            Object traceId = details.get("trace_id");
            return traceId != null ? traceId.toString() : null;
        }
        return null;
    }

    /**
     * Returns the HTTP status code of the error response.
     *
     * @return the status code
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Returns the error message from the API.
     *
     * @return the error message
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Returns additional error details from the API.
     *
     * @return the error details, or null if none provided
     */
    public Map<String, Object> getDetails() {
        return details;
    }

    /**
     * Returns the trace ID that can be provided to Gradient Labs support.
     *
     * @return the trace ID, or null if not available
     */
    public String getTraceId() {
        return extractTraceId(details);
    }
}
