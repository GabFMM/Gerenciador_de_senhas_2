package com.gabfmm.gerenciador_de_senhas.service;

import com.gabfmm.gerenciador_de_senhas.dto.token.TokenDTO;
import com.gabfmm.gerenciador_de_senhas.dto.user.UserLoginDTO;
import com.gabfmm.gerenciador_de_senhas.exception.UserNotFoundException;
import com.gabfmm.gerenciador_de_senhas.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    // -- Attributes --

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    // -- Methods --

    public LoginService(PasswordEncoder passwordEncoder, JwtService jwtService, UserRepository userRepository){
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public TokenDTO verify(final UserLoginDTO user){
        String passwordHash = userRepository.findPasswordHashByNameHash(user.name())
                .orElseThrow(() ->
                        new UserNotFoundException("Nome de usuário e/ou senha inválidos"));

        if (!passwordEncoder.matches(user.password(), passwordHash)) {
            throw new UserNotFoundException("Nome de usuário e/ou senha inválidos");
        }

        Optional<Long> userId = userRepository.findIdByNameHash(user.name());
        if(userId.isEmpty())
            throw new UserNotFoundException("Nome de usuário e/ou senha inválidos");

        return new TokenDTO(jwtService.generateToken(Long.toString(userId.get())));
    }
}
