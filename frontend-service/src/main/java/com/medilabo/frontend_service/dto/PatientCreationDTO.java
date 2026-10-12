package com.medilabo.frontend_service.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record PatientCreationDTO(
        String firstName,
        String lastName,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,
        String genre,
        String address,
        String telephone
) {}
