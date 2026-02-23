package com.gabfmm.gerenciador_de_senhas.controller.menu.account;

import com.gabfmm.gerenciador_de_senhas.controller.base.AccountSearchFormController;
import com.gabfmm.gerenciador_de_senhas.controller.base.BaseController;
import com.gabfmm.gerenciador_de_senhas.dto.account.AccountDTO;
import com.gabfmm.gerenciador_de_senhas.dto.account.DeleteAccountDTO;
import com.gabfmm.gerenciador_de_senhas.exception.AccountException;
import com.gabfmm.gerenciador_de_senhas.service.AccountService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeleteAccountController extends AccountSearchFormController {

    // -- Attributes --

    @FXML
    private Button removeButton;
    @FXML
    private Button confirmButton;

    // -- Methods --

    private void showRemoveButton(){
        confirmButton.setVisible(false);
        confirmButton.setManaged(false);

        removeButton.setManaged(true);
        removeButton.setVisible(true);
    }

    private void showConfirmButton(){
        removeButton.setVisible(false);
        removeButton.setManaged(false);

        confirmButton.setManaged(true);
        confirmButton.setVisible(true);
    }

    public DeleteAccountController() { }

    @FXML
    public void initialize(){
        super.initialize();
        showRemoveButton();
    }

    @Override
    public void updateInfosDeleted(String oldTitle, String newTitle){
        removeTitleComboBox(oldTitle);

        clear();
    }

    public void onRemoveButtonClicked(ActionEvent actionEvent){
        // Avoid prompt text
        if(titleComboBox.getValue() == null) return;

        showConfirmButton();
    }

    public void onConfirmButtonClicked(ActionEvent actionEvent){
        // Avoid prompt text
        if(titleComboBox.getValue() == null) return;

        try {
            accountService.delete(new DeleteAccountDTO(titleComboBox.getValue()));

            showInfo("Remoção de conta realizada", "");
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            showError("Erro imprevisto", "Não foi possível remover conta: " + titleComboBox.getValue());
        } catch (AccountException ex){
            ex.printStackTrace();
            showError(ex.getTitle(), ex.getMessage());
        }

        showRemoveButton();
    }
}
