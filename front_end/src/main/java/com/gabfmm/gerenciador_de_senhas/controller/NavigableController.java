package com.gabfmm.gerenciador_de_senhas.controller;

import com.gabfmm.gerenciador_de_senhas.navigation.SceneManager;

public abstract class NavigableController extends BaseController{
    protected SceneManager sceneManager;

    public NavigableController(){}

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
}
