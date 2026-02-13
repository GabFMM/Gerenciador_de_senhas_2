package com.gabfmm.gerenciador_de_senhas.controller.menu.account;

import com.gabfmm.gerenciador_de_senhas.controller.base.AccountSearchFormController;
import com.gabfmm.gerenciador_de_senhas.dto.account.AccountDTO;
import com.gabfmm.gerenciador_de_senhas.dto.account.AccountUpdateDTO;
import com.gabfmm.gerenciador_de_senhas.dto.account.AccountUpdateInfoDTO;
import com.gabfmm.gerenciador_de_senhas.exception.AccountException;
import com.gabfmm.gerenciador_de_senhas.util.UpdateType;
import javafx.event.ActionEvent;

import java.io.IOException;

public class EditAccountController extends AccountSearchFormController {

    public EditAccountController(){}

    @Override
    public void updateInfosEdited(String title){
        // do load because I don't know the previous title
        loadTitleComboBox();

        titleComboBox.getSelectionModel().select(title);
    }

    public void onSaveButtonClicked(ActionEvent event){
        if(titleComboBox.getValue() == null) return;

        try{
            AccountUpdateInfoDTO accountUpdateInfoDTO = accountService.saveAccount(
                    new AccountUpdateDTO(
                            titleComboBox.getValue(),
                            titleTextField.getText(),
                            descriptionTextArea.getText(),
                            passwordField.getText()
                    )
            );

            StringBuilder content = new StringBuilder();
            for(String info : accountUpdateInfoDTO.infos())
                content.append(info).append("\n");

            showInfo("Resultado:", content.toString());
        }
        catch (IOException | InterruptedException ex){
            ex.printStackTrace();
            showError("Erro imprevisto", "Não foi possível salvar informações da conta");
        }
        catch (AccountException ex){
            ex.printStackTrace();
            showError(ex.getTitle(), ex.getMessage());
        }
    }
}
