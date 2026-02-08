package com.gabfmm.gerenciador_de_senhas.navigation;

import com.gabfmm.gerenciador_de_senhas.controller.NavigableController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneManager {

    // -- private --

    private final Stage stage;

    private void loadScene(final String fxmlPath){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load());

            // injeta o SceneManager no controller
            Object controller = loader.getController();
            if (controller instanceof NavigableController) {
                ((NavigableController) controller).setSceneManager(this);
            }

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadScene(final String fxmlPath, final String cssPath){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load());

            scene.getStylesheets().add(
                    Objects.requireNonNull(getClass().getResource(cssPath)).toExternalForm()
            );

            // injeta o SceneManager no controller
            Object controller = loader.getController();
            if (controller instanceof NavigableController) {
                ((NavigableController) controller).setSceneManager(this);
            }

            stage.setScene(scene);
            stage.show();

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    // -- public --

    public SceneManager(Stage stage){
        this.stage = stage;
    }

    public void showLogin(){
        loadScene(
                "/com/gabfmm/gerenciador_de_senhas/view/login.fxml",
                "/com/gabfmm/gerenciador_de_senhas/style/login.css"
        );
    }

    public void showCreateNewUser(){
        loadScene("/com/gabfmm/gerenciador_de_senhas/view/createNewUser.fxml");
    }

    public void showMainMenu(){ loadScene("/com/gabfmm/gerenciador_de_senhas/view/mainMenu/mainMenu.fxml");}
}
