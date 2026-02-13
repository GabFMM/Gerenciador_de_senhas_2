package com.gabfmm.gerenciador_de_senhas.dto.user;

public record UserUpdateDTO(
        String name,
        String currentPasswordAttempt,
        String newPassword,
        String confirmPassword
) {
}
