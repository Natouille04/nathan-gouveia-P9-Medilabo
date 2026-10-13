package com.medilabo.notes_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notes")
public record Note(
        @Id String id,
        Long patId,
        String note
) {}