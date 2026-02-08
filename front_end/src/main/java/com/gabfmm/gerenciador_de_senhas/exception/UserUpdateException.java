package com.gabfmm.gerenciador_de_senhas.exception;

public class UserUpdateException extends RuntimeException {
    String title;

    public UserUpdateException(String title, String message) {
        super(message);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
