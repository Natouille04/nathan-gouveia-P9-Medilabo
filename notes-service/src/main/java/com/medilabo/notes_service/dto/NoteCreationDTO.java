package com.medilabo.notes_service.dto;

public record NoteCreationDTO(
    Long patId,
    String note
) {}