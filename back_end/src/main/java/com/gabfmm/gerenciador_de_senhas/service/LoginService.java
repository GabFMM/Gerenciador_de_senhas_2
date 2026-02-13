package com.gabfmm.gerenciador_de_senhas.service;

import com.gabfmm.gerenciador_de_senhas.dto.token.TokenDTO;
import com.gabfmm.gerenciador_de_senhas.dto.user.UserLoginDTO;
import com.gabfmm.gerenciador_de_senhas.exception.UserNotFoundException;
import com.gabfmm.gerenciador_de_senhas.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    // -- Attributes --

    private final JwtService jwtService;
    private final UserRepository userRepository;

    // -- Methods --

    public LoginService(JwtService jwtService, UserRepository userRepository){
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public TokenDTO verify(final UserLoginDTO user){

        if(!userRepository.existsByNameAndPassword(user.name(), user.password()))
            throw new UserNotFoundException("Nome de usuário e/ou senha inválidos");

        Optional<Long> userId = userRepository.findIdByName(user.name());
        if(userId.isPresent())
            return new TokenDTO(jwtService.generateToken(Long.toString(userId.get())));

        throw new UserNotFoundException("Nome de usuário e/ou senha inválidos");
    }
}
