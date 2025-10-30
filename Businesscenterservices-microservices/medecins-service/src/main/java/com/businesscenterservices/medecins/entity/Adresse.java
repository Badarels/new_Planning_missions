package com.businesscenterservices.medecins.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Adresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomRue;
    private int numeroRue;
    private String complement;
    private String codePostal;
    private String ville;
    private String departement;
    private String region;

    @JsonBackReference
    @JsonIgnore
    @OneToMany(mappedBy = "adresse", cascade = CascadeType.ALL)
    private List<Medecin> medecins;
}
