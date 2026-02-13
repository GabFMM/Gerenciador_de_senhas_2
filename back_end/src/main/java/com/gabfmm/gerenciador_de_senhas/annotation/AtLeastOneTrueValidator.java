package com.gabfmm.gerenciador_de_senhas.annotation;

import com.gabfmm.gerenciador_de_senhas.dto.password.PasswordGenerationRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AtLeastOneTrueValidator implements ConstraintValidator<AtLeastOneTrue, PasswordGenerationRequestDTO> {

    @Override
    public boolean isValid(PasswordGenerationRequestDTO object, ConstraintValidatorContext constraintValidatorContext) {
        if(object == null)
            return true;

        return object.alphabetLowerCase() ||
                object.alphabetUpperCase() ||
                object.numeric() ||
                object.specialCharacters();
    }
}
