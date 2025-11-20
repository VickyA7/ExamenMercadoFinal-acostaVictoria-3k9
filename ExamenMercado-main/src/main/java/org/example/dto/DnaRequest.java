package org.example.dto;

import lombok.Data;

//Esta clase representa JSON que nos enviarán en la petición POST /mutant

@Data
public class DnaRequest {

    private String[] dna; // El array de cadenas que representa el ADN

}
