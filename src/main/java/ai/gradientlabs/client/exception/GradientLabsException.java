package ai.gradientlabs.client.exception;

/**
 * Base exception for all Gradient Labs client errors.
 * <p>
 * This is an unchecked exception as most client errors are non-recoverable
 * and should be handled at application boundaries.
 */
public class GradientLabsException extends RuntimeException {

    public GradientLabsException(String message) {
        super(message);
    }

    public GradientLabsException(String message, Throwable cause) {
        super(message, cause);
    }

    public GradientLabsException(Throwable cause) {
        super(cause);
    }
}
