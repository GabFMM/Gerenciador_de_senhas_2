package com.gabfmm.gerenciador_de_senhas.exception;

public class AccountException extends RuntimeException {
    String title;

    public AccountException(String title, String message) {
        super(message);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
