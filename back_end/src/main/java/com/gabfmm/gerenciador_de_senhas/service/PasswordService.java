package com.gabfmm.gerenciador_de_senhas.service;

import com.gabfmm.gerenciador_de_senhas.dto.PasswordGenerationRequestDTO;
import com.gabfmm.gerenciador_de_senhas.dto.PasswordGenerationResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PasswordService {

    public PasswordService(){

    }

    public PasswordGenerationResponseDTO generate(PasswordGenerationRequestDTO obj){
        String set = "";

        if(obj.alphabetLowerCase())
            set += "abcdefghijklmnopqrstuvwxyz";
        if(obj.alphabetUpperCase())
            set += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if(obj.numeric())
            set += "0123456789";
        if(obj.specialCharacters())
            set += "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";

        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for(int i = 0; i < obj.tam(); i++)
            password.append(set.charAt(random.nextInt(set.length())));

        return new PasswordGenerationResponseDTO(password.toString());
    }
}
