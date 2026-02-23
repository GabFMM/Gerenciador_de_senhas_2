package com.gabfmm.gerenciador_de_senhas.service;

import com.gabfmm.gerenciador_de_senhas.dto.user.*;
import com.gabfmm.gerenciador_de_senhas.exception.UserNotFoundException;
import com.gabfmm.gerenciador_de_senhas.exception.UsernameAlreadyExistsException;
import com.gabfmm.gerenciador_de_senhas.model.UserModel;
import com.gabfmm.gerenciador_de_senhas.repository.UserRepository;
import com.gabfmm.gerenciador_de_senhas.util.SecurityUtils;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    // -- Attributes --

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // -- Methods --

    private void verifyNewUser(NewUserDTO newUser){
        // name does not exist
        if(userRepository.existsByNameHash(newUser.name()))
            throw new UsernameAlreadyExistsException("Nome de usuário já existe");
    }

    public UserService(PasswordEncoder passwordEncoder,
                       UserRepository userRepository){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveNewUser(NewUserDTO newUser){
        verifyNewUser(newUser);

        UserModel user = new UserModel();
        user.setNameEncrypted(newUser.name());
        user.setNameHash(newUser.name());
        user.setPasswordHash(passwordEncoder.encode(newUser.password()));

        userRepository.save(user);
    }

    @Transactional
    public UserUpdateInfoDTO saveUser(UserUpdateDTO userUpdateDTO) {
        ArrayList<String> infos = new ArrayList<>();

        if(!userUpdateDTO.name().isBlank()){
            userRepository.updateNameEncryptedById(userUpdateDTO.name(), SecurityUtils.getUserId());
            userRepository.updateNameHashById(userUpdateDTO.name(), SecurityUtils.getUserId());

            infos.add("Nome de usuário atualizado");
        }
        else{
            infos.add("Nome de usuário não atualizado");
        }

        Optional<String> currentPasswordEncoded = userRepository.findPasswordHashById(SecurityUtils.getUserId());
        if(
                currentPasswordEncoded.isPresent() &&
                passwordEncoder.matches(userUpdateDTO.currentPasswordAttempt(), currentPasswordEncoded.get()) &&
                userUpdateDTO.newPassword().length() >= 8 && userUpdateDTO.newPassword().length() <= 20 &&
                userUpdateDTO.newPassword().equals(userUpdateDTO.confirmPassword())
        ){
            userRepository.updatePasswordHashById(passwordEncoder.encode(userUpdateDTO.newPassword()), SecurityUtils.getUserId());
            infos.add("Senha atualizada");
        }
        else{
            infos.add("Senha não atualizada");
        }

        return new UserUpdateInfoDTO(infos);
    }

    public UsernameDTO getUsername(){
        Optional<String> username = userRepository.findNameEncryptedById(SecurityUtils.getUserId());

        if(username.isPresent())
            return new UsernameDTO(username.get());

        throw new UserNotFoundException("Usuário(a) não encontrado(a)");
    }

    @Transactional
    public void delete(DeleteUserDTO deleteUserDTO) {
        Optional<String> passwordEncoded = userRepository.findPasswordHashById(SecurityUtils.getUserId());
        if(passwordEncoded.isPresent() && passwordEncoder.matches(deleteUserDTO.password(), passwordEncoded.get()))
            userRepository.deleteById(SecurityUtils.getUserId());
        else
            throw new UserNotFoundException("Usuário(a) não encontrado(a)");

    }
}
