package com.gabfmm.gerenciador_de_senhas.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabfmm.gerenciador_de_senhas.auth.AuthSession;
import com.gabfmm.gerenciador_de_senhas.controller.menu.account.AccountTabPaneController;
import com.gabfmm.gerenciador_de_senhas.dto.account.AccountDTO;
import com.gabfmm.gerenciador_de_senhas.dto.account.AccountUpdateDTO;
import com.gabfmm.gerenciador_de_senhas.dto.account.AccountUpdateInfoDTO;
import com.gabfmm.gerenciador_de_senhas.dto.account.DeleteAccountDTO;
import com.gabfmm.gerenciador_de_senhas.dto.error.ApiErrorDTO;
import com.gabfmm.gerenciador_de_senhas.exception.AccountException;
import com.gabfmm.gerenciador_de_senhas.util.HttpStatusCode;
import com.gabfmm.gerenciador_de_senhas.util.UpdateType;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

public class AccountService extends BaseService{

    private static AccountTabPaneController accountTabPaneController;

    private void verifyNewAccount(AccountDTO account){
        if(account.title().isBlank())
            throw new AccountException("Erro", "O título da conta não pode ser vazio");

        if(account.password().isBlank())
            throw new AccountException("Erro", "A senha da conta não pode ser vazia");
    }

    public AccountService(){}

    public static void setAccountTabPaneController(
            AccountTabPaneController accountTabPaneController) {
        AccountService.accountTabPaneController = accountTabPaneController;
    }

    public AccountDTO getAccount(String title) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();

        HttpResponse<String> response =
                apiClient.get("http://localhost:8080/users/me/accounts?title=", title, AuthSession.getToken());

        HttpStatusCode statusCode = HttpStatusCode.from(response.statusCode());
        if(statusCode == HttpStatusCode.OK)
            return mapper.readValue(response.body(), AccountDTO.class);

        ApiErrorDTO error = mapper.readValue(response.body(), ApiErrorDTO.class);
        throw new AccountException(error.title(), error.detail());
    }

    public List<AccountDTO> getAccounts() throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();

        HttpResponse<String> response =
                apiClient.get("http://localhost:8080/users/me/accounts", "",AuthSession.getToken());

        HttpStatusCode statusCode = HttpStatusCode.from(response.statusCode());
        if(statusCode == HttpStatusCode.OK)
            return mapper.readValue(response.body(), new TypeReference<List<AccountDTO>>() {});

        ApiErrorDTO error = mapper.readValue(response.body(), ApiErrorDTO.class);
        throw new AccountException(error.title(), error.detail());
    }

    public List<AccountDTO> getAccountsThatContains(String prefixTitle) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();

        HttpResponse<String> response =
                apiClient.get("http://localhost:8080/users/me/accounts?title-contains=", prefixTitle,
                        AuthSession.getToken());

        HttpStatusCode statusCode = HttpStatusCode.from(response.statusCode());
        if(statusCode == HttpStatusCode.OK)
            return mapper.readValue(response.body(), new TypeReference<List<AccountDTO>>() {});

        ApiErrorDTO error = mapper.readValue(response.body(), ApiErrorDTO.class);
        throw new AccountException(error.title(), error.detail());
    }

    public void saveNewAccount(AccountDTO accountDTO) throws IOException, InterruptedException {
        // this can throw exception
        verifyNewAccount(accountDTO);

        ObjectMapper mapper = new ObjectMapper();

        HttpResponse<String> response =
                apiClient.post("http://localhost:8080/users/me/accounts",
                        AuthSession.getToken(),
                        accountDTO);

        HttpStatusCode statusCode = HttpStatusCode.from(response.statusCode());
        if(statusCode == HttpStatusCode.CREATED) {
            accountTabPaneController.updateTabs(accountDTO.title(), UpdateType.CREATED);
            return;
        }

        ApiErrorDTO error = mapper.readValue(response.body(), ApiErrorDTO.class);
        throw new AccountException(error.title(), error.detail());
    }

    public AccountUpdateInfoDTO saveAccount(AccountUpdateDTO accountDTO) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();

        HttpResponse<String> response =
                apiClient.put("http://localhost:8080/users/me/accounts",
                        AuthSession.getToken(),
                        accountDTO);

        HttpStatusCode statusCode = HttpStatusCode.from(response.statusCode());
        if(statusCode == HttpStatusCode.OK) {
            accountTabPaneController.updateTabs(accountDTO.newTitle(), UpdateType.EDITED);
            return mapper.readValue(response.body(), AccountUpdateInfoDTO.class);
        }

        ApiErrorDTO error = mapper.readValue(response.body(), ApiErrorDTO.class);
        throw new AccountException(error.title(), error.detail());
    }

    public void delete(DeleteAccountDTO deleteAccountDTO) throws IOException, InterruptedException {

        HttpResponse<String> response = apiClient.delete("http://localhost:8080/users/me/accounts",
                AuthSession.getToken(), deleteAccountDTO);

        HttpStatusCode status = HttpStatusCode.from(response.statusCode());
        if(status == HttpStatusCode.NO_CONTENT){
            accountTabPaneController.updateTabs(deleteAccountDTO.title(), UpdateType.DELETED);
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        ApiErrorDTO error = mapper.readValue(response.body(), ApiErrorDTO.class);
        throw new AccountException(error.title(), error.detail());
    }
}
