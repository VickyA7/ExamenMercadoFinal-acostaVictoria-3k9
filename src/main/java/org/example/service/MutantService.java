package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Optional;

@Service // Le dice a Spring que esta clase contiene lógica de negocio
@RequiredArgsConstructor // Lombok genera un constructor con todos los campos 'final'
@Slf4j // Lombok genera un logger automáticamente
public class MutantService {

    // Spring inyectará estas dependencias automáticamente
    private final MutantDetector mutantDetector;
    private final DnaRecordRepository dnaRecordRepository;

    /**
     * Analiza una secuencia de ADN para determinar si es mutante.
     * Si el ADN ya fue analizado, retorna el resultado cacheado.
     * Si no, lo analiza, guarda el resultado y lo retorna.
     */
    @Transactional // Asegura que todas las operaciones de BD se hagan en una sola transacción
    public boolean analyzeDna(String[] dna) {
        // 1. Calcular hash del ADN para identificarlo únicamente
        String dnaHash = calculateDnaHash(dna);

        // 2. Buscar en la BD si ya analizamos este ADN
        Optional<DnaRecord> existingRecord = dnaRecordRepository.findByDnaHash(dnaHash);

        if (existingRecord.isPresent()) {
            // 3a. Si existe, retornar el resultado cacheado (no re-analizar)
            log.info("DNA ya analizado. Hash: {}, Es mutante: {}",
                    dnaHash, existingRecord.get().isMutant());
            return existingRecord.get().isMutant();
        }

        // 3b. Si no existe, analizar con el detector
        boolean isMutant = mutantDetector.isMutant(dna);
        log.info("Nuevo análisis de DNA. Hash: {}, Es mutante: {}", dnaHash, isMutant);

        // 4. Guardar el resultado en la BD para futura referencia
        DnaRecord newRecord = new DnaRecord(dnaHash, isMutant);
        dnaRecordRepository.save(newRecord);

        return isMutant;
    }

    /**
     * Calcula un hash SHA-256 del ADN para identificarlo únicamente.
     * Esto nos permite verificar si ya analizamos este ADN sin guardar toda la secuencia.
     */
    private String calculateDnaHash(String[] dna) {
        try {
            // Concatenar todas las filas del ADN en un solo string
            String concatenatedDna = String.join("", dna);

            // Calcular el hash SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(concatenatedDna.getBytes());

            // Convertir bytes a string hexadecimal
            return HexFormat.of().formatHex(hashBytes);

        } catch (NoSuchAlgorithmException e) {
            // Esto nunca debería pasar porque SHA-256 es estándar
            throw new RuntimeException("Error calculando hash SHA-256", e);
        }
    }
}
