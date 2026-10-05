package com.medilabo.patientService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record PatientInfoDTO(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotNull @Past LocalDate birthDate,
        @NotBlank String genre,
        String address,
        String telephone
) {}