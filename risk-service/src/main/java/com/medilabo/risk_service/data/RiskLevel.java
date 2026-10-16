package com.medilabo.risk_service.data;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RiskLevel {
    NONE("None"),
    BORDERLINE("Borderline"),
    IN_DANGER("InDanger"),
    EARLY_ONSET("EarlyOnset");

    @JsonValue private final String label;

    RiskLevel(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }
}