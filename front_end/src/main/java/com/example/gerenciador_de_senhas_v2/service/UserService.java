package com.example.gerenciador_de_senhas_v2.service;

import com.example.gerenciador_de_senhas_v2.dto.ApiErrorDTO;
import com.example.gerenciador_de_senhas_v2.dto.UserDTO;
import com.example.gerenciador_de_senhas_v2.exception.NewUserException;

import com.example.gerenciador_de_senhas_v2.util.HttpStatusCode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserService {

    // -- private --

    private void persistNewUser(UserDTO newUser) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(newUser);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/users"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        HttpStatusCode status = HttpStatusCode.from(response.statusCode());

        if(status == HttpStatusCode.CREATED) return;

        ApiErrorDTO error = mapper.readValue(response.body(), ApiErrorDTO.class);
        throw new NewUserException(error.title(), error.detail());
    }

    private void verifyFieldsNewUser(UserDTO newUser, String confirmPassword){
        if(newUser.name().isEmpty()){
            throw new NewUserException("Erro no campo usuário", "O campo usuário está vazio");
        }
        else if(newUser.password().isEmpty()){
            throw new NewUserException("Erro no campo senha", "O campo senha está vazio");
        }
        else if(newUser.password().length() < 8){
            throw new NewUserException("Erro no campo senha", "O campo senha tem tamanho menor do que 8 caracteres");
        }
        else if(confirmPassword.isEmpty()){
            throw new NewUserException("Erro no campo confirmar senha", "O campo confirmar senha está vazio");
        }
        else if(!newUser.password().equals(confirmPassword)){
            throw new NewUserException("Erro nos campos senha e confirmar senha", "Os campos senha e confirmar senha não são correspondentes");
        }
    }

    // -- public --

    public void saveNewUser(UserDTO newUser, String confirmPassword) throws IOException, InterruptedException {
        verifyFieldsNewUser(newUser, confirmPassword);
        persistNewUser(newUser);
    }
}
