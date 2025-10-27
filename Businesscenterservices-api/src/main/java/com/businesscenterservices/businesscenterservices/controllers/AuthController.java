package com.businesscenterservices.businesscenterservices.controllers;

import com.businesscenterservices.businesscenterservices.dto.*;
import com.businesscenterservices.businesscenterservices.security.*;
import com.businesscenterservices.businesscenterservices.dto.LoginRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final LoginAttemptService loginAttemptService;
    private final TwoFactorAuthService twoFactorAuthService;
    private final SecurityAuditService securityAuditService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
                                            HttpServletRequest request) {
        logger.info("Tentative de connexion pour l'utilisateur: {}", loginRequest.getUsername());
        logger.debug("Headers de la requête: {}", Collections.list(request.getHeaderNames())
                .stream()
                .map(header -> header + ": " + request.getHeader(header))
                .collect(Collectors.joining(", ")));

        String username = loginRequest.getUsername();

        if (loginAttemptService.isBlocked(username)) {
            logger.warn("Compte bloqué pour l'utilisateur: {}", username);
            return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .body(Map.of(
                    "error", "Compte bloqué",
                    "message", loginAttemptService.getBlockDurationMessage()
                ));
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );

            // Si 2FA est activé
            if (twoFactorAuthService.isTwoFactorEnabled(username)) {
                twoFactorAuthService.generateAndSendCode(username);
                return ResponseEntity.ok(Map.of(
                    "requiresTwoFactor", true,
                    "message", "Code d'authentification envoyé par email"
                ));
            }

            // Si pas de 2FA, authentification directe
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
            
            logger.info("Connexion réussie pour l'utilisateur: {}", username);
            loginAttemptService.loginSucceeded(username);
            securityAuditService.auditLoginAttempt(username, true, request);

            return ResponseEntity.ok(Map.of(
                "token", jwt,
                "username", username
            ));
        } catch (Exception e) {
            logger.error("Échec de la connexion pour l'utilisateur: {} - Exception: {}", username, e.getMessage(), e);
            loginAttemptService.loginFailed(username);
            securityAuditService.auditLoginAttempt(username, false, request);
            
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                    "error", "Échec de l'authentification",
                    "message", e.getMessage()
                ));
        }
    }
}