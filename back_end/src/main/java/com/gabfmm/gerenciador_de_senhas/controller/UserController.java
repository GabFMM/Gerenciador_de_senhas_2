package com.gabfmm.gerenciador_de_senhas.controller;

import com.gabfmm.gerenciador_de_senhas.dto.UserDTO;
import com.gabfmm.gerenciador_de_senhas.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid UserDTO newUser){

        // If it throws an exception, the ApiExceptionHandler will act
        userService.saveNewUser(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
