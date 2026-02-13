package com.gabfmm.gerenciador_de_senhas.service;

import com.gabfmm.gerenciador_de_senhas.auth.AuthSession;
import com.gabfmm.gerenciador_de_senhas.dto.error.ApiErrorDTO;
import com.gabfmm.gerenciador_de_senhas.dto.user.*;
import com.gabfmm.gerenciador_de_senhas.exception.NewUserException;

import com.gabfmm.gerenciador_de_senhas.exception.UserDeleteException;
import com.gabfmm.gerenciador_de_senhas.exception.UserNotFoundException;
import com.gabfmm.gerenciador_de_senhas.exception.UserUpdateException;
import com.gabfmm.gerenciador_de_senhas.util.HttpStatusCode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.http.HttpResponse;

public class UserService extends BaseService{

    // -- private --

    private void persistNewUser(NewUserDTO newUser) throws IOException, InterruptedException {

        HttpResponse<String> response = apiClient.post("http://localhost:8080/users", newUser);

        HttpStatusCode status = HttpStatusCode.from(response.statusCode());

        if(status == HttpStatusCode.CREATED) return;

        ObjectMapper mapper = new ObjectMapper();
        ApiErrorDTO error = mapper.readValue(response.body(), ApiErrorDTO.class);
        throw new NewUserException(error.title(), error.detail());
    }

    private void verifyFieldsNewUser(NewUserDTO newUser, String confirmPassword){
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

    public void saveNewUser(NewUserDTO newUser, String confirmPassword) throws IOException, InterruptedException {
        verifyFieldsNewUser(newUser, confirmPassword);
        persistNewUser(newUser);
    }

    public UserUpdateInfoDTO saveUser(UserUpdateDTO user) throws IOException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();
        HttpResponse<String> response = apiClient.put("http://localhost:8080/users/me", AuthSession.getToken(), user);

        HttpStatusCode status = HttpStatusCode.from(response.statusCode());

        if(status == HttpStatusCode.OK) {
            return mapper.readValue(response.body(), UserUpdateInfoDTO.class);
        }

        ApiErrorDTO error = mapper.readValue(response.body(), ApiErrorDTO.class);
        throw new UserUpdateException(error.title(), error.detail());
    }

    public String getUsername() throws IOException, InterruptedException {

        HttpResponse<String> response = apiClient.get("http://localhost:8080/users/me/username", "", AuthSession.getToken());

        HttpStatusCode status = HttpStatusCode.from(response.statusCode());

        ObjectMapper mapper = new ObjectMapper();

        if(status == HttpStatusCode.OK){
            UsernameDTO usernameDTO = mapper.readValue(response.body(), UsernameDTO.class);
            return usernameDTO.username();
        }

        ApiErrorDTO error = mapper.readValue(response.body(), ApiErrorDTO.class);
        throw new UserNotFoundException(error.title(), error.detail());
    }

    public void delete(DeleteUserDTO deleteUserDTO) throws IOException, InterruptedException {

        HttpResponse<String> response = apiClient.delete("http://localhost:8080/users/me", AuthSession.getToken(), deleteUserDTO);

        HttpStatusCode status = HttpStatusCode.from(response.statusCode());
        if(status == HttpStatusCode.NO_CONTENT) return;

        ObjectMapper mapper = new ObjectMapper();
        ApiErrorDTO error = mapper.readValue(response.body(), ApiErrorDTO.class);
        throw new UserDeleteException(error.title(), error.detail());
    }
}
