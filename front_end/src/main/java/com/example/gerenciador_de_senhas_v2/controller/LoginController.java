package com.example.gerenciador_de_senhas_v2.controller;

import com.example.gerenciador_de_senhas_v2.navigation.SceneAware;
import com.example.gerenciador_de_senhas_v2.navigation.SceneManager;
import javafx.event.ActionEvent;

public class LoginController implements SceneAware {

    SceneManager sceneManager;

    @Override
    public void setSceneManager(SceneManager sceneManager){
        this.sceneManager = sceneManager;
    }

    public void onButtonEntrarClicked(ActionEvent event){

    }

    public void onButtonCriarNovoUsuarioClicked(ActionEvent event){
        sceneManager.showCreateNewUser();
    }
}
