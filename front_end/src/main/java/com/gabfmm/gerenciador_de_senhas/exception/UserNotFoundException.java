package com.gabfmm.gerenciador_de_senhas.exception;

public class UserNotFoundException extends RuntimeException {

    String title;

    public UserNotFoundException(String title, String message) {
        super(message);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
