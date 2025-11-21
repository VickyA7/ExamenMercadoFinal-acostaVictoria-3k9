package org.example.repository;

import org.example.entity.DnaRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Interfaz de acceso a datos
public interface DnaRecordRepository extends JpaRepository<DnaRecord, Long>  {
                                                //Extend JpaRepository para operaciones CRUD

    // Spring Data JPA creará automáticamente la consulta SQL para este metodo:
    // SELECT * FROM dna_records WHERE dna_hash = ?
    Optional<DnaRecord> findByDnaHash(String dnaHash);

    // Y para este también:
    // SELECT COUNT(*) FROM dna_records WHERE is_mutant = ?
    long countByIsMutant(boolean isMutant);
}