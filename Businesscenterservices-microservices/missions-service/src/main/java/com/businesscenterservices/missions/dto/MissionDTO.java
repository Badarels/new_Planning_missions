package com.businesscenterservices.missions.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MissionDTO {

    private Long id;

    @NotBlank(message = "La description de la mission est obligatoire")
    @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
    private String detailMission;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateMissions;

    @FutureOrPresent(message = "La date de fin doit être dans le présent ou le futur")
    private LocalDate dateFinMissions;

    @Positive(message = "La rémunération doit être positive")
    private Double salaireMission;

    @NotBlank(message = "Le statut de la mission est obligatoire")
    private String statutAnnonce;

    @NotBlank(message = "Le type horaire est obligatoire")
    private String typeHoraireMission;

    private LocalTime startTime;
    private LocalTime endTime;
    private boolean archived;

    @NotNull(message = "L'identifiant du centre hospitalier est obligatoire")
    private Long centreHospitalierId;

    private Long medecinId;
    private Long specialiteId;
    private Long planningId;
}
