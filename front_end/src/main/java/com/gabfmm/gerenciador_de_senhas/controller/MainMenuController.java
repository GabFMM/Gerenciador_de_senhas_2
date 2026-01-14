package com.gabfmm.gerenciador_de_senhas.controller;

import com.gabfmm.gerenciador_de_senhas.navigation.SceneAware;
import com.gabfmm.gerenciador_de_senhas.navigation.SceneManager;
import javafx.event.ActionEvent;

public class MainMenuController implements SceneAware {

    // -- Attributes --

    private SceneManager sceneManager;

    // -- Methods --

    public MainMenuController(){}

    @Override
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void onButtonSairClicked(ActionEvent event){
        sceneManager.showLogin();
    }
}
