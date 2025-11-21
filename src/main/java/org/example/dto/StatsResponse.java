package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

//Esta clase representa JSON que la API devolverá en la petición GET /stats

@Data
@AllArgsConstructor
public class StatsResponse {
    //Anotación @JsonProperty le dice a Spring como nombrar a este campo en JSON final
    //Es una buena practica usar nombres en camelCase en Java y snake_case en JSON

    @JsonProperty("count_mutant_dna")
    private long countMutantDna;

    @JsonProperty("count_human_dna")
    private long countHumanDna;

    private double ratio; // Ratio: count_mutant_dna / count_human_dna
}
