package com.businesscenterservices.businesscenterservices.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
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
    private Date dateDeNaissanceMedecin;
    private Date dateEcheance;
    private String lieuDeNaissanceMedecin;
    private String numeroSecuriteSocialeMedecin;
    private String telephoneMedecin_1;
    private String telephoneMedecin_2;
    private String statutMedecin;
    private String numeroRpps;
    private String qualifications;
    private String inscription_A_lordre;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adresse_id")
    private Adresse adresse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "centreHospitalier_id")
    private CentreHospitalier centreHospitalier;

    @JsonManagedReference
    @OneToMany(mappedBy = "medecin", cascade = CascadeType.ALL)
    private List<Missions> missions;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "medecin_specialites",
            joinColumns = @JoinColumn(name = "medecin_id"),
            inverseJoinColumns = @JoinColumn(name = "specialite_id")
    )
    private List<Specialite> specialites = new ArrayList<>();
}
