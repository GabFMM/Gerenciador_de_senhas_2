package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    // -- Attributes --

    private final UserRepository userRepository;

    // -- Methods --

    public LoginService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void verify(final UserDTO user){
        if(!userRepository.existsByNameAndPassword(user.name(), user.password()))
            throw new UserNotFoundException("Nome de usuário e/ou senha inválidos");
    }
}
