package com.example.gerenciador_de_senhas_v2.service;

import com.example.gerenciador_de_senhas_v2.dto.ApiErrorDTO;
import com.example.gerenciador_de_senhas_v2.dto.UserDTO;
import com.example.gerenciador_de_senhas_v2.exception.UserNotFoundException;
import com.example.gerenciador_de_senhas_v2.util.HttpStatusCode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginService {

    public LoginService(){}

    public void verifyUser(UserDTO user) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/auth/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        HttpStatusCode status = HttpStatusCode.from(response.statusCode());
        if(status == HttpStatusCode.OK) return;

        ApiErrorDTO error = mapper.readValue(response.body(), ApiErrorDTO.class);
        throw new UserNotFoundException(error.title(), error.detail());
    }
}
