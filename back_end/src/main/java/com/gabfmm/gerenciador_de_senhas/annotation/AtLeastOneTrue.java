package com.gabfmm.gerenciador_de_senhas.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AtLeastOneTrueValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AtLeastOneTrue {

    String message() default "Pelo menos uma das opções deve ser verdadeira";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
