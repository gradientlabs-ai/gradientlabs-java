package ai.gradientlabs.client.exception;

/**
 * Exception thrown when a webhook signature cannot be verified.
 * <p>
 * When this exception is thrown, you should respond to the webhook request
 * with HTTP 401 Unauthorized.
 */
public class InvalidWebhookSignatureException extends GradientLabsException {

    public InvalidWebhookSignatureException(String message) {
        super(message);
    }

    public InvalidWebhookSignatureException(String message, Throwable cause) {
        super(message, cause);
    }
}
