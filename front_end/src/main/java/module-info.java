module com.example.gerenciador_de_senhas_v2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    exports com.example.gerenciador_de_senhas_v2.application;
    exports com.example.gerenciador_de_senhas_v2.dto;
    opens com.example.gerenciador_de_senhas_v2.controller to javafx.fxml;
}