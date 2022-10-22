package com.myplaylists.client;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncHttpClient {

    private static final int TIMEOUT = 3;
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    /**
     * POST 요청
     */
    public static String post(String uri, String data, Map<String, String> headers) throws ExecutionException, InterruptedException, TimeoutException {
        // 1. set uri
        HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(uri));

        // 2. set headers
        for (String name : headers.keySet()) {
            builder.header(name, headers.get(name));
        }

        // 3. set method(`post`) and body
        HttpRequest request = builder.POST(HttpRequest.BodyPublishers.ofString(data)).build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .get(TIMEOUT, TimeUnit.SECONDS);
    }

    /**
     * GET 요청 (쿼리 파라미터 설정)
     */
    public static String getWithQueryParams(String uri, Map<String, Object> queryParams) throws ExecutionException, InterruptedException, TimeoutException, MalformedURLException {
        return get(uri, queryParams, Collections.emptyMap());
    }

    /**
     * GET 요청 (헤더 설정)
     */
    public static String getWithHeaders(String uri, Map<String, String> headers) throws ExecutionException, InterruptedException, TimeoutException, MalformedURLException {
        return get(uri, Collections.emptyMap(), headers);
    }

    /**
     * GET 요청 (쿼리 파라미터 + 헤더 설정)
     */
    private static String get(String uri, Map<String, Object> queryParams, Map<String, String> headers) throws ExecutionException, InterruptedException, TimeoutException, MalformedURLException {
        // 1. set uri
        UriBuilder uriBuilder = new UriBuilder(uri);
        for (String key : queryParams.keySet()) {
            uriBuilder.addParam(key, queryParams.get(key));
        }
        HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(uriBuilder.build()));

        // 2. set headers
        for (String name : headers.keySet()) {
            builder.header(name, headers.get(name));
        }

        // 3. set method(`get`)
        HttpRequest request = builder.GET().build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .get(TIMEOUT, TimeUnit.SECONDS);
    }
}
