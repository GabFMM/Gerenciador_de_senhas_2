package com.gabfmm.gerenciador_de_senhas.service;

import com.gabfmm.gerenciador_de_senhas.auth.SessionContext;
import com.gabfmm.gerenciador_de_senhas.dto.user.*;
import com.gabfmm.gerenciador_de_senhas.exception.UserNotFoundException;
import com.gabfmm.gerenciador_de_senhas.exception.UsernameAlreadyExistsException;
import com.gabfmm.gerenciador_de_senhas.model.UserModel;
import com.gabfmm.gerenciador_de_senhas.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    // -- Attributes --

    private final SessionContext sessionContext;
    private final UserRepository userRepository;

    // -- Methods --

    private void verifyNewUser(NewUserDTO newUser){
        // name does not exist
        if(userRepository.existsByName(newUser.name()))
            throw new UsernameAlreadyExistsException("Nome de usuário já existe");
    }

    public UserService(SessionContext sessionContext,
                       UserRepository userRepository){
        this.sessionContext = sessionContext;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveNewUser(NewUserDTO newUser){
        verifyNewUser(newUser);

        UserModel user = new UserModel();
        user.setName(newUser.name());
        user.setPassword(newUser.password());

        // if it throws DataIntegrityViolationException
        // the ApiExceptionHandler will handle this
        userRepository.save(user);
    }

    @Transactional
    public UserUpdateInfoDTO saveUser(UserUpdateDTO userUpdateDTO) {
        ArrayList<String> infos = new ArrayList<>();

        if(!userUpdateDTO.name().isBlank()){
            userRepository.updateNameById(userUpdateDTO.name(), sessionContext.getUserId());
            infos.add("Nome de usuário atualizado");
        }
        else{
            infos.add("Nome de usuário não atualizado");
        }

        Optional<String> currentPassword = userRepository.findPasswordById(sessionContext.getUserId());
        if(
                currentPassword.isPresent() &&
                Objects.equals(userUpdateDTO.currentPasswordAttempt(), currentPassword.get()) &&
                userUpdateDTO.newPassword().length() >= 8 && userUpdateDTO.newPassword().length() <= 20 &&
                userUpdateDTO.newPassword().equals(userUpdateDTO.confirmPassword())
        ){
            userRepository.updatePasswordById(userUpdateDTO.newPassword(), sessionContext.getUserId());
            infos.add("Senha atualizada");
        }
        else{
            infos.add("Senha não atualizada");
        }

        return new UserUpdateInfoDTO(infos);
    }

    public UsernameDTO getUsername(){
        Optional<String> username = userRepository.findNameById(sessionContext.getUserId());

        if(username.isPresent())
            return new UsernameDTO(username.get());

        throw new UserNotFoundException("Usuário(a) não encontrado(a)");
    }

    @Transactional
    public void delete(DeleteUserDTO deleteUserDTO) {
        if(!userRepository.existsByIdAndPassword(sessionContext.getUserId(), deleteUserDTO.password()))
            throw new UserNotFoundException("Usuário(a) não encontrado(a)");

        userRepository.deleteById(sessionContext.getUserId());
    }
}
