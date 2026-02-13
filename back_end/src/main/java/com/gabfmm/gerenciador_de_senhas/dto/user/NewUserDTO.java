package com.gabfmm.gerenciador_de_senhas.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewUserDTO(

        @NotBlank(message = "Nome de usuário não pode ser vazio")
        String name,

        @NotBlank(message = "Senha não pode ser vazia")
        @Size(min = 8, max = 20, message = "Senha deve ter, no mínimo, 8 caracteres e, no máximo, 20 caracteres")
        String password
) {}
