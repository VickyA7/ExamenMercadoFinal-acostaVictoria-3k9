package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD}) // Esta anotación solo se puede usar en campos de una clase.
@Retention(RetentionPolicy.RUNTIME) // Validación en tiempo de ejecución.
@Constraint(validatedBy = ValidDnaSequenceValidator.class) // IMPORTANTE: conecta esta anotación con su lógica de validación.
public @interface ValidDnaSequence {

    // Mensaje de error por defecto si la validación falla.
    String message() default "La secuencia de ADN debe ser una matriz NxN (mínimo 4x4) con solo los caracteres A, T, C, G.";

    // Estos dos métodos son necesarios para las anotaciones de validación personalizadas.
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}