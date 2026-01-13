package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public LoginService(){

    }

    public boolean verify(final UserDTO user){
        return true;
    }
}
