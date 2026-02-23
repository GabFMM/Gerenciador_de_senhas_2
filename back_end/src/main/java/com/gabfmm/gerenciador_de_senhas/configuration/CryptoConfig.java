package com.gabfmm.gerenciador_de_senhas.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CryptoConfig {

    private static String CRYPTO_KEY;

    @Value("${security.crypto-key}")
    public void setCryptoKey(String cryptoKey){
        CRYPTO_KEY = cryptoKey;
    }

    public static String getCryptoKey() {
        return CRYPTO_KEY;
    }
}
