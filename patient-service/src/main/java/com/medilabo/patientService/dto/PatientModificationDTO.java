package com.medilabo.patientService.dto;

import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record PatientModificationDTO(
        String firstName,
        String lastName,
        @Past LocalDate birthDate,
        String genre,
        String address,
        String telephone
) {}