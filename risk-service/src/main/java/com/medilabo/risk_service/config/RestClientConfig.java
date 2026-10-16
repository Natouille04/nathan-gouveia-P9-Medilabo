package com.medilabo.risk_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient patientRestClient(@Value("${patient.url}") String patientUrl) {
        return RestClient.builder()
                .baseUrl(patientUrl)
                .build();
    }

    @Bean
    public RestClient notesRestClient(@Value("${notes.url}") String notesUrl) {
        return RestClient.builder()
                .baseUrl(notesUrl)
                .build();
    }
}