package com.gabfmm.gerenciador_de_senhas.controller.menu.account;

import com.gabfmm.gerenciador_de_senhas.controller.base.BaseController;
import com.gabfmm.gerenciador_de_senhas.dto.account.AccountDTO;
import com.gabfmm.gerenciador_de_senhas.exception.AccountException;
import com.gabfmm.gerenciador_de_senhas.service.AccountService;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DisplayAccountController extends BaseController {

    // -- Attributes --

    private final AccountService accountService;
    private AccountTabPaneController accountTabPaneController;

    private PauseTransition debounce;
    @FXML
    private VBox displayAccountsVBox;
    @FXML
    private TextField searchTitleTextField;

    // -- Methods --

    private void configViewTitle(){
        searchTitleTextField.textProperty()
                .addListener((observableValue, s, t1) ->
                        debounce.playFromStart()
                );
    }

    private void configDebounce(){
        debounce = new PauseTransition(Duration.millis(400));
        debounce.setOnFinished((actionEvent) -> showAccounts());
    }

    private void showAccounts() {

        if(searchTitleTextField.getText().isEmpty()){
            displayAccountsVBox.getChildren().clear();
        }
        else {
            try {
                List<AccountDTO> accounts = accountService.getAccountsThatContains(searchTitleTextField.getText());

                displayAccountsVBox.getChildren().clear();

                if(!accounts.isEmpty()) {
                    for (AccountDTO account : accounts) {
                        HBox accountContainer = new HBox();
                        Label titleLabel = new Label(account.title());
                        Label descriptionLabel = new Label(account.description());
                        Button moreButton = new Button("+");

                        accountContainer.setAlignment(Pos.CENTER);
                        accountContainer.setStyle(
                                "-fx-background-color: white;" +
                                "-fx-border-width: 1.5px;" +
                                "-fx-border-color: black;"
                        );

                        HBox.setMargin(titleLabel, new Insets(10, 10, 10, 10));
                        titleLabel.setStyle("-fx-padding: 15px; -fx-border-width: 0 1.5px 0 0; -fx-border-color: black;");
                        titleLabel.setMaxWidth(75);
                        titleLabel.setEllipsisString("...");
                        titleLabel.setTextOverrun(OverrunStyle.ELLIPSIS);

                        HBox.setMargin(descriptionLabel, new Insets(10, 10, 10, 10));
                        descriptionLabel.setStyle("-fx-padding: 15px; -fx-border-width: 0 1.5px 0 0; -fx-border-color: black;");
                        descriptionLabel.setMaxWidth(75);
                        descriptionLabel.setEllipsisString("...");
                        descriptionLabel.setTextOverrun(OverrunStyle.ELLIPSIS);

                        HBox.setMargin(moreButton, new Insets(10, 10, 10, 10));
                        moreButton.setOnAction((actionEvent) -> showMoreInfoOfAccount(account));

                        accountContainer.setSpacing(10);
                        accountContainer.getChildren().addAll(titleLabel, descriptionLabel, moreButton);

                        displayAccountsVBox.getChildren().add(accountContainer);
                    }
                }
                else{
                    Label label = new Label("Nenhuma conta encontrada");
                    label.setStyle("-fx-text-fill: gray;");
                    displayAccountsVBox.getChildren().add(label);
                }
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
                showError("Erro imprevisto",
                        "Erro ao recuperar contas que contenham o título: " + searchTitleTextField.getText());
            } catch (AccountException ex) {
                ex.printStackTrace();
                showError(ex.getTitle(), ex.getMessage());
            }
        }
    }

    private void showMoreInfoOfAccount(AccountDTO account) {
        try {
            accountTabPaneController.createTabAccountInfo(account);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erro imprevisto", "Não foi possível recuperar informações da conta: " + account.title());
        }
    }

    public DisplayAccountController(){
        accountService = new AccountService();
    }

    @FXML
    public void initialize(){
        configDebounce();
        configViewTitle();
    }

    public void setAccountTabPaneController(AccountTabPaneController accountTabPaneController){
        this.accountTabPaneController = accountTabPaneController;
    }

    public void clear() {
        searchTitleTextField.clear();
        displayAccountsVBox.getChildren().clear();
    }
}
