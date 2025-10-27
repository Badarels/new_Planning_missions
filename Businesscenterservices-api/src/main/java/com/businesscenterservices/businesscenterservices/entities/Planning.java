package com.businesscenterservices.businesscenterservices.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Planning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String commentaire;
    private boolean archived;

    @OneToMany(mappedBy = "planning", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Missions> missions;

}
