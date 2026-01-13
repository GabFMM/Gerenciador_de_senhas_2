package com.example.gerenciador_de_senhas_v2.util;

public enum HttpStatusCode {

    CREATED(201),
    BAD_REQUEST(400),
    CONFLICT(409);

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
