package com.gabfmm.gerenciador_de_senhas.controller;

import com.gabfmm.gerenciador_de_senhas.dto.PasswordGenerationRequestDTO;
import com.gabfmm.gerenciador_de_senhas.dto.PasswordGenerationResponseDTO;
import com.gabfmm.gerenciador_de_senhas.service.PasswordService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passwords")
public class PasswordController {

    private final PasswordService passwordService;

    public PasswordController(PasswordService passwordService){
        this.passwordService = passwordService;
    }

    @PostMapping
    public ResponseEntity<PasswordGenerationResponseDTO> generate(@Valid @RequestBody PasswordGenerationRequestDTO obj){
        return ResponseEntity.ok(passwordService.generate(obj));
    }
}
