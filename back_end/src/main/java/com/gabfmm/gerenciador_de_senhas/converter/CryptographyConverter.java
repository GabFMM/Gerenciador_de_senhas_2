package com.gabfmm.gerenciador_de_senhas.converter;

import com.gabfmm.gerenciador_de_senhas.configuration.CryptoConfig;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Component;

@Converter
public class CryptographyConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) return null;

        // 1. Gera um salt aleatorio para cada salvamento
        String dynamicSalt = KeyGenerators.string().generateKey(); // Gera hex aleatorio
        TextEncryptor encryptor = Encryptors.text(CryptoConfig.getCryptoKey(), dynamicSalt);

        // 2. Retorna "SALT:DADO_CRIPTOGRAFADO"
        return dynamicSalt + ":" + encryptor.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;

        // 3. Separa o salt do dado para descriptografar
        String[] parts = dbData.split(":");
        String salt = parts[0];
        String encryptedText = parts[1];

        TextEncryptor encryptor = Encryptors.text(CryptoConfig.getCryptoKey(), salt);
        return encryptor.decrypt(encryptedText);
    }
}
