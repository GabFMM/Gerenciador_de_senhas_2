package com.gabfmm.gerenciador_de_senhas.auth;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class TokenKeyProvider {
    private SecretKey tokenKey;

    public TokenKeyProvider(){}

    @PostConstruct
    public void init(){
        tokenKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public SecretKey getTokenKey() {
        return tokenKey;
    }
}
