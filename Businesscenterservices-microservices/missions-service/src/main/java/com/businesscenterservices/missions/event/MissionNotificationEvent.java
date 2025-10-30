package com.businesscenterservices.missions.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionNotificationEvent {

    private Long missionId;
    private Long medecinId;
    private Long centreHospitalierId;
    private String detailMission;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalTime startTime;
    private LocalTime endTime;
    private Double remuneration;
    private String statut;
    private String typeHoraire;
    private boolean archived;
}
