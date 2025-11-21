package org.example.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {

    private static final int MIN_SIZE = 4;

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        // 1. Verificar si es nulo o vacío
        if (dna == null || dna.length == 0) {
            return false;
        }

        // 2. Verificar tamaño mínimo
        final int n = dna.length;
        if (n < MIN_SIZE) {
            return false;
        }

        // 3. Verificar si es una matriz cuadrada y si contiene caracteres válidos
        for (String row : dna) {
            // Cada fila debe tener la misma longitud que el número de filas
            if (row == null || row.length() != n) {
                return false;
            }
            // Usamos una expresión regular para asegurar que solo contenga A, T, C, G
            if (!row.matches("[ATCG]+")) {
                return false;
            }
        }

        // Si pasó todas las validaciones, es válido.
        return true;
    }
}