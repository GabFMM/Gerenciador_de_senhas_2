package com.gabfmm.gerenciador_de_senhas.security;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

@Component
public class ApiSecurity {

    public ApiSecurity(){}

    public static byte[] generateSalt(){
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    public SecretKey deriveKey(
            char[] masterPassword,
            byte[] salt
    ) throws Exception {

        PBEKeySpec spec = new PBEKeySpec(
                masterPassword,
                salt,
                100_000,   // iterações
                256        // bits
        );

        SecretKeyFactory factory =
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        byte[] keyBytes = factory.generateSecret(spec).getEncoded();

        return new SecretKeySpec(keyBytes, "AES");
    }
}
