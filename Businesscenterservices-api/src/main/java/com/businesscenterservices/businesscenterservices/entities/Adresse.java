package com.businesscenterservices.businesscenterservices.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Adresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String nomRue;
    private int numeroRue;
    private String complement;
    private String codePostal;
    private String ville;
    private String departement;
    private String region;

    @JsonBackReference
    @OneToMany(mappedBy = "adresse", cascade = CascadeType.ALL)
    private List<Medecin> medecins;

    @JsonBackReference("adresse-centre")
    @OneToMany(mappedBy = "adresse", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<CentreHospitalier> centreHospitalier;


}
