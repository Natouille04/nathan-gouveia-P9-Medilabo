package com.medilabo.risk_service.controller;

import com.medilabo.risk_service.dto.RiskDataResponseDTO;
import com.medilabo.risk_service.service.RiskService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/risk")
public class RiskController {
    private final RiskService riskService;

    public RiskController(RiskService riskService) {
        this.riskService = riskService;
    }

    @GetMapping("/{patId}")
    public RiskDataResponseDTO getRisk(@PathVariable Long patId) {
        return riskService.assess(patId);
    }
}