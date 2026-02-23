package com.gabfmm.gerenciador_de_senhas.controller.menu.account;

import com.gabfmm.gerenciador_de_senhas.controller.base.AccountMinimalFormController;
import com.gabfmm.gerenciador_de_senhas.dto.account.AccountDTO;
import com.gabfmm.gerenciador_de_senhas.exception.AccountException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

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
    public void updateInfosEdited(String oldTitle, String newTitle){
        accountTabPaneController.renameTab(oldTitle, newTitle);

        try {
            AccountDTO account = accountService.getAccount(newTitle);

            originalAccountTitle = account.title();
            titleTextField.setText(account.title());
            descriptionTextArea.setText(account.description());
            passwordField.setText(account.password());

            showInfo("Conta atualizada",
                    "Devido a mudanças externas, as informações tiveram que ser " +
                            "atualizadas");
        }
        catch (IOException | InterruptedException ex){
            ex.printStackTrace();
            showError("Erro imprevisto", "Tente novamente");
        }
        catch (AccountException ex){
            ex.printStackTrace();
            showError(ex.getTitle(), ex.getMessage());
        }
    }

    @Override
    public void updateInfosDeleted(String oldTitle, String newTitle){

        accountTabPaneController.removeTab(oldTitle);
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
