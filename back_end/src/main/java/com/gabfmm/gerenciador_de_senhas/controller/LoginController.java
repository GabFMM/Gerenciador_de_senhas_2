package com.gabfmm.gerenciador_de_senhas.controller;

import com.gabfmm.gerenciador_de_senhas.dto.token.TokenDTO;
import com.gabfmm.gerenciador_de_senhas.dto.user.UserLoginDTO;
import com.gabfmm.gerenciador_de_senhas.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService){
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<TokenDTO> verify(@RequestBody @Valid UserLoginDTO user){
        return ResponseEntity.ok(loginService.verify(user));
    }
}
