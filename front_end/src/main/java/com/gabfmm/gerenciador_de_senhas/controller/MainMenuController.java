package com.gabfmm.gerenciador_de_senhas.controller;

import com.gabfmm.gerenciador_de_senhas.navigation.SceneAware;
import com.gabfmm.gerenciador_de_senhas.navigation.SceneManager;
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

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class MainMenuController implements SceneAware {

    // -- Attributes --

    private SceneManager sceneManager;
    final private MainMenuService mainMenuService;

    @FXML
    private Label remainingTimeLabel;
    @FXML
    private StackPane stackPane;

    // -- Methods --

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

    @Override
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void onButtonSairClicked(ActionEvent event){
        sceneManager.showLogin();
    }

    public void onButtonGeradordeSenhasClicked(ActionEvent event){
        try {
            Parent view = FXMLLoader.load(
                    Objects.requireNonNull(getClass().getResource("/com/gabfmm/gerenciador_de_senhas/view/mainMenu" +
                            "/passwordGenerator.fxml")));

            view.getStylesheets().add(Objects.requireNonNull(
                    getClass().getResource("/com/gabfmm/gerenciador_de_senhas/style/mainMenu/passwordGenerator.css")).toExternalForm());

            stackPane.getChildren().addAll(view);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
