package org.example.controller;

import org.example.repository.DnaRecordRepository;
import org.example.service.MutantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // Anotación clave: Levanta TODA tu aplicación de Spring para el test.
@AutoConfigureMockMvc // Simula peticiones HTTP.
@Transactional // Cada test se ejecuta en su propia transacción y al final se deshace (rollback).
        // Esto asegura que la base de datos esté limpia antes de cada test.
class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc; // La herramienta para hacer las "llamadas falsas" a nuestros endpoints.

    @Autowired
    private DnaRecordRepository dnaRecordRepository; // Acceso directo a la BD para preparar y limpiar datos.

    @Autowired
    private MutantService mutantService; // Servicio para preparar datos de prueba.

    @BeforeEach
    void setUp() {
        // Limpiamos la base de datos antes de cada test para asegurar que no haya interferencia.
        dnaRecordRepository.deleteAll();
    }

    // --- TESTS PARA POST /mutant ---

    @Test
    @DisplayName("POST /mutant debe retornar 200 OK cuando el ADN es mutante")
    void checkMutant_ShouldReturnOk_WhenDnaIsMutant() throws Exception {
        String mutantDnaJson = """
                {
                  "dna": [
                    "ATGCGA",
                    "CAGTGC",
                    "TTATGT",
                    "AGAAGG",
                    "CCCCTA",
                    "TCACTG"
                  ]
                }
                """;

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mutantDnaJson))
                .andExpect(status().isOk()); // Verificamos que la respuesta sea un HTTP 200
    }

    @Test
    @DisplayName("POST /mutant debe retornar 403 Forbidden cuando el ADN es humano")
    void checkMutant_ShouldReturnForbidden_WhenDnaIsHuman() throws Exception {
        String humanDnaJson = """
                {
                  "dna": [
                    "ATGCGA",
                    "CAGTGC",
                    "TTATTT",
                    "AGACGG",
                    "GCGTCA",
                    "TCACTG"
                  ]
                }
                """;

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(humanDnaJson))
                .andExpect(status().isForbidden()); // Verificamos que la respuesta sea un HTTP 403
    }

    @Test
    @DisplayName("POST /mutant debe retornar 400 Bad Request cuando el ADN es inválido")
    void checkMutant_ShouldReturnBadRequest_WhenDnaIsInvalid() throws Exception {
        String invalidDnaJson = """
                {
                  "dna": [
                    "ATGC",
                    "CAGT",
                    "TTAT"
                  ]
                }
                """; // Matriz no cuadrada

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidDnaJson))
                .andExpect(status().isBadRequest()); // Verificamos que la respuesta sea un HTTP 400
    }

    // --- TESTS PARA GET /stats ---

    @Test
    @DisplayName("GET /stats debe retornar las estadísticas correctas")
    void getStatistics_ShouldReturnCorrectStats() throws Exception {
        // Arrange: Preparamos la base de datos con datos de prueba
        mutantService.analyzeDna(new String[]{"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"}); // 1 Mutante
        mutantService.analyzeDna(new String[]{"ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"}); // 1 Humano
        mutantService.analyzeDna(new String[]{"ATGCGA","CAGTGC","TTATGC","AGACGG","GCGTCA","TCACTG"}); // 2 Humanos

        // Act & Assert: Hacemos la llamada y verificamos el JSON de respuesta
        mockMvc.perform(get("/stats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(1)) // jsonPath nos permite inspeccionar el JSON de respuesta
                .andExpect(jsonPath("$.count_human_dna").value(2))
                .andExpect(jsonPath("$.ratio").value(0.5));
    }

    @Test
    @DisplayName("GET /stats debe retornar 0 cuando no hay datos")
    void getStatistics_ShouldReturnZeros_WhenNoData() throws Exception {
        mockMvc.perform(get("/stats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(0))
                .andExpect(jsonPath("$.count_human_dna").value(0))
                .andExpect(jsonPath("$.ratio").value(0.0));
    }
}