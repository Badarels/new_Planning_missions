package com.businesscenterservices.businesscenterservices.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Missions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String detailMission;
    private LocalDate dateMissions;
    private LocalDate dateFinMissions;
    private Double salaireMission;
    private String statutAnnonce;
    private String typeHoraireMission;
    private boolean archived;
    private LocalTime startTime;
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "centreHospitalier_id")
    @JsonBackReference
    private CentreHospitalier centreHospitalier;

    @ManyToOne
    @JoinColumn(name = "Planning_id")
    @JsonBackReference
    private Planning planning;

    @ManyToOne
    @JoinColumn(name = "medecin_id")
    @JsonBackReference
    private Medecin medecin;

    @ManyToOne
    @JoinColumn(name = "specialite_id")
    @JsonBackReference
    private Specialite specialite;
}
