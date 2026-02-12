package ai.gradientlabs.client.internal;

import ai.gradientlabs.client.exception.GradientLabsException;
import ai.gradientlabs.client.exception.ResponseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Internal HTTP client wrapper for making API requests.
 * <p>
 * This class is not part of the public API and may change without notice.
 */
public class HttpClientWrapper {

    private final HttpClient httpClient;
    private final String apiKey;
    private final String userAgent;
    private final ObjectMapper objectMapper;

    public HttpClientWrapper(HttpClient httpClient, String apiKey, String userAgent) {
        this.httpClient = httpClient;
        this.apiKey = apiKey;
        this.userAgent = userAgent;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public <T> T get(String path, Object queryParams, Class<T> responseType) {
        try {
            HttpRequest request = buildRequest("GET", path, null)
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return handleResponse(response, responseType);
        } catch (Exception e) {
            throw new GradientLabsException("Request failed", e);
        }
    }

    public <T> List<T> getList(String path, Object queryParams, Class<T> itemType) {
        try {
            HttpRequest request = buildRequest("GET", path, null)
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw parseError(response);
            }

            return objectMapper.readValue(
                    response.body(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, itemType)
            );
        } catch (ResponseException e) {
            throw e;
        } catch (Exception e) {
            throw new GradientLabsException("Request failed", e);
        }
    }

    public <T> T post(String path, Object body, Class<T> responseType) {
        try {
            HttpRequest request = buildRequest("POST", path, body)
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return handleResponse(response, responseType);
        } catch (ResponseException e) {
            throw e;
        } catch (Exception e) {
            throw new GradientLabsException("Request failed", e);
        }
    }

    public <T> CompletableFuture<T> postAsync(String path, Object body, Class<T> responseType) {
        try {
            HttpRequest request = buildRequest("POST", path, body)
                    .build();

            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> handleResponse(response, responseType));
        } catch (Exception e) {
            return CompletableFuture.failedFuture(new GradientLabsException("Request failed", e));
        }
    }

    public <T> T put(String path, Object body, Class<T> responseType) {
        try {
            HttpRequest request = buildRequest("PUT", path, body)
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return handleResponse(response, responseType);
        } catch (ResponseException e) {
            throw e;
        } catch (Exception e) {
            throw new GradientLabsException("Request failed", e);
        }
    }

    public <T> T delete(String path, Class<T> responseType) {
        try {
            HttpRequest request = buildRequest("DELETE", path, null)
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return handleResponse(response, responseType);
        } catch (ResponseException e) {
            throw e;
        } catch (Exception e) {
            throw new GradientLabsException("Request failed", e);
        }
    }

    public <T> T delete(String path, Object body, Class<T> responseType) {
        try {
            HttpRequest request = buildRequest("DELETE", path, body)
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return handleResponse(response, responseType);
        } catch (ResponseException e) {
            throw e;
        } catch (Exception e) {
            throw new GradientLabsException("Request failed", e);
        }
    }

    private HttpRequest.Builder buildRequest(String method, String path, Object body) {
        try {
            URI uri = URI.create(path);

            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(uri)
                    .timeout(Duration.ofSeconds(30))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Accept", "application/json")
                    .header("User-Agent", userAgent);

            if (body != null) {
                String jsonBody = objectMapper.writeValueAsString(body);
                builder.method(method, HttpRequest.BodyPublishers.ofString(jsonBody));
                builder.header("Content-Type", "application/json");
            } else {
                builder.method(method, HttpRequest.BodyPublishers.noBody());
            }

            return builder;
        } catch (Exception e) {
            throw new GradientLabsException("Failed to build request", e);
        }
    }

    private <T> T handleResponse(HttpResponse<String> response, Class<T> responseType) {
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw parseError(response);
        }

        if (responseType == Void.class || response.body() == null || response.body().isEmpty()) {
            return null;
        }

        try {
            return objectMapper.readValue(response.body(), responseType);
        } catch (Exception e) {
            throw new GradientLabsException("Failed to parse response", e);
        }
    }

    private ResponseException parseError(HttpResponse<String> response) {
        try {
            Map<String, Object> error = objectMapper.readValue(
                    response.body(),
                    new TypeReference<Map<String, Object>>() {
                    }
            );

            String message = (String) error.get("message");
            @SuppressWarnings("unchecked")
            Map<String, Object> details = (Map<String, Object>) error.get("details");

            return new ResponseException(response.statusCode(), message, details);
        } catch (Exception e) {
            // Fallback if we can't parse the error response
            return new ResponseException(
                    response.statusCode(),
                    "HTTP " + response.statusCode(),
                    null
            );
        }
    }
}
