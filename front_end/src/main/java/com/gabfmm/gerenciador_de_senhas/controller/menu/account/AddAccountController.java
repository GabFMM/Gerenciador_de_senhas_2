package com.gabfmm.gerenciador_de_senhas.controller.menu.account;

import com.gabfmm.gerenciador_de_senhas.controller.base.AccountMinimalFormController;
import com.gabfmm.gerenciador_de_senhas.controller.base.BaseController;
import com.gabfmm.gerenciador_de_senhas.dto.account.AccountDTO;
import com.gabfmm.gerenciador_de_senhas.exception.AccountException;
import com.gabfmm.gerenciador_de_senhas.service.AccountService;
import com.gabfmm.gerenciador_de_senhas.util.UpdateType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddAccountController extends AccountMinimalFormController {

    // -- Attributes --
    @FXML
    private Button saveButton;

    // -- Methods --

    public AddAccountController(){ }

    @Override
    public void updateInfos(String title, UpdateType type){
        // it does nothing,
        // because I don't wanna update the infos for this controller
    }

    public void onSaveButtonClicked(ActionEvent actionEvent){
        try {
            accountService.saveNewAccount(new AccountDTO(
                    titleTextField.getText(),
                    descriptionTextArea.getText(),
                    passwordField.getText()
            ));

            showInfo("Sucesso", "Conta " + titleTextField.getText() + " salvada");
        }
        catch (IOException | InterruptedException ex){
            ex.printStackTrace();
            showError("Erro imprevisto", "Não foi possível salvar conta");
        }
        catch (AccountException ex){
            ex.printStackTrace();
            showError(ex.getTitle(), ex.getMessage());
        }
    }
}
