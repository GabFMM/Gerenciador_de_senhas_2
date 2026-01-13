package com.example.gerenciador_de_senhas_v2.controller;

import com.example.gerenciador_de_senhas_v2.dto.UserDTO;
import com.example.gerenciador_de_senhas_v2.exception.NewUserException;
import com.example.gerenciador_de_senhas_v2.navigation.SceneAware;
import com.example.gerenciador_de_senhas_v2.navigation.SceneManager;
import com.example.gerenciador_de_senhas_v2.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class CreateNewUserController implements SceneAware {

    // -- Attributes --

    private SceneManager sceneManager;

    private final UserService userService;

    @FXML
    private Label usernameTooltip;
    @FXML
    private Label passwordTooltip;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField confirmPasswordTextField;

    // -- Methods --

    private void showError(String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    private void applyMaxLenght(TextField textField, int size){
        textField.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().length() <= size) return change;
            return null;
        }));
    }

    public CreateNewUserController(){
        userService = new UserService();
    }

    @FXML
    public void initialize(){
        applyMaxLenght(usernameTextField, 20);
        applyMaxLenght(passwordTextField, 20);
        applyMaxLenght(confirmPasswordTextField, 20);
    }

    @Override
    public void setSceneManager(SceneManager sceneManager){
        this.sceneManager = sceneManager;
    }

    public void onVoltarButtonClicked(ActionEvent event){
        sceneManager.showLogin();
    }

    public void onUsernameTooltipMouseEntered(MouseEvent event){
        usernameTooltip.setVisible(true);
    }

    public void onUsernameTooltipMouseExited(MouseEvent event){
        usernameTooltip.setVisible(false);
    }

    public void onPasswordTooltipMouseEntered(MouseEvent event){
        passwordTooltip.setVisible(true);
    }

    public void onPasswordTooltipMouseExited(MouseEvent event){
        passwordTooltip.setVisible(false);
    }

    public void onConfirmarButtonClicked(ActionEvent event){

        UserDTO userDTO = new UserDTO(usernameTextField.getText(), passwordTextField.getText());

        try {
            userService.saveNewUser(userDTO, confirmPasswordTextField.getText());
        }
        catch (NewUserException ex){
            showError(ex.getTitle(), ex.getMessage());
            return;
        }
        catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            showError("Erro imprevisto", "Tente novamente");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setContentText("Usuário criado com sucesso. Agora você voltará para a tela de login");
        alert.showAndWait();

        sceneManager.showLogin();
    }
}
