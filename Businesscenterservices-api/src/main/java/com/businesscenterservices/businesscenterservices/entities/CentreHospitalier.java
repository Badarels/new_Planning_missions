package com.businesscenterservices.businesscenterservices.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CentreHospitalier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email_ch;
    private String nom_ch;
    private String siret;
    private String telephone;
    private boolean archived;

    @ManyToOne
    @JsonManagedReference("adresse-centre")
    @JoinColumn(name = "adresse_id")
    private Adresse adresse;

    @OneToMany(mappedBy = "centreHospitalier", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Medecin> medecins;

    @JsonManagedReference
    @OneToMany(mappedBy = "centreHospitalier", cascade = CascadeType.ALL)
    private List<Missions> missions;
}
