package ai.gradientlabs.client.internal;

import ai.gradientlabs.client.exception.GradientLabsException;
import ai.gradientlabs.client.exception.ResponseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Internal HTTP client wrapper for making API requests.
 * <p>
 * This class is not part of the public API and may change without notice.
 */
public class HttpClientWrapper {

    private final HttpClient httpClient;
    private final String baseUrl;
    private final String apiKey;
    private final String userAgent;
    private final ObjectMapper objectMapper;

    /**
     * Creates a wrapper that sends every request to {@code baseUrl} followed by the request path.
     *
     * @param httpClient the underlying HTTP client
     * @param baseUrl    the API base URL, such as {@code https://api.gradient-labs.ai}
     * @param apiKey     the API key sent as a bearer token
     * @param userAgent  the User-Agent header value
     */
    public HttpClientWrapper(HttpClient httpClient, String baseUrl, String apiKey, String userAgent) {
        this.httpClient = httpClient;
        this.baseUrl = stripTrailingSlash(baseUrl == null ? "" : baseUrl);
        this.apiKey = apiKey;
        this.userAgent = userAgent;
        this.objectMapper = Json.newMapper();
    }

    /**
     * Creates a wrapper whose request paths are absolute URLs.
     *
     * @param httpClient the underlying HTTP client
     * @param apiKey     the API key sent as a bearer token
     * @param userAgent  the User-Agent header value
     */
    public HttpClientWrapper(HttpClient httpClient, String apiKey, String userAgent) {
        this(httpClient, "", apiKey, userAgent);
    }

    /**
     * Encodes a value for use as one path segment.
     *
     * @param value the raw value
     * @return the encoded segment
     */
    public static String pathSegment(String value) {
        if (value == null) {
            throw new IllegalArgumentException("path parameter must not be null");
        }
        return URLEncoder.encode(value, StandardCharsets.UTF_8).replace("+", "%20");
    }

    public <T> T get(String path, Map<String, ?> queryParams, Class<T> responseType) {
        return send("GET", withQuery(path, queryParams), null, responseType);
    }

    public <T> T post(String path, Object body, Class<T> responseType) {
        return send("POST", path, body, responseType);
    }

    public <T> T put(String path, Object body, Class<T> responseType) {
        return send("PUT", path, body, responseType);
    }

    public <T> T delete(String path, Class<T> responseType) {
        return send("DELETE", path, null, responseType);
    }

    public <T> T delete(String path, Map<String, ?> queryParams, Class<T> responseType) {
        return send("DELETE", withQuery(path, queryParams), null, responseType);
    }

    private <T> T send(String method, String path, Object body, Class<T> responseType) {
        try {
            HttpRequest request = buildRequest(method, path, body).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return handleResponse(response, responseType);
        } catch (GradientLabsException e) {
            throw e;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new GradientLabsException("Request interrupted", e);
        } catch (Exception e) {
            throw new GradientLabsException("Request failed", e);
        }
    }

    static String withQuery(String path, Map<String, ?> queryParams) {
        if (queryParams == null || queryParams.isEmpty()) {
            return path;
        }
        StringJoiner query = new StringJoiner("&");
        for (Map.Entry<String, ?> entry : queryParams.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            query.add(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "="
                    + URLEncoder.encode(String.valueOf(entry.getValue()), StandardCharsets.UTF_8));
        }
        if (query.length() == 0) {
            return path;
        }
        return path + (path.contains("?") ? "&" : "?") + query;
    }

    private HttpRequest.Builder buildRequest(String method, String path, Object body) {
        try {
            URI uri = URI.create(baseUrl + path);

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

    private static String stripTrailingSlash(String url) {
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }
}
