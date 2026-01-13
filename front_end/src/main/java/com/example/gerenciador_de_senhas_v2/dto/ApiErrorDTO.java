package com.example.gerenciador_de_senhas_v2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// this annotation have to be removed
// if another attribute from ProblemDetail class are going to be used
@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiErrorDTO(
        String title,
        String detail
)
{ }
