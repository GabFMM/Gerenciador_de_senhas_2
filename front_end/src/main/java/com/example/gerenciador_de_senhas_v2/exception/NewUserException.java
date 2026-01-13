package com.example.gerenciador_de_senhas_v2.exception;

public class NewUserException extends RuntimeException {

    String title;

    public NewUserException(String message, String title) {
        super(message);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
