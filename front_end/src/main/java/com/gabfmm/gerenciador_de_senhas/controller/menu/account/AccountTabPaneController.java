package com.gabfmm.gerenciador_de_senhas.controller.menu.account;

import com.gabfmm.gerenciador_de_senhas.controller.base.AccountMinimalFormController;
import com.gabfmm.gerenciador_de_senhas.controller.base.BaseController;
import com.gabfmm.gerenciador_de_senhas.controller.menu.MenuController;
import com.gabfmm.gerenciador_de_senhas.dto.account.AccountDTO;
import com.gabfmm.gerenciador_de_senhas.service.AccountService;
import com.gabfmm.gerenciador_de_senhas.util.UpdateType;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class AccountTabPaneController extends BaseController {

    // -- Attributes --

    private ArrayList<AccountMinimalFormController> accountMinimalFormControllers;
    private DisplayAccountController displayAccountController;
    private EditAccountController editAccountController;
    private DeleteAccountController deleteAccountController;

    private final String DISPLAY_TAB_NAME = "Visualizar contas";
    private final String ADD_TAB_NAME = "Adicionar conta";
    private final String EDIT_TAB_NAME = "Editar conta";
    private final String DELETE_TAB_NAME = "Remover conta";

    @FXML
    private TabPane tabPane;

    // -- Methods --

    // returns the controller of the new tab
    private Object addTab(final String fxmlPath,
                          final String tabName,
                          final boolean isClosable) throws IOException {
        Tab tab = new Tab(tabName);
        tab.setClosable(isClosable);

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(fxmlPath)
        );

        Parent parent = loader.load();

        tab.setContent(parent);

        tabPane.getTabs().add(tab);

        Object controller = loader.getController();
        if(controller instanceof AccountMinimalFormController)
            accountMinimalFormControllers.add((AccountMinimalFormController) controller);

        return controller;
    }

    public AccountTabPaneController(MenuController menuController){
        accountMinimalFormControllers = new ArrayList<>();

        AccountService.setAccountTabPaneController(this);

        tabPane = new TabPane();
        menuController.setRightPanel(tabPane);

        try {
            displayAccountController = (DisplayAccountController)
                    addTab(
                            "/com/gabfmm/gerenciador_de_senhas/view/menu/account/display.fxml",
                            DISPLAY_TAB_NAME,
                            false
                    );
            displayAccountController.setAccountTabPaneController(this);

            addTab(
                    "/com/gabfmm/gerenciador_de_senhas/view/menu/account/add.fxml",
                    ADD_TAB_NAME,
                    false
            );

            editAccountController = (EditAccountController)
            addTab(
                    "/com/gabfmm/gerenciador_de_senhas/view/menu/account/edit.fxml",
                    EDIT_TAB_NAME,
                    false
            );

            deleteAccountController = (DeleteAccountController)
            addTab(
                    "/com/gabfmm/gerenciador_de_senhas/view/menu/account/delete.fxml",
                    DELETE_TAB_NAME,
                    false
            );
        }
        catch(IOException ex){
            ex.printStackTrace();
            showError("Erro imprevisto", "Não foi possível criar o menu de contas");
        }
    }

    // if selected tab was switched, so returns true, else returns false
    public boolean selectTabIfExists(final String tabName){
        ObservableList<Tab> tabs = tabPane.getTabs();

        for(Tab tab : tabs) {
            if (Objects.equals(tab.getText(), tabName)){
                tabPane.getSelectionModel().select(tab);
                return true;
            }
        }

        return false;
    }

    public void createTabAccountInfo(AccountDTO account) throws IOException {

        // avoid duplicate tabs of the same account
        if(selectTabIfExists(account.title())) return;

        AccountInfoController accountInfoController =
                (AccountInfoController)
                addTab(
                    "/com/gabfmm/gerenciador_de_senhas/view/menu/account/info.fxml",
                    account.title(),
                        true
                );

        accountInfoController.setAccountTabPaneController(this);
        accountInfoController.loadAccountInfo(account);

        selectTabIfExists(account.title());
    }

    /*
    * Must be called when an account was modified in the DB
    *
    * "title" refers to the modified account
    * */
    public void updateTabs(String oldTitle, String newTitle, UpdateType type){
        displayAccountController.clear();

        for(AccountMinimalFormController tab : accountMinimalFormControllers)
            tab.updateInfos(oldTitle, newTitle, type);
    }

    public void selectEditTab(String accountTitle){
        editAccountController.showAccount(accountTitle);

        selectTabIfExists(EDIT_TAB_NAME);
    }

    public void selectDeleteTab(String accountTitle){
        deleteAccountController.showAccount(accountTitle);

        selectTabIfExists(DELETE_TAB_NAME);
    }

    public void renameTab(String oldName, String newName){
        tabPane.getTabs()
                .stream()
                .filter(tab -> tab.getText().equals(oldName))
                .findFirst()
                .ifPresent(tab -> tab.setText(newName));
    }

    public void removeTab(String tabName){
        ObservableList<Tab> tabs = tabPane.getTabs();

        accountMinimalFormControllers.removeIf
                (controller -> controller.getAccountTitle().equals(tabName));

        for(Tab tab : tabs) {
            if (Objects.equals(tab.getText(), tabName)){
                tabPane.getTabs().remove(tab);
                return;
            }
        }
    }
}
