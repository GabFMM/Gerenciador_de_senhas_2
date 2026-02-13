package com.gabfmm.gerenciador_de_senhas.service;

import com.gabfmm.gerenciador_de_senhas.auth.SessionContext;
import com.gabfmm.gerenciador_de_senhas.dto.account.*;
import com.gabfmm.gerenciador_de_senhas.exception.AccountNotFoundException;
import com.gabfmm.gerenciador_de_senhas.exception.UserNotFoundException;
import com.gabfmm.gerenciador_de_senhas.model.AccountModel;
import com.gabfmm.gerenciador_de_senhas.model.UserModel;
import com.gabfmm.gerenciador_de_senhas.repository.AccountRepository;
import com.gabfmm.gerenciador_de_senhas.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final SessionContext sessionContext;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(SessionContext sessionContext, AccountRepository accountRepository, UserRepository userRepository){
        this.sessionContext = sessionContext;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public AccountDTO getAccount(String title) {
        Optional<AccountModel> accountModel = accountRepository.findByTitleAndUser_Id(title,
                sessionContext.getUserId());

        if(accountModel.isPresent())
            return new AccountDTO(
                    accountModel.get().getTitle(),
                    accountModel.get().getDescription(),
                    accountModel.get().getPassword()
            );

        throw new AccountNotFoundException("Não foi possível recuperar informações da conta: " + title);
    }

    public List<AccountDTO> getAccounts(String titleContains){
        return accountRepository
                .findByUserIdAndTitleContainingOrderByTitleAsc(sessionContext.getUserId(), titleContains)
                .stream()
                .map(
                        (accountModel) -> new AccountDTO(
                                accountModel.getTitle(),
                                accountModel.getDescription(),
                                accountModel.getPassword()
                        )
                )
                .toList();
    }

    public List<AccountDTO> getAccounts(){
        return accountRepository
                .findByUser_IdOrderByTitleAsc(sessionContext.getUserId())
                .stream()
                .map(
                        (accountModel) -> new AccountDTO(
                                accountModel.getTitle(),
                                accountModel.getDescription(),
                                accountModel.getPassword()
                        )
                )
                .toList();
    }

    @Transactional
    public void createAccount(NewAccountDTO newAccountDTO){
        Optional<UserModel> user = userRepository.findById(sessionContext.getUserId());
        if(user.isEmpty())
            throw new UserNotFoundException("Não foi possível criar conta: " + newAccountDTO.title());

        AccountModel accountModel = new AccountModel();
        accountModel.setTitle(newAccountDTO.title());
        accountModel.setDescription(newAccountDTO.description());
        accountModel.setPassword(newAccountDTO.password());
        accountModel.setUser(user.get());

        accountRepository.save(accountModel);
    }

    @Transactional
    public AccountUpdateInfoDTO saveAccount(AccountUpdateDTO accountUpdateDTO){
        ArrayList<String> infos = new ArrayList<>();

        Optional<AccountModel> accountModel =
                accountRepository.findByTitleAndUser_Id(accountUpdateDTO.originalTitle(), sessionContext.getUserId());

        if(accountModel.isEmpty())
            throw new AccountNotFoundException("Não foi possível atualizar conta: " + accountUpdateDTO.originalTitle());

        if(!accountUpdateDTO.newTitle().isBlank()) {
            if(accountRepository.existsByTitleAndUser_Id(accountUpdateDTO.newTitle(), sessionContext.getUserId())){
                infos.add("Título já existente");
            }
            else {
                accountModel.get().setTitle(accountUpdateDTO.newTitle());
                infos.add("Título da conta atualizado");
            }
        }
        else{
            infos.add("Título da conta não atualizado");
        }

        accountModel.get().setDescription(accountUpdateDTO.newDescription());
        infos.add("Descrição da conta atualizada");

        accountModel.get().setPassword(accountUpdateDTO.newPassword());
        infos.add("Senha da conta atualizada");

        accountRepository.save(accountModel.get());

        return new AccountUpdateInfoDTO(infos);
    }

    @Transactional
    public void deleteAccount(DeleteAccountDTO deleteAccountDTO){
        if(!accountRepository.existsByTitleAndUser_Id(deleteAccountDTO.title(), sessionContext.getUserId()))
            throw new AccountNotFoundException("Não foi possível remover conta: " + deleteAccountDTO.title());

        accountRepository.deleteByTitleAndUser_id(deleteAccountDTO.title(), sessionContext.getUserId());
    }
}
