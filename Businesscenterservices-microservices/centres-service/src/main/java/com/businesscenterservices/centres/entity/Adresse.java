package com.businesscenterservices.centres.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Adresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomRue;
    private Integer numeroRue;
    private String complement;
    private String codePostal;
    private String ville;
    private String departement;
    private String region;
}
