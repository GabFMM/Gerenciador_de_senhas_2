package com.gabfmm.gerenciador_de_senhas.dto.account;

import jakarta.validation.constraints.NotBlank;

public record NewAccountDTO (
        @NotBlank
        String title,

        String description,

        @NotBlank
        String password
) {
}
