package com.gabfmm.gerenciador_de_senhas.controller.menu.account;

import com.gabfmm.gerenciador_de_senhas.controller.base.AccountMinimalFormController;
import com.gabfmm.gerenciador_de_senhas.dto.account.AccountDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AccountInfoController extends AccountMinimalFormController {

    // -- Attributes --
    private AccountTabPaneController accountTabPaneController;

    @FXML
    private Button editButton;
    @FXML
    private Button removeButton;

    // -- Methods --

    public AccountInfoController(){}

    public void setAccountTabPaneController(
            AccountTabPaneController accountTabPaneController) {
        this.accountTabPaneController = accountTabPaneController;
    }

    @FXML
    public void initialize(){}

    @Override
    public void updateInfosDeleted(String title){
        accountTabPaneController.removeTab(title);
    }

    public void loadAccountInfo(AccountDTO accountDTO){
        setOriginalAccountTitle(accountDTO.title());

        titleTextField.setText(accountDTO.title());
        descriptionTextArea.setText(accountDTO.description());
        passwordField.setText(accountDTO.password());
    }

    public void onEditButtonClicked(ActionEvent actionEvent){
        accountTabPaneController.selectEditTab(originalAccountTitle);
    }

    public void onRemoveButtonClicked(ActionEvent actionEvent){
        accountTabPaneController.selectDeleteTab(originalAccountTitle);
    }
}
