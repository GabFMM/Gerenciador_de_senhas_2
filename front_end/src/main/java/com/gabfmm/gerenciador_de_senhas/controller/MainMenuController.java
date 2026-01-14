package com.gabfmm.gerenciador_de_senhas.controller;

import com.gabfmm.gerenciador_de_senhas.navigation.SceneAware;
import com.gabfmm.gerenciador_de_senhas.navigation.SceneManager;
import com.gabfmm.gerenciador_de_senhas.service.MainMenuService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.awt.*;

public class MainMenuController implements SceneAware {

    // -- Attributes --

    private SceneManager sceneManager;
    final private MainMenuService mainMenuService;

    @FXML
    private Label remainingTimeLabel;

    // -- Methods --

    public MainMenuController(){
        mainMenuService = new MainMenuService();
    }

    @FXML
    public void initialize(){

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

    @Override
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void onButtonSairClicked(ActionEvent event){
        sceneManager.showLogin();
    }
}
