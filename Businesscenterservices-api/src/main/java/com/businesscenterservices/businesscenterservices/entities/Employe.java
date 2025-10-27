package com.businesscenterservices.businesscenterservices.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nom;
    private String prenom;
    private Date datenaissance;
    private String lieunaissance;
    private String contact;
    private Date date;
    private Integer ipres;
    private Integer css;
    private Integer vrs;
    private Integer salaireBrute;
    private Integer salaireNet;
    private Integer prime;
    private Integer avancement;
    private Integer conge;
    private Integer avanceSurSalaire;
    private String type;
    @Column(columnDefinition = "boolean default false")
    private boolean archive;

}
