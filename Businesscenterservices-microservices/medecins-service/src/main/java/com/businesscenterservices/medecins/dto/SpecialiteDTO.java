package com.businesscenterservices.medecins.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialiteDTO {

    private Long id;

    @NotBlank(message = "Le nom de la spécialité est obligatoire")
    private String nomSpecialite;
}
