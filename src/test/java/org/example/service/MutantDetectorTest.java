package org.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

    private MutantDetector detector;

    @BeforeEach // Inicializa el detector antes de cada test
    void setUp() {

        detector = new MutantDetector();
    }

    // --- CASOS DE MUTANTES (deben devolver true) ---

    @Test
    @DisplayName("1º Debe detectar mutante con secuencias horizontal y vertical")
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
    @DisplayName("2º Debe detectar mutante con dos secuencias diagonales")
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

    @Test
    @DisplayName("3º Debe detectar mutante con secuencias horizontales")
    void testMultipleHorizontalSequences() {
        String[] dna = {
                "AAAAAA", // Secuencia horizontal de 'A'
                "CAGTGC",
                "TTATTT", // Secuencia horizontal de 'T'
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna), "Debería ser mutante, detecta secuencia horizontal");
    }

    @Test
    @DisplayName("4º Debe detectar mutante con secuencias verticales")
    void testMultipleVerticalSequences() {
        String[] dna = {
                "ATACGA",
                "ATATGC",
                "ATATTG", // Secuencia vertical de 'A' y de 'T' y de 'G'
                "ATACGG",
                "GCGTCG",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna), "Debería ser mutante, detecta secuencia vertical");
        }

        @Test
        @DisplayName("5º Debe detectar mutante, minimo en una matriz de 4x4")
        void testMinimun4x4Matrix() {
            String[] dna = {
                    "ATGC",
                    "AGCC",
                    "ATGC",
                    "ATGC" // Secuencia vertical de 'A' y de 'C'
            };
            assertTrue(detector.isMutant(dna), "Debería ser mutante en matriz mínima 4x4");
        }

        @Test
        @DisplayName("6º Debe detectar matriz con todas las letras iguales")
        void testAllSameLettersMatrix() {
            String[] dna = {
                    "AAAAAA",
                    "AAAAAA",
                    "AAAAAA",
                    "AAAAAA",
                    "AAAAAA",
                    "AAAAAA" // Múltiples secuencias de 'A'
            };
            assertTrue(detector.isMutant(dna), "Debería ser mutante con todas las letras iguales");
        }

        @Test
        @DisplayName("7º Debe detectar mutante en matriz grande 10x10")
        void testLarge10x10Matrix() {
            String[] dna = {
                    "ATGCGATCGA",
                    "CAGTGCTAGC",
                    "TTATTTTGTA",
                    "AGACGGAGAC",
                    "GCGTCAGCTA",
                    "TCACTGATCG",
                    "ACGCGATCGA",
                    "CCGTGCTAGC",
                    "TTATTTTGTA",
                    "AGACGGAGAC" // Varias secuencias dispersas
            };
            assertTrue(detector.isMutant(dna), "Debería ser mutante en matriz grande 10x10");
        }

    // --- CASOS DE HUMANOS (deben devolver false) ---

    @Test
    @DisplayName("8º NO debe detectar mutante si solo hay UNA secuencia")
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
    @DisplayName("9º NO debe detectar mutante si no hay secuencias")
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
    @DisplayName("10. Debe retornar false para ADN nulo")
    void shouldReturnFalse_ForNullDna() {
        assertFalse(detector.isMutant(null), "Debe retornar false si es nulo");
    }

    @Test
    @DisplayName("11. Debe retornar false para ADN vacío")
    void shouldReturnFalse_ForEmptyDna() {
        assertFalse(detector.isMutant(new String[0]), "Debe retornar false si está vacío");
    }

    @Test
    @DisplayName("12. Debe retornar false para matriz no cuadrada")
    void shouldReturnFalse_ForNonSquareDna() {
        String[] dna = {"ATGC", "CAGT", "TTAT"}; // Matriz 3x4
        assertFalse(detector.isMutant(dna), "Debe retornar false si no es cuadrado");
    }

    @Test
    @DisplayName("13. Debe retornar false para caracteres inválidos")
    void shouldReturnFalse_ForDnaWithInvalidChars() {
        String[] dna = {"ATGC", "CXZT", "TTAT", "AGAC"};
        assertFalse(detector.isMutant(dna), "Debe retornar false por caracteres no permitidos");
    }

    @Test
    @DisplayName("14. Debe retornar false para matriz muy pequeña 3x3")
    void shouldReturnFalse_ForTooSmallDna() {
        String[] dna = {"ATG", "CAG", "TTA"}; // Matriz 3x3
        assertFalse(detector.isMutant(dna), "Debe retornar false si es menor a 4x4");
    }

    @Test
    @DisplayName("15. Debe retornar false para fila nula")
    void shouldReturnFalse_ForNullRow() {
        String[] dna = {
                "ATGCGA",
                null,
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna), "Debe retornar false si una fila es nula");
    }

    @Test
    @DisplayName("16. Debe retornar false para fila con longitud incorrecta")
    void shouldReturnFalse_ForIncorrectRowLength() {
        String[] dna = {
                "ATGCGA",
                "CAGTG",  // 5 chars, debe ser 6
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna), "Debe retornar false si la fila no tiene la longitud correcta");
    }

    // ==================== OPTIMIZACIONES (1 test) ====================

    @Test
    @DisplayName("17. Optimización - Early Termination (rendimiento)")
    void testEarlyTermination() {
        String[] dna = {
                "AAAAGA",
                "AAAAGC",
                "TTATGT",
                "AGAAGG",
                "TACCTA",
                "TCACTG"
        };

        long startTime = System.nanoTime();
        boolean result = detector.isMutant(dna);
        long endTime = System.nanoTime();

        assertTrue(result, "Debería ser mutante");
        long executionTimeMs = (endTime - startTime) / 1_000_000;
        assertTrue(executionTimeMs < 10, "Debe ejecutarse en < 10ms con early termination, tardó: " + executionTimeMs + "ms");
    }
}