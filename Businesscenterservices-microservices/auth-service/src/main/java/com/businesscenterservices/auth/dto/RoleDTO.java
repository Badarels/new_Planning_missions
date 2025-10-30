package com.businesscenterservices.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    private Long id;

    @NotBlank(message = "Le nom du rôle est obligatoire")
    private String nomRoles;
}
