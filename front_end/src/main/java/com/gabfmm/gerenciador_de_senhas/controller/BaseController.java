package com.gabfmm.gerenciador_de_senhas.controller;

import javafx.scene.control.Alert;

public abstract class BaseController {
    protected void showErrorAndWait(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    protected void showError(String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.show();
    }
}
