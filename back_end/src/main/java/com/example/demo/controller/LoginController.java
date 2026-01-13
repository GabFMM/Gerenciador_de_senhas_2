package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(){
        loginService = new LoginService();
    }

    @PostMapping
    public boolean verify(@RequestBody @Valid UserDTO user){
        return loginService.verify(user);
    }
}
