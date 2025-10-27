package com.businesscenterservices.businesscenterservices.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Specialite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomSpecialite;

    @ManyToMany(mappedBy = "specialites")
    private List<Medecin> medecins = new ArrayList<>();


}
