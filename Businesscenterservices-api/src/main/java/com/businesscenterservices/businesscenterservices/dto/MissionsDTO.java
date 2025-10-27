package com.businesscenterservices.businesscenterservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MissionsDTO {
    private Long id;
    private String detailMission;
    private LocalDate dateMissions;
    private Double salaireMission;
    private LocalDate dateFinMissions;
    private String statutAnnonce;
    private CentreHospitalierDTO centreHospitalier;
    private MedecinDTO medecin;
    private SpecialiteDTO specialite;
    private boolean archived;
    private String typeHoraireMission;
    private LocalTime startTime;
    private LocalTime endTime;

}

