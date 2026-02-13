package com.gabfmm.gerenciador_de_senhas.controller;

import com.gabfmm.gerenciador_de_senhas.dto.account.*;
import com.gabfmm.gerenciador_de_senhas.dto.user.*;
import com.gabfmm.gerenciador_de_senhas.service.AccountService;
import com.gabfmm.gerenciador_de_senhas.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AccountService accountService;

    public UserController(UserService userService, AccountService accountService){

        this.userService = userService;
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid NewUserDTO newUser){

        // If it throws an exception, the ApiExceptionHandler will act
        userService.saveNewUser(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/me")
    public ResponseEntity<UserUpdateInfoDTO> save(@RequestBody UserUpdateDTO userUpdateDTO){
        return ResponseEntity.ok(userService.saveUser(userUpdateDTO));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> delete(@RequestBody @Valid DeleteUserDTO deleteUserDTO){
        userService.delete(deleteUserDTO);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/me/username")
    public ResponseEntity<UsernameDTO> getUsername(){
        return ResponseEntity.ok(userService.getUsername());
    }

    @GetMapping("/me/accounts")
    public ResponseEntity<List<AccountDTO>> getAccounts(){
        return ResponseEntity.ok(accountService.getAccounts());
    }

    @GetMapping(value = "/me/accounts", params = "title")
    public ResponseEntity<AccountDTO> getAccount(@RequestParam(name = "title", required = false) String title){
        return ResponseEntity.ok(accountService.getAccount(title));
    }

    @GetMapping(value = "/me/accounts", params = "title-contains")
    public ResponseEntity<List<AccountDTO>> getAccounts(@RequestParam(name = "title-contains", required = false) String titleContains){
        return ResponseEntity.ok(accountService.getAccounts(titleContains));
    }

    @PostMapping("/me/accounts")
    public ResponseEntity<Void> create(@RequestBody @Valid NewAccountDTO newAccountDTO){
        accountService.createAccount(newAccountDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/me/accounts")
    public ResponseEntity<AccountUpdateInfoDTO> save(@RequestBody @Valid AccountUpdateDTO accountUpdateDTO){
        return ResponseEntity.ok(accountService.saveAccount(accountUpdateDTO));
    }

    @DeleteMapping("/me/accounts")
    public ResponseEntity<Void> delete(@RequestBody @Valid DeleteAccountDTO deleteAccountDTO){
        accountService.deleteAccount(deleteAccountDTO);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}