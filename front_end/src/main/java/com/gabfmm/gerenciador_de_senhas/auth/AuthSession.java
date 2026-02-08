package com.gabfmm.gerenciador_de_senhas.auth;

public class AuthSession {
    private static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        AuthSession.token = token;
    }
}
