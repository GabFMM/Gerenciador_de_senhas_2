package com.example.gerenciador_de_senhas_v2.controller;

import com.example.gerenciador_de_senhas_v2.dto.UserDTO;
import com.example.gerenciador_de_senhas_v2.exception.UserNotFoundException;
import com.example.gerenciador_de_senhas_v2.navigation.SceneAware;
import com.example.gerenciador_de_senhas_v2.navigation.SceneManager;
import com.example.gerenciador_de_senhas_v2.service.LoginService;
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
