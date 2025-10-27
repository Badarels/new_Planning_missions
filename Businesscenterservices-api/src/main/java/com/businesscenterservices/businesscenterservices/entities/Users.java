package com.businesscenterservices.businesscenterservices.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomUser;
    private String prenomUser;
    private String adresseUser;
    private String telephoneUser;
    private String sexeUser;
    private LocalDate dateNaissanceUser;
    private String numeroPieceIdentiteUser;
    private String passwordUser;
    private String emailUser;

    private boolean archived;

    @Column(columnDefinition = "boolean default true")
    private boolean status;

    @Column(columnDefinition = "boolean default false")
    private boolean passwordChanged;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roles_id", referencedColumnName = "id")
    private Roles roles;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Appel> appelList = new ArrayList<>();
}


