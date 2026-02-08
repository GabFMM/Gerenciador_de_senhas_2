package com.gabfmm.gerenciador_de_senhas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DeleteUserDTO(

        @NotBlank(message = "Senha não pode ser vazia")
        @Size(min = 8, max = 20, message = "Senha deve ter, no mínimo, 8 caracteres e, no máximo, 20 caracteres")
        String password
) {
}
