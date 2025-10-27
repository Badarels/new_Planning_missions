package com.businesscenterservices.businesscenterservices.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class ResetPasswordRequest {
    @NotBlank(message = "Le token est obligatoire")
    private String token;

    @NotBlank(message = "Le nouveau mot de passe est obligatoire")
    private String newPassword;
} 