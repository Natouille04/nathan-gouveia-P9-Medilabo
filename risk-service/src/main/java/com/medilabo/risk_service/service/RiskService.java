package com.medilabo.risk_service.service;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.medilabo.risk_service.data.RiskLevel;
import com.medilabo.risk_service.dto.NoteDTO;
import com.medilabo.risk_service.dto.PatientDataDTO;
import com.medilabo.risk_service.dto.RiskDataResponseDTO;

@Service
public class RiskService {

    private final RestClient patientClient;
    private final RestClient notesClient;

    public RiskService(@Qualifier("patientRestClient") RestClient patientClient,
            @Qualifier("notesRestClient") RestClient notesClient) {
        this.patientClient = patientClient;
        this.notesClient = notesClient;
    }

    private static final List<String> TRIGGERS = List.of(
        "hemoglobine",
        "microalbumine",
        "taille",
        "poids",
        "fumeu",
        "anormal",
        "cholesterol",
        "vertige",
        "rechute",
        "reaction",
        "anticorps"
    );

    private String normalize(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT);
    }

    private int countTriggers(List<NoteDTO> notes) {
        if (notes == null || notes.isEmpty()) return 0;

        String text = normalize(notes.stream()
                .map(NoteDTO::note)
                .filter(Objects::nonNull)
                .collect(Collectors.joining("\n")));

        return (int) TRIGGERS.stream()
                .filter(text::contains)
                .count();
    }

    private int ageCalculator(LocalDate birthdate) {
        LocalDate now = LocalDate.now();
        return Period.between(birthdate, now).getYears();
    }

    private PatientDataDTO fetchPatient(Long patId) {
        return patientClient.get()
                .uri("/patient/{id}", patId)
                .retrieve()
                .body(PatientDataDTO.class);
    }

    private List<NoteDTO> fetchNotes(Long patId) {
        return notesClient.get()
                .uri("/notes/{patId}", patId)
                .retrieve()
                .body(new ParameterizedTypeReference<List<NoteDTO>>() {
                });
    }

    private RiskDataResponseDTO calculateRisk(LocalDate birthDate, String genre, List<NoteDTO> notes) {
        int age = ageCalculator(birthDate);
        int triggers = countTriggers(notes);

        RiskLevel risk;

        if (age >= 30) {
            if (triggers >= 8) {
                risk = RiskLevel.EARLY_ONSET;
            }

            else if (triggers >= 6) {
                risk = RiskLevel.IN_DANGER;
            }

            else if (triggers >= 2) {
                risk = RiskLevel.BORDERLINE;
            }

            else {
                risk = RiskLevel.NONE;
            }
        }

        else {
            boolean male = "M".equalsIgnoreCase(genre);

            if (triggers >= (male ? 5 : 7)) {
                risk = RiskLevel.EARLY_ONSET;
            }

            else if (triggers >= (male ? 3 : 4)) {
                risk = RiskLevel.IN_DANGER;
            }

            else {
                risk = RiskLevel.NONE;
            }
        }

        return new RiskDataResponseDTO(risk);
    }

    public RiskDataResponseDTO assess(Long patId) {
        PatientDataDTO patient = fetchPatient(patId);
        List<NoteDTO> notes = fetchNotes(patId);

        return calculateRisk(patient.birthDate(), patient.genre(), notes);
    }
}
