package com.businesscenterservices.centres.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdresseDTO {

    private Long id;

    @NotBlank(message = "Le nom de la rue est obligatoire")
    private String nomRue;

    private Integer numeroRue;

    private String complement;

    @NotBlank(message = "Le code postal est obligatoire")
    @Size(min = 4, max = 10, message = "Le code postal doit contenir entre 4 et 10 caractères")
    private String codePostal;

    @NotBlank(message = "La ville est obligatoire")
    private String ville;

    private String departement;

    private String region;
}
