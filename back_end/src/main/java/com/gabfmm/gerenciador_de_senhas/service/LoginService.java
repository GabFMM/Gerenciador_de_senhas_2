package com.gabfmm.gerenciador_de_senhas.service;

import com.gabfmm.gerenciador_de_senhas.dto.UserDTO;
import com.gabfmm.gerenciador_de_senhas.exception.UserNotFoundException;
import com.gabfmm.gerenciador_de_senhas.repository.UserRepository;
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
