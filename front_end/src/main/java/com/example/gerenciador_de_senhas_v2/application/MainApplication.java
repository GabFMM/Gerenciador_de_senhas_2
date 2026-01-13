package com.example.gerenciador_de_senhas_v2.application;

import com.example.gerenciador_de_senhas_v2.navigation.SceneManager;
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
