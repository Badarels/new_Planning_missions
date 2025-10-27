package com.businesscenterservices.businesscenterservices.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomClient;
    private String prenomClient;
    private String adresseClient;
    private String villeClient;
    private String telephoneClient;
    private String sexeClient;
    @OneToMany
    private List<RendezVous> rendezVousList=new ArrayList<>();

}
