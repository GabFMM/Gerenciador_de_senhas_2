package com.gabfmm.gerenciador_de_senhas.dto.account;

import jakarta.validation.constraints.NotBlank;

public record DeleteAccountDTO(
        @NotBlank
        String title
){
}
