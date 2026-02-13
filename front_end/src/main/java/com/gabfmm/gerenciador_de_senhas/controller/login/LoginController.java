package com.gabfmm.gerenciador_de_senhas.controller.login;

import com.gabfmm.gerenciador_de_senhas.controller.base.NavigableController;
import com.gabfmm.gerenciador_de_senhas.dto.user.UserLoginDTO;
import com.gabfmm.gerenciador_de_senhas.exception.UserNotFoundException;
import com.gabfmm.gerenciador_de_senhas.service.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.Objects;

public class LoginController extends NavigableController {

    // -- Attributes --

    private final LoginService loginService;

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox showPasswordCheckBox;

    // -- Methods --

    private void configPasswordFields() {

        // It keeps the passwordFields synchronized
        passwordField.textProperty().bindBidirectional(passwordTextField.textProperty());

        // It associates the state of the checkbox
        // with the visibility of the password fields
        passwordTextField.visibleProperty().bind(showPasswordCheckBox.selectedProperty());
        passwordField.visibleProperty().bind(showPasswordCheckBox.selectedProperty().not());

        // It avoids empty spaces when hidden
        passwordTextField.managedProperty().bind(passwordTextField.visibleProperty());
        passwordField.managedProperty().bind(passwordField.visibleProperty());
    }

    private void configPasswordCheckBox() {

        ImageView blockedEye = new ImageView(new Image(Objects.requireNonNull(
                        getClass().getResource("/com/gabfmm/gerenciador_de_senhas/asset/login/blockedEye.png"))
                .toExternalForm()));

        blockedEye.setFitHeight(showPasswordCheckBox.getPrefHeight());
        blockedEye.setFitWidth(showPasswordCheckBox.getPrefHeight());
        blockedEye.setPreserveRatio(true);

        ImageView unblockedEye = new ImageView(new Image(Objects.requireNonNull(
                        getClass().getResource("/com/gabfmm/gerenciador_de_senhas/asset" + "/login/unblockedEye.png"))
                .toExternalForm()));

        unblockedEye.setFitHeight(showPasswordCheckBox.getPrefHeight());
        unblockedEye.setFitWidth(showPasswordCheckBox.getPrefWidth());
        unblockedEye.setPreserveRatio(true);

        showPasswordCheckBox.setText(null);
        showPasswordCheckBox.setGraphicTextGap(0);
        showPasswordCheckBox.setGraphic(blockedEye);
        showPasswordCheckBox.selectedProperty().addListener(
                (obs, old, selected) -> showPasswordCheckBox.setGraphic(selected ? unblockedEye : blockedEye));
    }

    public LoginController() {

        loginService = new LoginService();
    }

    @FXML
    public void initialize() {

        configPasswordFields();
        configPasswordCheckBox();
    }

    public void onButtonEntrarClicked(ActionEvent event) {

        UserLoginDTO user = new UserLoginDTO(usernameTextField.getText(), passwordTextField.getText());

        try {
            loginService.verifyUser(user);
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            showErrorAndWait("Erro imprevisto", "Tente novamente");
            return;
        } catch (UserNotFoundException ex) {
            showErrorAndWait(ex.getTitle(), ex.getMessage());
            return;
        }

        sceneManager.showMainMenu();
    }

    public void onButtonCriarNovoUsuarioClicked(ActionEvent event) {
        sceneManager.showCreateNewUser();
    }
}
