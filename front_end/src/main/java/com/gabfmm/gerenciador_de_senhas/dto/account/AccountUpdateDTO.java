package com.gabfmm.gerenciador_de_senhas.dto.account;

public record AccountUpdateDTO (
        // identifier
        String originalTitle,

        String newTitle,
        String newDescription,
        String newPassword
) {
}
