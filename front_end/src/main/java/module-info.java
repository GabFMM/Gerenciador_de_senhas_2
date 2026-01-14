module com.gabfmm.gerenciador_de_senhas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    exports com.gabfmm.gerenciador_de_senhas.application;
    exports com.gabfmm.gerenciador_de_senhas.dto;
    opens com.gabfmm.gerenciador_de_senhas.controller to javafx.fxml;
}