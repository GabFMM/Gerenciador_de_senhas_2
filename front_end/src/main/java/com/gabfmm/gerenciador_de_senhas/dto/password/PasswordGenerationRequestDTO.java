package com.gabfmm.gerenciador_de_senhas.dto.password;

public record PasswordGenerationRequestDTO
        (
                int tam,
                boolean alphabetUpperCase,
                boolean alphabetLowerCase,
                boolean numeric,
                boolean specialCharacters
        )
{
}
