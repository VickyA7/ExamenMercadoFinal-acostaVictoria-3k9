package org.example.exception;

// Creamos nuestra propia clase de excepción extendiendo RuntimeException.

// Esto nos permite ser más específicos sobre qué tipo de error ocurrió.
public class DnaHashCalculationException extends RuntimeException {

    public DnaHashCalculationException(String message, Throwable cause) {
        super(message, cause);
    }
}