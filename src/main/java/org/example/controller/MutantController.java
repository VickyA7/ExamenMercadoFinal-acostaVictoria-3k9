package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.DnaRequest;
import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import org.example.service.StatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController //combina @Controller y @ResponseBody . Prepara para recibir solict web
@RequestMapping("/") //Ruta base para endpoints del controlador. Con "/" puede ser /mutant o /stats
@RequiredArgsConstructor
public class MutantController {
    private final MutantService mutantService;
    private final StatsService statsService;

    /**
     * Endpoint para verificar si un ADN es mutante.
     * HTTP Method: POST
     * Path: /mutant
     *
     * @param dnaRequest El cuerpo de la petición JSON que contiene el ADN.
     * @return HTTP 200 OK si es mutante, HTTP 403 Forbidden si es humano.
     */

    @PostMapping("/mutant")
    public ResponseEntity<Void> checkMutant(@Valid @RequestBody DnaRequest dnaRequest){
        // 1. Llamar al servicio para analizar el ADN (trabajo pesado)
        boolean isMutant = mutantService.analyzeDna(dnaRequest.getDna());

        // 2. Retornar la respuesta HTTP adecuada
        if (isMutant){
            return ResponseEntity.ok().build(); //200 OK
        } else {
            return ResponseEntity.status(403).build(); //403 Forbidden
        }
    }

    /**
     * Endpoint para obtener las estadísticas de los análisis de ADN.
     * HTTP Method: GET
     * Path: /stats
     *
     * @return Un JSON con las estadísticas y un HTTP 200 OK.
     */

    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStatistics() {
        // 1. Llama al servicio para obtener las estadísticas.
        StatsResponse stats = statsService.getStats();

        // 2. Devuelve las estadísticas en el cuerpo de la respuesta con un 200 OK.
        return ResponseEntity.ok(stats);
    }
}
