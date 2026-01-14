package com.gabfmm.gerenciador_de_senhas.controller;

import com.gabfmm.gerenciador_de_senhas.dto.UserDTO;
import com.gabfmm.gerenciador_de_senhas.exception.UserNotFoundException;
import com.gabfmm.gerenciador_de_senhas.navigation.SceneAware;
import com.gabfmm.gerenciador_de_senhas.navigation.SceneManager;
import com.gabfmm.gerenciador_de_senhas.service.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController implements SceneAware {

    // -- Attributes --

    SceneManager sceneManager;
    final LoginService loginService;

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;

    // -- Methods --

    private void showError(String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public LoginController(){
        loginService = new LoginService();
    }

    @Override
    public void setSceneManager(SceneManager sceneManager){
        this.sceneManager = sceneManager;
    }

    public void onButtonEntrarClicked(ActionEvent event){

        UserDTO user = new UserDTO(usernameTextField.getText(), passwordTextField.getText());

        try {
            loginService.verifyUser(user);
        }
        catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            showError("Erro imprevisto", "Tente novamente");
            return;
        }
        catch (UserNotFoundException ex){
            showError(ex.getTitle(), ex.getMessage());
            return;
        }

        sceneManager.showMainMenu();
    }

    public void onButtonCriarNovoUsuarioClicked(ActionEvent event){
        sceneManager.showCreateNewUser();
    }
}
