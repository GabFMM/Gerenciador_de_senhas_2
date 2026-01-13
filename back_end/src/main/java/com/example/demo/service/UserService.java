package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.exception.UsernameAlreadyExistsException;
import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
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
