package com.gabfmm.gerenciador_de_senhas.controller;

import com.gabfmm.gerenciador_de_senhas.service.MainMenuService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class MainMenuController extends NavigableController {

    // -- Attributes --

    final private MainMenuService mainMenuService;

    @FXML
    private Label remainingTimeLabel;
    @FXML
    private StackPane stackPane;

    // -- Methods --

    private void updateStackPane(String fxmlPath, String cssPath){
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(fxmlPath)));

            Parent view = loader.load();

            Object controller = loader.getController();
            if (controller instanceof NavigableController) {
                ((NavigableController) controller).setSceneManager(sceneManager);
            }

            view.getStylesheets().add(Objects.requireNonNull(
                    getClass().getResource(cssPath)).toExternalForm());

            stackPane.getChildren().setAll(view);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private void configTimer(){

        Timeline timer = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    if(mainMenuService.updateRemainingTime())
                        remainingTimeLabel.setText("Tempo restante " + mainMenuService.getRemainingTime());
                    else
                        sceneManager.showLogin();
                })
        );

        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    public MainMenuController(){
        mainMenuService = new MainMenuService();
    }

    @FXML
    public void initialize(){
        configTimer();
    }

    public void onButtonSairClicked(ActionEvent event){
        sceneManager.showLogin();
    }

    public void onButtonGeradordeSenhasClicked(ActionEvent event){
        updateStackPane(
                "/com/gabfmm/gerenciador_de_senhas/view/mainMenu/passwordGenerator.fxml",
                "/com/gabfmm/gerenciador_de_senhas/style/mainMenu/passwordGenerator.css"
        );
    }

    public void onButtonConfiguracoesClicked(ActionEvent event){
        updateStackPane(
                "/com/gabfmm/gerenciador_de_senhas/view/mainMenu/configuration.fxml",
                "/com/gabfmm/gerenciador_de_senhas/style/mainMenu/configuration.css"
        );
    }
}
