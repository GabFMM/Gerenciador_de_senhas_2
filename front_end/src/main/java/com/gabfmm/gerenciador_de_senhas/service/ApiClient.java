package com.gabfmm.gerenciador_de_senhas.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ApiClient {

    // -- Attributes --

    private final HttpClient client;

    // -- Methods --

    private String serialize(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(obj);
    }

    private HttpRequest.Builder baseRequest(String url, String param){
        String paramEncoded = URLEncoder.encode(param, StandardCharsets.UTF_8);

        return HttpRequest
                .newBuilder()
                .uri(URI.create(url + paramEncoded))
                .header("Content-Type", "application/json");
    }

    public ApiClient(){
        client = HttpClient.newHttpClient();
    }

    public HttpResponse<String> get(String url, String param, String token) throws IOException, InterruptedException {
        HttpRequest request =
                baseRequest(url, param)
                        .header("Authorization", "Bearer " + token)
                        .GET()
                        .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> post(String url, Object body) throws IOException, InterruptedException {
        String json = serialize(body);

        HttpRequest request =
                baseRequest(url, "")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> post(String url, String token, Object body) throws IOException, InterruptedException {
        String json = serialize(body);

        HttpRequest request =
                baseRequest(url, "")
                .header("Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> put(String url, String token, Object body) throws IOException, InterruptedException {
        String json = serialize(body);

        HttpRequest request =
                baseRequest(url, "")
                        .header("Authorization", "Bearer " + token)
                        .PUT(HttpRequest.BodyPublishers.ofString(json))
                        .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> patch(String url, String token, Object body) throws IOException, InterruptedException {
        String json = serialize(body);

        HttpRequest request =
                baseRequest(url, "")
                .header("Authorization", "Bearer " + token)
                .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> delete(String url, String token, Object body) throws IOException, InterruptedException {
        String json = serialize(body);

        HttpRequest request = baseRequest(url, "")
                .header("Authorization", "Bearer " + token)
                .method("DELETE", HttpRequest.BodyPublishers.ofString(json))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}