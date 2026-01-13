package com.example.gerenciador_de_senhas_v2.util;

public enum HttpStatusCode {

    OK(200),
    CREATED(201);

    private final int code;

    HttpStatusCode(int code){
        this.code = code;
    }

    public int code() {
        return code;
    }

    public static HttpStatusCode from(int code) {
        for (HttpStatusCode status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
}
