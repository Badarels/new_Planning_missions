package com.businesscenterservices.businesscenterservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CentreHospitalierDTO {
    private Long id;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email_ch;

    @NotBlank(message = "Le nom du centre hospitalier est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String nom_ch;

    @NotBlank(message = "Le numéro SIRET est obligatoire")
    @Pattern(regexp = "^\\d{14}$", message = "Le numéro SIRET doit contenir 14 chiffres")
    private String siret;

    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    @Pattern(regexp = "^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$", 
            message = "Le numéro de téléphone doit être un numéro français valide")
    private String telephone;

    private boolean archived;

    @NotNull(message = "L'adresse est obligatoire")
    private AdresseDTO adresse;

    private List<MedecinDTO> medecins;

    private List<MissionsDTO> missions;
}
