package com.gabfmm.gerenciador_de_senhas.controller.base;

import com.gabfmm.gerenciador_de_senhas.dto.account.AccountDTO;
import com.gabfmm.gerenciador_de_senhas.exception.AccountException;
import com.gabfmm.gerenciador_de_senhas.service.AccountService;
import com.gabfmm.gerenciador_de_senhas.util.UpdateType;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public abstract class AccountMinimalFormController extends BaseController {

    protected final AccountService accountService;

    protected String originalAccountTitle;
    @FXML
    protected TextField titleTextField;
    @FXML
    protected TextArea descriptionTextArea;
    @FXML
    protected PasswordField passwordField;

    protected void clear(){
        titleTextField.clear();
        descriptionTextArea.clear();
        passwordField.clear();
    }

    protected void setOriginalAccountTitle(String title){
        originalAccountTitle = title;
    }

    public AccountMinimalFormController() {
        accountService = new AccountService();
        originalAccountTitle = "";
    }

    /*
        Must be called to update the infos in this controller through AccountTabPaneController

        For example, the user can delete an account through DeleteAccountController,
        so this controller must update himself, if the deleted infos account are the same
        of the attributes

        Parameters:
        "title" refers to the affected account
    */
    public void updateInfos(String title, UpdateType type){
        if(!title.equals(titleTextField.getText())) return;

        switch (type){
            case EDITED:
                updateInfosEdited(title);
                break;

            case DELETED:
                updateInfosDeleted(title);
                break;
        }
    }

    public void updateInfosEdited(String title){
        try {
            AccountDTO account = accountService.getAccount(title);

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

    public void updateInfosDeleted(String title){
        titleTextField.clear();
        descriptionTextArea.clear();
        passwordField.clear();

        showInfo("O conteúdo mostrado está desatualizado",
                "A conta " + title + " não existe mais. Por favor feche essa aba.");
    }

    public String getAccountTitle(){
        return titleTextField.getText();
    }
}
