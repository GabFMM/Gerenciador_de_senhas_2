package com.gabfmm.gerenciador_de_senhas.controller.menu;

import com.gabfmm.gerenciador_de_senhas.controller.base.NavigableController;
import com.gabfmm.gerenciador_de_senhas.dto.user.DeleteUserDTO;
import com.gabfmm.gerenciador_de_senhas.dto.user.UserUpdateDTO;
import com.gabfmm.gerenciador_de_senhas.dto.user.UserUpdateInfoDTO;
import com.gabfmm.gerenciador_de_senhas.exception.UserDeleteException;
import com.gabfmm.gerenciador_de_senhas.exception.UserNotFoundException;
import com.gabfmm.gerenciador_de_senhas.exception.UserUpdateException;
import com.gabfmm.gerenciador_de_senhas.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;

import java.io.IOException;

public class ConfigurationController extends NavigableController {

    // -- Attributes --

    private final UserService userService;

    @FXML
    private TextField usernameTextField;
    @FXML
    private Label currentPasswordLabel;
    @FXML
    private PasswordField currentPasswordField;
    @FXML
    private Label newPasswordLabel;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label saveChangesLabel;
    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;
    @FXML
    private StackPane saveChangesStackPane;
    @FXML
    private HBox saveChangesHBox;
    @FXML
    private Label passwordLabel;
    @FXML
    private Button changePasswordButton;
    @FXML
    private Button deleteUserButton;
    @FXML
    private Button confirmDeleteUserButton;
    @FXML
    private Label deletePasswordLabel;
    @FXML
    private PasswordField deletePasswordField;

    // -- Methods --

    private void showSaveChanges(){
        saveChangesStackPane.setManaged(true);
        saveChangesStackPane.setVisible(true);

        saveChangesHBox.setManaged(true);
        saveChangesHBox.setVisible(true);

        saveChangesLabel.setManaged(true);
        saveChangesLabel.setVisible(true);

        yesButton.setManaged(true);
        yesButton.setVisible(true);

        noButton.setManaged(true);
        noButton.setVisible(true);
    }

    private void hideSaveChanges(){
        saveChangesLabel.setVisible(false);
        saveChangesLabel.setManaged(false);

        yesButton.setVisible(false);
        yesButton.setManaged(false);

        noButton.setVisible(false);
        noButton.setManaged(false);

        saveChangesStackPane.setVisible(false);
        saveChangesStackPane.setManaged(false);

        saveChangesHBox.setVisible(false);
        saveChangesHBox.setManaged(false);
    }

    private void showChangePassword(){
        passwordLabel.setVisible(false);
        passwordLabel.setManaged(false);

        changePasswordButton.setVisible(false);
        changePasswordButton.setManaged(false);

        currentPasswordLabel.setManaged(true);
        currentPasswordLabel.setVisible(true);

        currentPasswordField.setManaged(true);
        currentPasswordField.setVisible(true);

        newPasswordLabel.setManaged(true);
        newPasswordLabel.setVisible(true);

        newPasswordField.setManaged(true);
        newPasswordField.setVisible(true);

        confirmPasswordLabel.setManaged(true);
        confirmPasswordLabel.setVisible(true);

        confirmPasswordField.setManaged(true);
        confirmPasswordField.setVisible(true);
    }

    private void hideChangePassword(){
        passwordLabel.setVisible(true);
        passwordLabel.setManaged(true);

        changePasswordButton.setVisible(true);
        changePasswordButton.setManaged(true);

        currentPasswordLabel.setManaged(false);
        currentPasswordLabel.setVisible(false);

        currentPasswordField.clear();
        currentPasswordField.setManaged(false);
        currentPasswordField.setVisible(false);

        newPasswordLabel.setManaged(false);
        newPasswordLabel.setVisible(false);

        newPasswordField.clear();
        newPasswordField.setManaged(false);
        newPasswordField.setVisible(false);

        confirmPasswordLabel.setManaged(false);
        confirmPasswordLabel.setVisible(false);

        confirmPasswordField.clear();
        confirmPasswordField.setManaged(false);
        confirmPasswordField.setVisible(false);

    }

    private void showConfirmDeleteUser(){
        deleteUserButton.setVisible(false);
        deleteUserButton.setManaged(false);

        confirmDeleteUserButton.setManaged(true);
        confirmDeleteUserButton.setVisible(true);

        deletePasswordLabel.setManaged(true);
        deletePasswordLabel.setVisible(true);

        deletePasswordField.setManaged(true);
        deletePasswordField.setVisible(true);
    }

    private void hideConfirmDeleteUser(){
        deleteUserButton.setManaged(true);
        deleteUserButton.setVisible(true);

        confirmDeleteUserButton.setVisible(false);
        confirmDeleteUserButton.setManaged(false);

        deletePasswordLabel.setVisible(false);
        deletePasswordLabel.setManaged(false);

        deletePasswordField.setVisible(false);
        deletePasswordField.setManaged(false);
    }

    private void configUsernameTextField(){
        // set text
        try {
            usernameTextField.setText(userService.getUsername());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            showError("Erro imprevisto", "Tente novamente");
        } catch (UserNotFoundException e){
            showError(e.getTitle(), e.getMessage());
        }

        // change listener
        usernameTextField.textProperty().addListener((observable, oldValue, newValue) -> showSaveChanges());
    }

    private void configChangePassword(){
        currentPasswordField.textProperty().addListener(((observableValue, s, t1) -> showSaveChanges()));
        newPasswordField.textProperty().addListener(((observableValue, s, t1) -> showSaveChanges()));
        confirmPasswordField.textProperty().addListener(((observableValue, s, t1) -> showSaveChanges()));
    }

    public ConfigurationController(){
        userService = new UserService();
    }

    @FXML
    public void initialize(){
        hideSaveChanges();
        hideChangePassword();
        hideConfirmDeleteUser();

        configUsernameTextField();
        configChangePassword();
    }

    public void onYesButtonClicked(ActionEvent e){
        UserUpdateDTO user = new UserUpdateDTO(
                usernameTextField.getText(),
                currentPasswordField.getText(),
                newPasswordField.getText(),
                confirmPasswordField.getText()
        );

        try{
            UserUpdateInfoDTO userUpdateInfoDTO = userService.saveUser(user);

            StringBuilder content = new StringBuilder();
            for(String info : userUpdateInfoDTO.infos())
                content.append(info).append("\n");

            showInfo("Resultado:", content.toString());

        } catch (IOException | InterruptedException ex) {
            showError("Erro inesperado", "Tente novamente");
            ex.printStackTrace();
        } catch (UserUpdateException ex){
            showError(ex.getTitle(), ex.getTitle());
        }

        hideChangePassword();
        hideSaveChanges();
    }

    public void onNoButtonClicked(ActionEvent e){
        // username
        try {
            usernameTextField.setText(userService.getUsername());
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            showError("Erro imprevisto", "Tente novamente");
        } catch (UserNotFoundException ex){
            showError(ex.getTitle(), ex.getMessage());
        }

        hideChangePassword();
        hideSaveChanges();
    }

    public void onChangePasswordButtonClicked(ActionEvent e){
        showChangePassword();
    }

    public void onDeleteUserButtonClicked(ActionEvent e){
        showConfirmDeleteUser();
    }

    public void onConfirmDeleteUserButtonClicked(ActionEvent e){
        try {
            userService.delete(new DeleteUserDTO(deletePasswordField.getText()));
        } catch (IOException | InterruptedException ex) {
            showError("Erro inesperado", "Tente novamente");
            ex.printStackTrace();
        } catch (UserDeleteException ex){
            showError(ex.getTitle(), ex.getTitle());
        }

        showInfo("Usuário excluído com sucesso", "Agora você será levado a janela de login");

        sceneManager.showLogin();
    }
}
