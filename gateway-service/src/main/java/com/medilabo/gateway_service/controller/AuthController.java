package com.medilabo.gateway_service.controller;

import com.medilabo.gateway_service.dto.LoginRequestDTO;
import com.medilabo.gateway_service.dto.TokenReponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final ReactiveUserDetailsService users;
    private final PasswordEncoder encoder;
    private final JwtEncoder jwtEncoder;

    public AuthController(ReactiveUserDetailsService users, PasswordEncoder encoder, JwtEncoder jwtEncoder) {
        this.users = users;
        this.encoder = encoder;
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping("/login")
    public Mono<TokenReponseDTO> login(@RequestBody LoginRequestDTO request) {
        return users.findByUsername(request.username())
                .filter(user -> encoder.matches(request.password(), user.getPassword()))
                .map(user -> new TokenReponseDTO(generateToken(user.getUsername())))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));
    }

    private String generateToken(String username) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(username)
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .build();
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }
}
