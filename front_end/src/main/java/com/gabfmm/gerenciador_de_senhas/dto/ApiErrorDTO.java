package com.gabfmm.gerenciador_de_senhas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiErrorDTO(
        String title,
        String detail
)
{ }
