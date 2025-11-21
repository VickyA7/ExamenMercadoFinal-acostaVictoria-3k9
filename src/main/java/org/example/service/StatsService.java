package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final DnaRecordRepository dnaRecordRepository;

    /**
     * Obtiene las estadísticas de todos los análisis de ADN realizados.
     */
    public StatsResponse getStats() {
        // Consultar la BD para contar mutantes y humanos
        long countMutant = dnaRecordRepository.countByIsMutant(true);
        long countHuman = dnaRecordRepository.countByIsMutant(false);

        // Calcular el ratio
        double ratio = calculateRatio(countMutant, countHuman);

        // Construir y retornar la respuesta
        return new StatsResponse(countMutant, countHuman, ratio);
    }

    /**
     * Calcula el ratio mutantes/humanos con manejo especial para división por cero.
     */
    private double calculateRatio(long countMutant, long countHuman) {
        if (countHuman == 0) {
            // Si no hay humanos, retornar el número de mutantes
            // (o 0 si tampoco hay mutantes)
            return countMutant > 0 ? countMutant : 0.0;
        }
        // División normal
        return (double) countMutant / countHuman;
    }
}