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
public class RendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelleRendezVous;
    private Date dateRendezVous;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
