package org.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.validation.ValidDnaSequence;

//Esta clase representa JSON que nos enviarán en la petición POST /mutant

@Data
public class DnaRequest {

    @NotNull (message = "El campo 'dna' debe completarse!")
    @ValidDnaSequence //esta es la conexion con las validaciones personalizadas
    private String[] dna; // El array de cadenas que representa el ADN

}
