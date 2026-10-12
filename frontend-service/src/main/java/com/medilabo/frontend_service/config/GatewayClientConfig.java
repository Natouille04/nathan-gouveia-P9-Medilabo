package com.medilabo.frontend_service.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class GatewayClientConfig {
    @Bean
    RestClient gatewayClient(@Value("${gateway.url}") String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestInterceptor((request, body, execution) -> {

                    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

                    if (attributes != null) {
                        HttpServletRequest currentRequest = attributes.getRequest();
                        HttpSession session = currentRequest.getSession(false);

                        if (session != null && session.getAttribute("token") != null) {
                            String token = (String) session.getAttribute("token");
                            request.getHeaders().setBearerAuth(token);
                        }
                    }

                    return execution.execute(request, body);
                })
                .build();
    }
}
