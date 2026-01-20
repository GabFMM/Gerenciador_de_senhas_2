package com.gabfmm.gerenciador_de_senhas.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabfmm.gerenciador_de_senhas.dto.ApiErrorDTO;
import com.gabfmm.gerenciador_de_senhas.dto.PasswordGenerationRequestDTO;
import com.gabfmm.gerenciador_de_senhas.dto.PasswordGenerationResponseDTO;
import com.gabfmm.gerenciador_de_senhas.exception.PasswordGenerationException;
import com.gabfmm.gerenciador_de_senhas.util.HttpStatusCode;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PasswordGeneratorService {

    // -- private --

    private void verify(PasswordGenerationRequestDTO passwordGenerationRequestDTO){
        if(passwordGenerationRequestDTO.tam() < 1 || passwordGenerationRequestDTO.tam() > 100) {
            throw new PasswordGenerationException("Erro ao gerar a senha", "O tamanho da senha é inválido");
        }
        else if (
                !passwordGenerationRequestDTO.alphabetUpperCase() &&
                !passwordGenerationRequestDTO.alphabetLowerCase() &&
                !passwordGenerationRequestDTO.numeric() &&
                !passwordGenerationRequestDTO.specialCharacters()) {
            throw new PasswordGenerationException("Erro ao gerar senha", "Deve ser selecionada pelo menos uma opção");
        }
    }

    // -- public --

    public PasswordGeneratorService(){}

    public String generatePassword(PasswordGenerationRequestDTO passwordGenerationRequestDTO) throws IOException,
            InterruptedException {

        // this can throw an exception
        verify(passwordGenerationRequestDTO);

        HttpClient client = HttpClient.newHttpClient();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(passwordGenerationRequestDTO);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/passwords"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        HttpStatusCode statusCode = HttpStatusCode.from(response.statusCode());

        if(statusCode == HttpStatusCode.OK){
            PasswordGenerationResponseDTO passwordResponse = mapper.readValue(response.body(),
                    PasswordGenerationResponseDTO.class);

            return passwordResponse.password();
        }

        System.out.println(response.body());

        ApiErrorDTO apiErrorDTO = mapper.readValue(response.body(), ApiErrorDTO.class);
        throw new PasswordGenerationException(apiErrorDTO.title(), apiErrorDTO.detail());
    }
}
