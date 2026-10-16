package com.medilabo.risk_service.dto;

import com.medilabo.risk_service.data.RiskLevel;

public record RiskDataResponseDTO(
    RiskLevel risk
) {}
