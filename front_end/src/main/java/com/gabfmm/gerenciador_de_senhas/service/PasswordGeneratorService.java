package com.gabfmm.gerenciador_de_senhas.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabfmm.gerenciador_de_senhas.auth.AuthSession;
import com.gabfmm.gerenciador_de_senhas.dto.error.ApiErrorDTO;
import com.gabfmm.gerenciador_de_senhas.dto.password.PasswordGenerationRequestDTO;
import com.gabfmm.gerenciador_de_senhas.dto.password.PasswordGenerationResponseDTO;
import com.gabfmm.gerenciador_de_senhas.exception.PasswordGenerationException;
import com.gabfmm.gerenciador_de_senhas.util.HttpStatusCode;

import java.io.IOException;
import java.net.http.HttpResponse;

public class PasswordGeneratorService extends BaseService{

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

        HttpResponse<String> response = apiClient.post("http://localhost:8080/passwords", AuthSession.getToken(), passwordGenerationRequestDTO);

        HttpStatusCode statusCode = HttpStatusCode.from(response.statusCode());

        ObjectMapper mapper = new ObjectMapper();

        if(statusCode == HttpStatusCode.OK){
            PasswordGenerationResponseDTO passwordResponse = mapper.readValue(response.body(),
                    PasswordGenerationResponseDTO.class);

            return passwordResponse.password();
        }

        ApiErrorDTO apiErrorDTO = mapper.readValue(response.body(), ApiErrorDTO.class);
        throw new PasswordGenerationException(apiErrorDTO.title(), apiErrorDTO.detail());
    }
}
