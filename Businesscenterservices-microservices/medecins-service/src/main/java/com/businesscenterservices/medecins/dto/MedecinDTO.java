package com.businesscenterservices.medecins.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedecinDTO {

    private Long id;

    @NotBlank(message = "Le nom du médecin est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    private String nomMedecin;

    @NotBlank(message = "Le prénom du médecin est obligatoire")
    @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
    private String prenomMedecin;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String emailMedecin;

    @NotBlank(message = "Le sexe est obligatoire")
    @Pattern(regexp = "^(M|F)$", message = "Le sexe doit être 'M' ou 'F'")
    private String sexeMedecin;

    @NotNull(message = "La date de naissance est obligatoire")
    @Past(message = "La date de naissance doit être dans le passé")
    private Date dateDeNaissanceMedecin;

    private Date dateEcheance;

    @NotBlank(message = "Le lieu de naissance est obligatoire")
    private String lieuDeNaissanceMedecin;

    @NotBlank(message = "Le numéro de sécurité sociale est obligatoire")
    @Pattern(regexp = "^\\d{15}$", message = "Le numéro de sécurité sociale doit contenir 15 chiffres")
    private String numeroSecuriteSocialeMedecin;

    @NotBlank(message = "Le numéro de téléphone principal est obligatoire")
    private String telephoneMedecin1;

    private String telephoneMedecin2;

    @NotBlank(message = "Le statut du médecin est obligatoire")
    private String statutMedecin;

    @NotBlank(message = "Le numéro RPPS est obligatoire")
    @Pattern(regexp = "^\\d{11}$", message = "Le numéro RPPS doit contenir 11 chiffres")
    private String numeroRpps;

    private String qualifications;

    @NotBlank(message = "Le numéro d'inscription à l'ordre est obligatoire")
    private String inscriptionAlOrdre;

    private Long centreHospitalierId;

    @NotNull(message = "L'adresse est obligatoire")
    @Valid
    private AdresseDTO adresse;

    @NotEmpty(message = "Au moins une spécialité est requise")
    @Valid
    private List<SpecialiteDTO> specialites;
}
