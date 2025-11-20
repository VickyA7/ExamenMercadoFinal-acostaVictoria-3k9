package org.example.service;

import org.springframework.stereotype.Component;

@Component
public class MutantDetector {
    private static final int SEQUENCE_LENGTH = 4; //cantidad de letras iguales consecutivas
    private static final int MIN_SEQUENCES_MUTANT = 2; //cantidad minima de secuencias para ser mutante

    public boolean isMutant(String[] dna){
        if (!isValidDna(dna)){
            throw new IllegalArgumentException("El ADN proporcionado no es válido.");
            //return false;
        }

        final int n = dna.length;
        int sequencesCount = 0;

        // Crear matriz bidimensional para representar el ADN

        char[][] matrix = new char[n][n];

        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray(); // Convertir cada cadena en un array de caracteres
        }

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                char currentChar = matrix[row][col];

                // Verificar horizontal
                if (col <= n - SEQUENCE_LENGTH) {
                    if (isSequence(matrix, row, col, 0, 1, currentChar)) {
                        sequencesCount++;
                    }
                }

                // Verificar vertical
                if (row <= n - SEQUENCE_LENGTH) {
                    if (isSequence(matrix, row, col, 1, 0, currentChar)) {
                        sequencesCount++;
                    }
                }

                // Verificar diagonal derecha
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (isSequence(matrix, row, col, 1, 1, currentChar)) {
                        sequencesCount++;
                    }
                }

                // Verificar diagonal izquierda
                if (row <= n - SEQUENCE_LENGTH && col >= SEQUENCE_LENGTH - 1) {
                    if (isSequence(matrix, row, col, 1, -1, currentChar)) {
                        sequencesCount++;
                    }
                }

                if (sequencesCount >= MIN_SEQUENCES_MUTANT) {
                    return true; // Es mutante
                }
            }
        }

    }
}
