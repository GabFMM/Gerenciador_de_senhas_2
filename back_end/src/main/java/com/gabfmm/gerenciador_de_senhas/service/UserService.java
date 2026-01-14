package com.gabfmm.gerenciador_de_senhas.service;

import com.gabfmm.gerenciador_de_senhas.dto.UserDTO;
import com.gabfmm.gerenciador_de_senhas.exception.UsernameAlreadyExistsException;
import com.gabfmm.gerenciador_de_senhas.model.UserModel;
import com.gabfmm.gerenciador_de_senhas.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // -- Attributes --

    private final UserRepository userRepository;

    // -- Methods --

    private void verifyNewUser(UserDTO newUser){
        // name does not exist
        if(userRepository.existsByName(newUser.name()))
            throw new UsernameAlreadyExistsException("Nome de usuário já existe");
    }

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveNewUser(UserDTO newUser){
        verifyNewUser(newUser);

        UserModel user = new UserModel();
        user.setName(newUser.name());
        user.setPassword(newUser.password());

        // if it throws DataIntegrityViolationException
        // the ApiExceptionHandler will handle this
        userRepository.save(user);
    }
}
