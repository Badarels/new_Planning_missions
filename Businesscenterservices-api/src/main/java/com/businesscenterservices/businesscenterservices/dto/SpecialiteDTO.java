package com.businesscenterservices.businesscenterservices.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialiteDTO {

    private Long id;

    @NotBlank(message = "Le nom de la spécialité est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom de la spécialité doit contenir entre 2 et 100 caractères")
    private String nomSpecialite;

    @NotBlank(message = "La description est obligatoire")
    @Size(min = 10, max = 500, message = "La description doit contenir entre 10 et 500 caractères")
    private String description;

    @NotNull(message = "Le tarif horaire est obligatoire")
    @Positive(message = "Le tarif horaire doit être positif")
    private Double tarifHoraire;

    private List<MedecinDTO> medecins;
}