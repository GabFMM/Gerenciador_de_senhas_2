package com.example.gerenciador_de_senhas_v2.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    // -- private --

    private final Stage stage;

    private void loadScene(final String fxmlPath){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load());

            // injeta o SceneManager no controller
            Object controller = loader.getController();
            if (controller instanceof SceneAware) {
                ((SceneAware) controller).setSceneManager(this);
            }

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // -- public --

    public SceneManager(Stage stage){
        this.stage = stage;
    }

    public void showLogin(){
        loadScene("/com/example/gerenciador_de_senhas_v2/view/login.fxml");
    }

    public void showCreateNewUser(){
        loadScene("/com/example/gerenciador_de_senhas_v2/view/createNewUser.fxml");
    }

    public void showMainMenu(){ loadScene("/com/example/gerenciador_de_senhas_v2/view/mainMenu.fxml");}
}
