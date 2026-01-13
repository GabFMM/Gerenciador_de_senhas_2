package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Void> verify(@RequestBody @Valid UserDTO user){

        loginService.verify(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
