package com.medilabo.frontend_service.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.medilabo.frontend_service.dto.RiskDTO;

@Service
public class RiskService {
    private final RestClient gatewayClient;

    public RiskService(RestClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }

    public RiskDTO getRiskByPatId(int PatId) {
        return gatewayClient.get()
                .uri("/risk/" + PatId)
                .retrieve()
                .body(new ParameterizedTypeReference<RiskDTO>() {});
    }
}