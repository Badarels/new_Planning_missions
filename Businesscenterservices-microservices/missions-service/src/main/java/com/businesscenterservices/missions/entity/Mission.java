package com.businesscenterservices.missions.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "missions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String detail;

    @Column(nullable = false)
    private LocalDate dateDebut;

    @Column
    private LocalDate dateFin;

    @Column
    private Double remuneration;

    @Column(nullable = false)
    private String statut;

    @Column(nullable = false)
    private String typeHoraire;

    @Column
    private LocalTime startTime;

    @Column
    private LocalTime endTime;

    @Column(nullable = false)
    private boolean archived = false;

    @Column(name = "centre_hospitalier_id", nullable = false)
    private Long centreHospitalierId;

    @Column(name = "medecin_id")
    private Long medecinId;

    @Column(name = "specialite_id")
    private Long specialiteId;

    @Column(name = "planning_id")
    private Long planningId;
}
