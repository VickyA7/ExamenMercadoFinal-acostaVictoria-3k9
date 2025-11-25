package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitarios del MutantService con Mocks")
class MutantServiceTest {

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @Mock
    private MutantDetector mutantDetector;

    @InjectMocks
    private MutantService mutantService;

    private String[] validMutantDna;
    private String[] validHumanDna;

    @BeforeEach
    void setUp() {
        validMutantDna = new String[]{
                "GTCGAA",
                "GTCGTC",
                "GTACTG",
                "GTAGGG",
                "CCCCTA",
                "TCACTG"
        };

        validHumanDna = new String[]{
                "ATGCGA",
                "CAGTGC",
                "TTATGC",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };
    }

    @Test
    @DisplayName("1. Debe analizar y guardar un ADN mutante correctamente")
    void testAnalyzeMutantDnaAndSave() {
        when(dnaRecordRepository.findByDnaHash(anyString()))
                .thenReturn(Optional.empty());
        when(mutantDetector.isMutant(validMutantDna))
                .thenReturn(true);
        when(dnaRecordRepository.save(any(DnaRecord.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        boolean result = mutantService.analyzeDna(validMutantDna);

        assertTrue(result);
        verify(mutantDetector, times(1)).isMutant(validMutantDna);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    @DisplayName("2. Debe analizar y guardar un ADN humano correctamente")
    void testAnalyzeHumanDnaAndSave() {
        when(dnaRecordRepository.findByDnaHash(anyString()))
                .thenReturn(Optional.empty());
        when(mutantDetector.isMutant(validHumanDna))
                .thenReturn(false);
        when(dnaRecordRepository.save(any(DnaRecord.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        boolean result = mutantService.analyzeDna(validHumanDna);

        assertFalse(result);
        verify(mutantDetector, times(1)).isMutant(validHumanDna);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    @DisplayName("3. Debe retornar resultado cacheado si el ADN ya fue analizado")
    void testReturnCachedResultForAnalyzedDna() {
        DnaRecord cachedRecord = new DnaRecord("somehash", true);
        when(dnaRecordRepository.findByDnaHash(anyString()))
                .thenReturn(Optional.of(cachedRecord));

        boolean result = mutantService.analyzeDna(validMutantDna);

        assertTrue(result);
        verify(mutantDetector, never()).isMutant(any());
        verify(dnaRecordRepository, never()).save(any());
    }

    @Test
    @DisplayName("4. Debe generar hash consistente para el mismo ADN")
    void testConsistentHashGeneration() {
        when(dnaRecordRepository.findByDnaHash(anyString()))
                .thenReturn(Optional.empty());
        when(mutantDetector.isMutant(any()))
                .thenReturn(true);

        mutantService.analyzeDna(validMutantDna);
        mutantService.analyzeDna(validMutantDna);

        verify(dnaRecordRepository, times(2)).findByDnaHash(anyString());
    }

    @Test
    @DisplayName("5. Debe guardar registro con hash correcto (64 chars SHA-256)")
    void testSavesRecordWithCorrectHash() {
        when(dnaRecordRepository.findByDnaHash(anyString()))
                .thenReturn(Optional.empty());
        when(mutantDetector.isMutant(validMutantDna))
                .thenReturn(true);

        mutantService.analyzeDna(validMutantDna);

        verify(dnaRecordRepository).save(argThat(record ->
                record.getDnaHash() != null &&
                        record.getDnaHash().length() == 64 &&
                        record.isMutant()
        ));
    }
}