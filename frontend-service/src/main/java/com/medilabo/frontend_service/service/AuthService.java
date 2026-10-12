package com.medilabo.frontend_service.service;

import com.medilabo.frontend_service.dto.LoginRequestDTO;
import com.medilabo.frontend_service.dto.TokenResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AuthService {

    private final RestClient gatewayClient;

    public AuthService(RestClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }

    public String login(String username, String password) {
        TokenResponseDTO response = gatewayClient.post()
                .uri("/auth/login")
                .body(new LoginRequestDTO(username, password))
                .retrieve()
                .body(TokenResponseDTO.class);
        return response.token();
    }
}