package com.businesscenterservices.businesscenterservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

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

    @Future(message = "La date d'échéance doit être dans le futur")
    private Date dateEcheance;

    @NotBlank(message = "Le lieu de naissance est obligatoire")
    private String lieuDeNaissanceMedecin;

    @NotBlank(message = "Le numéro de sécurité sociale est obligatoire")
    @Pattern(regexp = "^\\d{15}$", message = "Le numéro de sécurité sociale doit contenir 15 chiffres")
    private String numeroSecuriteSocialeMedecin;

    @NotBlank(message = "Le numéro de téléphone principal est obligatoire")
    @Pattern(regexp = "^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$", 
            message = "Le numéro de téléphone doit être un numéro français valide")
    private String telephoneMedecin_1;

    @Pattern(regexp = "^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$", 
            message = "Le numéro de téléphone doit être un numéro français valide")
    private String telephoneMedecin_2;

    @NotBlank(message = "Le statut du médecin est obligatoire")
    private String statutMedecin;

    @NotBlank(message = "Le numéro RPPS est obligatoire")
    @Pattern(regexp = "^\\d{11}$", message = "Le numéro RPPS doit contenir 11 chiffres")
    private String numeroRpps;

    private String qualifications;

    @NotBlank(message = "Le numéro d'inscription à l'ordre est obligatoire")
    private String inscription_A_lordre;

    @NotNull(message = "L'adresse est obligatoire")
    private AdresseDTO adresse;

    private CentreHospitalierDTO centreHospitalier;

    private List<MissionsDTO> missions;

    @NotEmpty(message = "Au moins une spécialité est requise")
    private List<SpecialiteDTO> specialites;
}
