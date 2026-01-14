package com.gabfmm.gerenciador_de_senhas.exception;

public class NewUserException extends RuntimeException {

    String title;

    public NewUserException(String title, String message) {
        super(message);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
