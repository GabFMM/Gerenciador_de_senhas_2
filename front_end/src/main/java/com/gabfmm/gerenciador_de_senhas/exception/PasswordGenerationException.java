package com.gabfmm.gerenciador_de_senhas.exception;

public class PasswordGenerationException extends RuntimeException {
    String title;

    public PasswordGenerationException(String title, String message) {
        super(message);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
