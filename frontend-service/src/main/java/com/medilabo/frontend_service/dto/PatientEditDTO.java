package com.medilabo.frontend_service.dto;

public record PatientEditDTO(
        String firstName,
        String lastName,
        String genre,
        String address,
        String telephone
) {}
