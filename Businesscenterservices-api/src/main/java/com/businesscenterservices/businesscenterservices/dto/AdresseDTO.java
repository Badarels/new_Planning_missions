package com.businesscenterservices.businesscenterservices.dto;

import lombok.Data;

import jakarta.validation.constraints.*;
import java.util.List;

@Data
public class AdresseDTO {
    private Long id;

    @NotBlank(message = "Le nom de la rue est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom de la rue doit contenir entre 2 et 100 caractères")
    private String nomRue;

    @NotNull(message = "Le numéro de rue est obligatoire")
    @Positive(message = "Le numéro de rue doit être positif")
    private int numeroRue;

    @Size(max = 100, message = "Le complément d'adresse ne doit pas dépasser 100 caractères")
    private String complement;

    @NotBlank(message = "Le code postal est obligatoire")
    @Pattern(regexp = "^\\d{5}$", message = "Le code postal doit contenir exactement 5 chiffres")
    private String codePostal;

    @NotBlank(message = "La ville est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom de la ville doit contenir entre 2 et 50 caractères")
    private String ville;

    @NotBlank(message = "Le département est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom du département doit contenir entre 2 et 50 caractères")
    private String departement;

    @NotBlank(message = "La région est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom de la région doit contenir entre 2 et 50 caractères")
    private String region;

    private List<MedecinDTO> medecins;
    private List<CentreHospitalierDTO> centreHospitalier;
}
