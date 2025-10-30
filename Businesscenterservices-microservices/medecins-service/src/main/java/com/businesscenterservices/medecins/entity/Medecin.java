package com.businesscenterservices.medecins.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medecin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomMedecin;
    private String prenomMedecin;
    private String emailMedecin;
    private String sexeMedecin;
    @Temporal(TemporalType.DATE)
    private Date dateDeNaissanceMedecin;
    @Temporal(TemporalType.DATE)
    private Date dateEcheance;
    private String lieuDeNaissanceMedecin;
    private String numeroSecuriteSocialeMedecin;
    private String telephoneMedecin1;
    private String telephoneMedecin2;
    private String statutMedecin;
    private String numeroRpps;
    private String qualifications;
    private String inscriptionAlOrdre;

    private Long centreHospitalierId;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "adresse_id")
    private Adresse adresse;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "medecin_specialites",
            joinColumns = @JoinColumn(name = "medecin_id"),
            inverseJoinColumns = @JoinColumn(name = "specialite_id")
    )
    private List<Specialite> specialites = new ArrayList<>();
}
