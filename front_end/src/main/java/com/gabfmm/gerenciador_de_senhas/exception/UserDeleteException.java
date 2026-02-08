package com.gabfmm.gerenciador_de_senhas.exception;

public class UserDeleteException extends RuntimeException {
    String title;

    public UserDeleteException(String title, String message) {
        super(message);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
