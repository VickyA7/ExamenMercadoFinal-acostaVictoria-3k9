package org.example.dto;


//Clase para devolver mensajes de error consistentes y claros al fallar algo

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private LocalDate timestamp;
    private int status;
    private String error;
    private String message; // Descripción detallada del error
    private String path; // Ruta de la petición que causó el error
}
