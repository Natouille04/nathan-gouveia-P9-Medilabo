package com.medilabo.frontend_service.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.medilabo.frontend_service.dto.NoteDataDTO;

@Service
public class NoteService {
    private final RestClient gatewayClient;

    public NoteService(RestClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }

    public NoteDataDTO getNoteByPatId(int id) {
        return gatewayClient.get()
                .uri("/notes/" + id)
                .retrieve()
                .body(new ParameterizedTypeReference<NoteDataDTO>() {});
    }
}
