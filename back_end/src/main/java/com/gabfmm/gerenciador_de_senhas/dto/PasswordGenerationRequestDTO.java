package com.gabfmm.gerenciador_de_senhas.dto;

import com.gabfmm.gerenciador_de_senhas.annotation.AtLeastOneTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@AtLeastOneTrue
public record PasswordGenerationRequestDTO(

        @NotNull
        @Min(value = 1, message = "Tamanho da senha não pode ser menor que 1")
        @Max(value = 100, message = "Tamanho da senha não pode ser maior que 100")
        int tam,

        boolean alphabetUpperCase,
        boolean alphabetLowerCase,
        boolean numeric,
        boolean specialCharacters
) {
}
