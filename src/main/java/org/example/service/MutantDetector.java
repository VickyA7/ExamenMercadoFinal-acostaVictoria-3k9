package org.example.service;

import org.springframework.stereotype.Component;

@Component
public class MutantDetector {
    private static final int SEQUENCE_LENGTH = 4; //cantidad de letras iguales consecutivas
    private static final int MIN_SEQUENCES_MUTANT = 2; //cantidad minima de secuencias para ser mutante

    public boolean isMutant(String[] dna) {
        if (!isValidDna(dna)) {
            return false;
        }

        final int n = dna.length;
        int sequencesCount = 0;

        // Crear matriz bidimensional para representar el ADN

        char[][] matrix = new char[n][n];

        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray(); // Convertir cada cadena en un array de caracteres
        }

        // Iteración a través de la matriz para buscar secuencias

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                // Verificar horizontal
                if (col <= n - SEQUENCE_LENGTH) {
                    if (checkSequence(matrix, row, col, 0, 1)) {
                        sequencesCount++;
                    }
                }

                // Verificar vertical
                if (sequencesCount < MIN_SEQUENCES_MUTANT && row <= n - SEQUENCE_LENGTH) {
                    if (checkSequence(matrix, row, col, 1, 0)) {
                        sequencesCount++;
                    }
                }

                // Verificar diagonal derecha (DESCENDENTE)
                if (sequencesCount < MIN_SEQUENCES_MUTANT && row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (checkSequence(matrix, row, col, 1, 1)) {
                        sequencesCount++;
                    }
                }

                // Verificar diagonal izquierda (ASCENDENTE)
                if (sequencesCount < MIN_SEQUENCES_MUTANT && row <= n - SEQUENCE_LENGTH && col >= SEQUENCE_LENGTH - 1) {
                    if (checkSequence(matrix, row, col, 1, -1)) {
                        sequencesCount++;
                    }
                }

                //Utilizamos un early termination
                if (sequencesCount >= MIN_SEQUENCES_MUTANT) {
                    return true; // Es mutante
                }
            }
        }
        return false; // No es mutante
    }
        private boolean isValidDna(String[] dna) {
            if (dna == null || dna.length < SEQUENCE_LENGTH) { // Tamaño mínimo 4x4
                return false;
            }
            final int n = dna.length;
            for (String row : dna) {
                if (row == null || row.length() != n) {
                    return false;
                }
                if (!row.matches("[ATCG]+")) {
                    return false;
                }
            }
            return true;
        }

        private boolean checkSequence(char[][] matrix, int row, int col, int dRow, int dCol) {
            char baseChar = matrix[row][col];
            for (int i = 1; i < SEQUENCE_LENGTH; i++) {
                if (matrix[row + i * dRow][col + i * dCol] != baseChar) {
                    return false;
                }
            }
            return true;

    }
}
