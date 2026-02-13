package com.gabfmm.gerenciador_de_senhas.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(
        @NotBlank(message = "Nome de usuário não pode ser vazio")
        String name,

        @NotBlank(message = "Senha não pode ser vazia")
        String password
) {
}
