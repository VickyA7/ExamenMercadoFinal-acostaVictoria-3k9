package org.example.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

    private final MutantDetector detector = new MutantDetector();

    // --- CASOS DE MUTANTES (deben devolver true) ---

    @Test
    @DisplayName("Debe detectar mutante con secuencias horizontal y vertical")
    void shouldReturnTrue_WhenDnaHasHorizontalAndVerticalSequences() {
        String[] dnaConVertical = {
                "GTCGAA",
                "GTCGTC",
                "GTACTG",
                "GTAGGG", // Secuencia vertical de 'G' en la primera columna
                "CCCCTA", // Secuencia horizontal de 'C'
                "TCACTG"
        };
        assertTrue(detector.isMutant(dnaConVertical), "Debería ser mutante");
    }

    @Test
    @DisplayName("Debe detectar mutante con dos secuencias diagonales")
    void shouldReturnTrue_WhenDnaHasTwoDiagonalSequences() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG", // Diagonal descendente A-A-A-A
                "CGCCTA",
                "TCACTG"  // Diagonal ascendente C-C-C-C
        };
        assertTrue(detector.isMutant(dna), "Debería ser mutante");
    }

    // --- CASOS DE HUMANOS (deben devolver false) ---

    @Test
    @DisplayName("NO debe detectar mutante si solo hay UNA secuencia")
    void shouldReturnFalse_WhenDnaHasOnlyOneSequence() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna), "No debería ser mutante con una sola secuencia");
    }

    @Test
    @DisplayName("NO debe detectar mutante si no hay secuencias")
    void shouldReturnFalse_WhenDnaHasNoSequences() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGC",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna), "No debería ser mutante si no hay secuencias");
    }

    // --- CASOS DE VALIDACIÓN DE ADN (AHORA VERIFICAN LA EXCEPCIÓN) ---

    @Test
    @DisplayName("Debe lanzar excepción para un ADN no cuadrado")
    void shouldThrowException_ForNonSquareDna() {
        String[] dna = {"ATGC", "CAGT", "TTAT"}; // Matriz 3x4
        assertThrows(IllegalArgumentException.class, () -> {
            detector.isMutant(dna);
        }, "Debe lanzar excepción si no es cuadrado");
    }

    @Test
    @DisplayName("Debe lanzar excepción para un ADN con caracteres inválidos")
    void shouldThrowException_ForDnaWithInvalidChars() {
        String[] dna = {"ATGC", "CXZT", "TTAT", "AGAC"};
        assertThrows(IllegalArgumentException.class, () -> {
            detector.isMutant(dna);
        }, "Debe lanzar excepción por caracteres no permitidos");
    }

    @Test
    @DisplayName("Debe lanzar excepción para un ADN nulo o vacío")
    void shouldThrowException_ForNullOrEmptyDna() {
        assertThrows(IllegalArgumentException.class, () -> {
            detector.isMutant(null);
        }, "Debe lanzar excepción si es nulo");

        assertThrows(IllegalArgumentException.class, () -> {
            detector.isMutant(new String[0]);
        }, "Debe lanzar excepción si está vacío");
    }

    @Test
    @DisplayName("Debe lanzar excepción para un ADN demasiado pequeño (menor a 4x4)")
    void shouldThrowException_ForTooSmallDna() {
        String[] dna = {"ATG", "CAG", "TTA"}; // Matriz 3x3
        assertThrows(IllegalArgumentException.class, () -> {
            detector.isMutant(dna);
        }, "Debe lanzar excepción si es menor a 4x4");
    }
}