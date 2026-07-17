package ai.gradientlabs.client;

import ai.gradientlabs.client.internal.HttpClientWrapper;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.WebSocket;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Exercises the DELETE plumbing that {@code deleteConversation} and
 * {@code deleteBackOfficeTask} delegate to.
 */
class GradientLabsClientTest {

    @Test
    void deleteSendsDeleteRequestWithoutBodyAndReturnsNullOnAccepted() {
        RecordingHttpClient httpClient = new RecordingHttpClient();
        HttpClientWrapper wrapper = new HttpClientWrapper(httpClient, "test-key", "test-agent");

        Void result = wrapper.delete("https://api.example.com/conversations/conv-123", Void.class);

        assertNull(result);
        assertEquals("DELETE", httpClient.lastRequest.method());
        assertEquals("https://api.example.com/conversations/conv-123", httpClient.lastRequest.uri().toString());
        assertTrue(httpClient.lastRequest.bodyPublisher().isPresent());
        assertEquals(0, httpClient.lastRequest.bodyPublisher().get().contentLength());
    }

    @Test
    void deleteSendsDeleteToBackOfficeTaskPath() {
        RecordingHttpClient httpClient = new RecordingHttpClient();
        HttpClientWrapper wrapper = new HttpClientWrapper(httpClient, "test-key", "test-agent");

        wrapper.delete("https://api.example.com/back-office-tasks/task-456", Void.class);

        assertEquals("DELETE", httpClient.lastRequest.method());
        assertEquals("https://api.example.com/back-office-tasks/task-456", httpClient.lastRequest.uri().toString());
    }

    /**
     * Minimal {@link HttpClient} that records the last request and always replies
     * with an empty 202 Accepted response, mirroring the API's delete endpoints.
     */
    private static final class RecordingHttpClient extends HttpClient {
        private HttpRequest lastRequest;

        @Override
        @SuppressWarnings("unchecked")
        public <T> HttpResponse<T> send(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler) {
            this.lastRequest = request;
            return (HttpResponse<T>) new AcceptedResponse(request);
        }

        @Override
        public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler) {
            return CompletableFuture.completedFuture(send(request, responseBodyHandler));
        }

        @Override
        public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler, HttpResponse.PushPromiseHandler<T> pushPromiseHandler) {
            return CompletableFuture.completedFuture(send(request, responseBodyHandler));
        }

        @Override
        public Optional<CookieHandler> cookieHandler() {
            return Optional.empty();
        }

        @Override
        public Optional<Duration> connectTimeout() {
            return Optional.empty();
        }

        @Override
        public Redirect followRedirects() {
            return Redirect.NEVER;
        }

        @Override
        public Optional<ProxySelector> proxy() {
            return Optional.empty();
        }

        @Override
        public SSLContext sslContext() {
            try {
                return SSLContext.getDefault();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public SSLParameters sslParameters() {
            return new SSLParameters();
        }

        @Override
        public Optional<Authenticator> authenticator() {
            return Optional.empty();
        }

        @Override
        public Version version() {
            return Version.HTTP_1_1;
        }

        @Override
        public Optional<Executor> executor() {
            return Optional.empty();
        }

        @Override
        public WebSocket.Builder newWebSocketBuilder() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class AcceptedResponse implements HttpResponse<String> {
        private final HttpRequest request;

        private AcceptedResponse(HttpRequest request) {
            this.request = request;
        }

        @Override
        public int statusCode() {
            return 202;
        }

        @Override
        public HttpRequest request() {
            return request;
        }

        @Override
        public Optional<HttpResponse<String>> previousResponse() {
            return Optional.empty();
        }

        @Override
        public HttpHeaders headers() {
            return HttpHeaders.of(Map.of(), (a, b) -> true);
        }

        @Override
        public String body() {
            return "";
        }

        @Override
        public Optional<SSLSession> sslSession() {
            return Optional.empty();
        }

        @Override
        public URI uri() {
            return request.uri();
        }

        @Override
        public HttpClient.Version version() {
            return HttpClient.Version.HTTP_1_1;
        }
    }
}
