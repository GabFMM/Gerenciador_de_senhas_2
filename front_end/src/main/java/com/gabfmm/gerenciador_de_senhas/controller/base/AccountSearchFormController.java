package com.gabfmm.gerenciador_de_senhas.controller.base;

import com.gabfmm.gerenciador_de_senhas.dto.account.AccountDTO;
import com.gabfmm.gerenciador_de_senhas.exception.AccountException;
import com.gabfmm.gerenciador_de_senhas.util.UpdateType;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public abstract class AccountSearchFormController extends AccountMinimalFormController {

    @FXML
    protected ComboBox<String> titleComboBox;

    @Override
    protected void clear(){
        titleComboBox.getSelectionModel().clearSelection();
        titleTextField.clear();
        descriptionTextArea.clear();
        passwordField.clear();
    }

    protected void loadTitleComboBox(){
        // avoid duplicate items
        titleComboBox.getItems().clear();

        try {
            List<AccountDTO> accounts = accountService.getAccounts();

            for(AccountDTO account : accounts)
                titleComboBox.getItems().add(account.title());
        }
        catch (IOException | InterruptedException ex){
            ex.printStackTrace();
            showError("Erro imprevisto", "Não foi possível recuperar contas do usuário");
        }
        catch (AccountException ex){
            ex.printStackTrace();
            showError(ex.getTitle(), ex.getMessage());
        }
    }

    protected void addTitleComboBox(String title){
        titleComboBox.getItems().add(title);

        // sort to the alphabetical order
        titleComboBox.getItems().sort(Comparator.comparing(String::toLowerCase));
    }

    protected void removeTitleComboBox(String title){
        titleComboBox.getItems().remove(title);
    }

    public AccountSearchFormController(){}

    @FXML
    public void initialize(){
        loadTitleComboBox();

        titleComboBox.valueProperty()
                .addListener((obs, oldValue, newValue) -> showAccount(newValue));
    }

    @Override
    public void updateInfos(String title, UpdateType type){
        switch (type){
            case CREATED:
                updateInfosCreated(title);
                break;

            case EDITED:
                updateInfosEdited(title);
                break;

            case DELETED:
                updateInfosDeleted(title);
                break;
        }
    }

    public void updateInfosCreated(String title){
        addTitleComboBox(title);
    }

    @Override
    public void updateInfosEdited(String title){
        try {
            // do load because I don't know the previous title
            loadTitleComboBox();

            if(!title.equals(titleTextField.getText())) return;

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

    @Override
    public void updateInfosDeleted(String title){
        removeTitleComboBox(title);

        if(!title.equals(titleTextField.getText())) return;

        clear();

        showInfo("O conteúdo mostrado estava desatualizado",
                "A conta " + title + " não existe mais");
    }

    public void showAccount(String title){
        // avoid prompt text
        if(title == null) return;

        // useful for the actions of the AccountInfoController buttons
        titleComboBox.getSelectionModel().select(title);

        try {
            AccountDTO accountDTO = accountService.getAccount(title);

            setOriginalAccountTitle(accountDTO.title());
            titleTextField.setText(accountDTO.title());
            descriptionTextArea.setText(accountDTO.description());
            passwordField.setText(accountDTO.password());
        }
        catch (IOException | InterruptedException ex){
            ex.printStackTrace();
            showError("Erro imprevisto", "Não foi possível recuperar informações da conta");
        }
        catch (AccountException ex){
            ex.printStackTrace();
            showError(ex.getTitle(), ex.getMessage());
        }
    }
}
