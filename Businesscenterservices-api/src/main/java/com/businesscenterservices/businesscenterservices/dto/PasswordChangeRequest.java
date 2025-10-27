package com.businesscenterservices.businesscenterservices.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class PasswordChangeRequest {
    @NotBlank(message = "L'ancien mot de passe est obligatoire")
    private String oldPassword;

    @NotBlank(message = "Le nouveau mot de passe est obligatoire")
    private String newPassword;

    @NotBlank(message = "La confirmation du mot de passe est obligatoire")
    private String confirmPassword;
} 