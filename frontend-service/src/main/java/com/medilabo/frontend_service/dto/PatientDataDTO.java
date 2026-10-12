package com.medilabo.frontend_service.dto;

import java.time.LocalDate;

public record PatientDataDTO(
        Long id,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String genre,
        String address,
        String telephone
) {}
