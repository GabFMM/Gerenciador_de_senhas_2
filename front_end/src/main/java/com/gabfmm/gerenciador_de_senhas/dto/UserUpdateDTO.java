package com.gabfmm.gerenciador_de_senhas.dto;

public record UserUpdateDTO(
        String name,
        String currentPasswordAttempt,
        String newPassword,
        String confirmPassword
){
}
