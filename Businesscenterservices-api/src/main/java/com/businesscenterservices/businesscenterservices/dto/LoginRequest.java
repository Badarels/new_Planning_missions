package com.businesscenterservices.businesscenterservices.dto;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String username;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;
} 