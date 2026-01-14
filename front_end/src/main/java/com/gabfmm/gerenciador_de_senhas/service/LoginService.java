package com.gabfmm.gerenciador_de_senhas.service;

import com.gabfmm.gerenciador_de_senhas.dto.ApiErrorDTO;
import com.gabfmm.gerenciador_de_senhas.dto.UserDTO;
import com.gabfmm.gerenciador_de_senhas.exception.UserNotFoundException;
import com.gabfmm.gerenciador_de_senhas.util.HttpStatusCode;
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
