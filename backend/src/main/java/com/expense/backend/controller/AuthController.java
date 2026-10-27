package com.expense.backend.controller;

import com.expense.backend.dto.AuthResponse;
import com.expense.backend.dto.LoginRequest;
import com.expense.backend.dto.RegisterRequest;
import com.expense.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final ObjectProvider<ClientRegistrationRepository> clientRegistrationRepository;

    public AuthController(
            AuthService authService,
            ObjectProvider<ClientRegistrationRepository> clientRegistrationRepository) {
        this.authService = authService;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @GetMapping("/oauth2/status")
    public ResponseEntity<Map<String, Boolean>> getOAuth2Status() {
        return ResponseEntity.ok(Map.of(
                "googleEnabled", clientRegistrationRepository.getIfAvailable() != null));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}