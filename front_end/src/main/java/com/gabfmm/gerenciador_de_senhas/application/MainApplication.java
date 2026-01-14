package com.gabfmm.gerenciador_de_senhas.application;

import com.gabfmm.gerenciador_de_senhas.navigation.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage){
        stage.setTitle("Gerenciador de senhas");

        SceneManager sceneManager = new SceneManager(stage);
        sceneManager.showLogin();
    }
}
