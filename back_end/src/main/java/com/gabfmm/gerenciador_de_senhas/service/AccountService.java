package com.gabfmm.gerenciador_de_senhas.service;

import com.gabfmm.gerenciador_de_senhas.dto.account.*;
import com.gabfmm.gerenciador_de_senhas.exception.AccountNotFoundException;
import com.gabfmm.gerenciador_de_senhas.exception.UserNotFoundException;
import com.gabfmm.gerenciador_de_senhas.model.AccountModel;
import com.gabfmm.gerenciador_de_senhas.model.UserModel;
import com.gabfmm.gerenciador_de_senhas.repository.AccountRepository;
import com.gabfmm.gerenciador_de_senhas.repository.UserRepository;
import com.gabfmm.gerenciador_de_senhas.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository){
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public AccountDTO getAccount(String title) {
        Optional<AccountModel> accountModel = accountRepository.findByTitleHashAndUser_Id(title, SecurityUtils.getUserId());

        if(accountModel.isPresent())
            return new AccountDTO(
                    accountModel.get().getTitle(),
                    accountModel.get().getDescription(),
                    accountModel.get().getPassword()
            );

        throw new AccountNotFoundException("Não foi possível recuperar informações da conta: " + title);
    }

    public List<AccountDTO> getAccounts(String titleContains) {
        return accountRepository.findByUser_Id(SecurityUtils.getUserId())
                .stream()
                // Filtra apenas os que contêm o título
                .filter(account -> account.getTitle().contains(titleContains))
                // Mapeia de Model para DTO
                .map(account -> new AccountDTO(
                        account.getTitle(),
                        account.getDescription(),
                        account.getPassword()
                ))
                .sorted(Comparator.comparing(AccountDTO::title))
                .toList();
    }

    public List<AccountDTO> getAccounts(){
        return accountRepository
                .findByUser_Id(SecurityUtils.getUserId())
                .stream()
                .map(
                        accountModel -> new AccountDTO(
                                accountModel.getTitle(),
                                accountModel.getDescription(),
                                accountModel.getPassword()
                        )
                )
                .sorted(Comparator.comparing(AccountDTO::title))
                .toList();
    }

    @Transactional
    public void createAccount(NewAccountDTO newAccountDTO){
        Optional<UserModel> user = userRepository.findById(SecurityUtils.getUserId());
        if(user.isEmpty())
            throw new UserNotFoundException("Não foi possível criar conta: " + newAccountDTO.title());

        AccountModel accountModel = new AccountModel();
        accountModel.setTitleEncrypted(newAccountDTO.title());
        accountModel.setTitleHash(newAccountDTO.title());
        accountModel.setDescriptionEncrypted(newAccountDTO.description());
        accountModel.setPasswordEncrypted(newAccountDTO.password());
        accountModel.setUser(user.get());

        accountRepository.save(accountModel);
    }

    @Transactional
    public AccountUpdateInfoDTO saveAccount(AccountUpdateDTO accountUpdateDTO){
        ArrayList<String> infos = new ArrayList<>();

        Optional<AccountModel> accountModel =
                accountRepository.findByTitleHashAndUser_Id(accountUpdateDTO.originalTitle(), SecurityUtils.getUserId());

        if(accountModel.isEmpty())
            throw new AccountNotFoundException("Não foi possível atualizar conta: " + accountUpdateDTO.originalTitle());

        if(!accountUpdateDTO.newTitle().isBlank()) {
            if(accountRepository.existsByTitleHashAndUser_Id(accountUpdateDTO.newTitle(), SecurityUtils.getUserId())){
                infos.add("Título já existente");
            }
            else {
                accountModel.get().setTitleEncrypted(accountUpdateDTO.newTitle());
                accountModel.get().setTitleHash(accountUpdateDTO.newTitle());
                infos.add("Título da conta atualizado");
            }
        }
        else{
            infos.add("Título da conta não atualizado");
        }

        accountModel.get().setDescriptionEncrypted(accountUpdateDTO.newDescription());
        infos.add("Descrição da conta atualizada");

        accountModel.get().setPasswordEncrypted(accountUpdateDTO.newPassword());
        infos.add("Senha da conta atualizada");

        accountRepository.save(accountModel.get());

        return new AccountUpdateInfoDTO(infos);
    }

    @Transactional
    public void deleteAccount(DeleteAccountDTO deleteAccountDTO){
        if(!accountRepository.existsByTitleHashAndUser_Id(deleteAccountDTO.title(), SecurityUtils.getUserId()))
            throw new AccountNotFoundException("Não foi possível remover conta: " + deleteAccountDTO.title());

        accountRepository.deleteByTitleHashAndUser_id(deleteAccountDTO.title(), SecurityUtils.getUserId());
    }
}
