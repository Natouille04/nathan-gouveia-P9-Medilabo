package com.medilabo.frontend_service.service;

import com.medilabo.frontend_service.dto.PatientCreationDTO;
import com.medilabo.frontend_service.dto.PatientDataDTO;
import com.medilabo.frontend_service.dto.PatientEditDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

@Service
public class PatientService {

    private final RestClient gatewayClient;

    public PatientService(RestClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }

    public List<PatientDataDTO> getAllPatients() {
        return gatewayClient.get()
                .uri("/patient")
                .retrieve()
                .body(new ParameterizedTypeReference<List<PatientDataDTO>>() {});
    }

    public PatientDataDTO getPatientById(int id) {
        return gatewayClient.get()
                .uri("/patient/" + id)
                .retrieve()
                .body(new ParameterizedTypeReference<PatientDataDTO>() {});
    }

    public void createPatient(PatientCreationDTO dto) {
        gatewayClient.post()
                .uri("/patient")
                .body(dto)
                .retrieve()
                .toBodilessEntity();
    }

    public void updatePatient(Long id, PatientEditDTO dto) {
        gatewayClient.put()
                .uri("/patient/{id}", id)
                .body(dto)
                .retrieve()
                .toBodilessEntity();
    }
}
