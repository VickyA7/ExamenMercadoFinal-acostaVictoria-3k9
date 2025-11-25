package org.example.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.validation.ValidDnaSequence;

//Esta clase representa JSON que nos enviarán en la petición POST /mutant

@Data
public class DnaRequest {

    @NotNull (message = "El campo 'dna' debe completarse!")
    @Size (min = 4, message = "El array 'dna' debe tener al menos 4 filas!")

    @ValidDnaSequence //esta es la conexion con las validaciones personalizadas

    private String[] dna; // El array de cadenas que representa el ADN

}
