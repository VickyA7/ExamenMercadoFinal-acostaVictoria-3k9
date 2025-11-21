package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor

@Entity
@Table(name = "dna_records", indexes = {
        // Creamos índices para que las búsquedas por hash y por tipo (mutante/humano) sean ultra rápidas
        @Index(name = "idx_dna_hash", columnList = "dnaHash", unique = true),
        @Index(name = "idx_is_mutant", columnList = "isMutant")
})
public class DnaRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Para buscar ADN mas rapido:
    @Column(nullable = false, unique = true, length = 64)
    private String dnaHash; // El hash SHA-256 del ADN, algoritmo convierte texto en codigo de 64 caracteres

    @Column(nullable = false)
    private boolean isMutant;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Antes de guardar un nuevo registro, JPA ejecutará este metodo
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Constructor personalizado que usaremos en el servicio
    public DnaRecord(String dnaHash, boolean isMutant) {
        this.dnaHash = dnaHash;
        this.isMutant = isMutant;
    }
}