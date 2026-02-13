package com.gabfmm.gerenciador_de_senhas.dto.account;

import jakarta.validation.constraints.NotBlank;

public record AccountUpdateDTO(

        // identifier
        @NotBlank
        String originalTitle,

        String newTitle,
        String newDescription,
        String newPassword
) {
}
