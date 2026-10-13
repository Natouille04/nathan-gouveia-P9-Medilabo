package com.medilabo.frontend_service.dto;

public record NoteCreationDTO(
    Long patId,
    String note
) {}
