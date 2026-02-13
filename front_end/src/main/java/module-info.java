module com.gabfmm.gerenciador_de_senhas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    exports com.gabfmm.gerenciador_de_senhas.application;
    opens com.gabfmm.gerenciador_de_senhas.controller.menu.account to javafx.fxml;
    opens com.gabfmm.gerenciador_de_senhas.controller.menu to javafx.fxml;
    opens com.gabfmm.gerenciador_de_senhas.controller.login to javafx.fxml;
    opens com.gabfmm.gerenciador_de_senhas.controller.base to javafx.fxml;
    exports com.gabfmm.gerenciador_de_senhas.dto.user;
    exports com.gabfmm.gerenciador_de_senhas.dto.account;
    exports com.gabfmm.gerenciador_de_senhas.dto.password;
    exports com.gabfmm.gerenciador_de_senhas.dto.auth;
    exports com.gabfmm.gerenciador_de_senhas.dto.error;
}