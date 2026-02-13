package com.gabfmm.gerenciador_de_senhas.controller.login;

import com.gabfmm.gerenciador_de_senhas.controller.base.NavigableController;
import com.gabfmm.gerenciador_de_senhas.dto.user.NewUserDTO;
import com.gabfmm.gerenciador_de_senhas.exception.NewUserException;
import com.gabfmm.gerenciador_de_senhas.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class RegisterController extends NavigableController {

    // -- Attributes --

    private final UserService userService;

    @FXML
    private Label usernameTooltip;
    @FXML
    private Label passwordTooltip;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    // -- Methods --

    private void applyMaxLenght(TextField textField, int size){
        textField.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().length() <= size) return change;
            return null;
        }));
    }

    public RegisterController(){
        userService = new UserService();
    }

    @FXML
    public void initialize(){
        applyMaxLenght(usernameTextField, 20);
        applyMaxLenght(passwordField, 20);
        applyMaxLenght(confirmPasswordField, 20);
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

        NewUserDTO userDTO = new NewUserDTO(usernameTextField.getText(), passwordField.getText());

        try {
            userService.saveNewUser(userDTO, confirmPasswordField.getText());
        }
        catch (NewUserException ex){
            showErrorAndWait(ex.getTitle(), ex.getMessage());
            return;
        }
        catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            showErrorAndWait("Erro imprevisto", "Tente novamente");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setContentText("Usuário criado com sucesso. \nAgora você voltará para a tela de login");
        alert.showAndWait();

        sceneManager.showLogin();
    }
}
