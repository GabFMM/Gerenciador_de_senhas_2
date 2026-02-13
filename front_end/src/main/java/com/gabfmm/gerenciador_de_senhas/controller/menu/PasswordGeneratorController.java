package com.gabfmm.gerenciador_de_senhas.controller.menu;

import com.gabfmm.gerenciador_de_senhas.controller.base.BaseController;
import com.gabfmm.gerenciador_de_senhas.dto.password.PasswordGenerationRequestDTO;
import com.gabfmm.gerenciador_de_senhas.exception.PasswordGenerationException;
import com.gabfmm.gerenciador_de_senhas.service.PasswordGeneratorService;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class PasswordGeneratorController extends BaseController {

    // -- Attributes --

    private final PasswordGeneratorService passwordGeneratorService;

    private final PauseTransition debounce;

    @FXML
    private Spinner<Integer> spinner;
    @FXML
    private Button copyButton;
    @FXML
    private TextField passwordTextField;
    @FXML
    private CheckBox ABCCheckBox;
    @FXML
    private CheckBox abcCheckBox;
    @FXML
    private CheckBox numericCheckBox;
    @FXML
    private CheckBox specialCheckBox;

    // -- Methods --

    private void updatePasswordTextField(){
        try{
            passwordTextField.setText(passwordGeneratorService.generatePassword(new PasswordGenerationRequestDTO(
                    spinner.getValue(),
                    ABCCheckBox.isSelected(),
                    abcCheckBox.isSelected(),
                    numericCheckBox.isSelected(),
                    specialCheckBox.isSelected()
            )));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            showError("Erro imprevisto", "Tente novamente");
        } catch (PasswordGenerationException e){
            showError(e.getTitle(), e.getMessage());
        }
    }

    private void configCheckBoxes(){
        ABCCheckBox.selectedProperty().addListener((observableValue, oldValue, newValue) -> debounce.playFromStart());
        abcCheckBox.selectedProperty().addListener((observableValue, oldValue, newValue) -> debounce.playFromStart());
        numericCheckBox.selectedProperty().addListener((observableValue, oldValue, newValue) -> debounce.playFromStart());
        specialCheckBox.selectedProperty().addListener((observableValue, oldValue, newValue) -> debounce.playFromStart());
    }

    private void configSpinner(){
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);

        spinner.setValueFactory(valueFactory);
        spinner.setEditable(false);

        spinner.valueProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(abcCheckBox.isSelected() || ABCCheckBox.isSelected() || numericCheckBox.isSelected() || specialCheckBox.isSelected())
                debounce.playFromStart();
        }));
    }

    private void configCopyButton(){

        ImageView image =
                new ImageView(new Image(
                        Objects.requireNonNull(getClass().getResource("/com/gabfmm/gerenciador_de_senhas/asset" +
                                "/mainMenu/copyIcon.png")).toExternalForm()));

        image.setFitHeight(copyButton.getPrefHeight());
        image.setFitWidth(copyButton.getPrefWidth());
        image.setPreserveRatio(true);

        copyButton.setText(null);
        copyButton.setGraphicTextGap(0);
        copyButton.setGraphic(image);
    }

    public PasswordGeneratorController() {
        passwordGeneratorService = new PasswordGeneratorService();

        debounce = new PauseTransition(Duration.millis(400));
        debounce.setOnFinished(actionEvent -> updatePasswordTextField());
    }

    @FXML
    public void initialize(){
        configSpinner();
        configCopyButton();
        configCheckBoxes();
    }

    public void onCopyButtonClicked(){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent clipboardContent = new ClipboardContent();

        clipboardContent.putString(passwordTextField.getText());
        clipboard.setContent(clipboardContent);
    }
}
