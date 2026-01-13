package com.example.gerenciador_de_senhas_v2.exception;

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
