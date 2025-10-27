package com.businesscenterservices.businesscenterservices.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class TwoFactorVerificationRequest {
    @NotBlank(message = "L'email est obligatoire")
    private String username;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;

    @NotBlank(message = "Le code d'authentification est obligatoire")
    private String code;
} 