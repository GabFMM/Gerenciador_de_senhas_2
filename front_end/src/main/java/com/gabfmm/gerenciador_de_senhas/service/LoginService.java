package com.gabfmm.gerenciador_de_senhas.service;

import com.gabfmm.gerenciador_de_senhas.auth.AuthSession;
import com.gabfmm.gerenciador_de_senhas.dto.error.ApiErrorDTO;
import com.gabfmm.gerenciador_de_senhas.dto.auth.TokenDTO;
import com.gabfmm.gerenciador_de_senhas.dto.user.UserLoginDTO;
import com.gabfmm.gerenciador_de_senhas.exception.UserNotFoundException;
import com.gabfmm.gerenciador_de_senhas.util.HttpStatusCode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.http.HttpResponse;

public class LoginService extends BaseService{

    public LoginService(){}

    public void verifyUser(UserLoginDTO user) throws IOException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();

        HttpResponse<String> response = apiClient.post("http://localhost:8080/auth/login", user);

        HttpStatusCode status = HttpStatusCode.from(response.statusCode());
        if(status == HttpStatusCode.OK){
            TokenDTO tokenDTO = mapper.readValue(response.body(), TokenDTO.class);
            AuthSession.setToken(tokenDTO.token());
            return;
        }

        ApiErrorDTO error = mapper.readValue(response.body(), ApiErrorDTO.class);
        throw new UserNotFoundException(error.title(), error.detail());
    }
}
