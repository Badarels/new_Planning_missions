package com.businesscenterservices.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_user", nullable = false)
    private String nomUser;

    @Column(name = "prenom_user", nullable = false)
    private String prenomUser;

    @Column(name = "adresse_user")
    private String adresseUser;

    @Column(name = "telephone_user", nullable = false)
    private String telephoneUser;

    @Column(name = "sexe_user")
    private String sexeUser;

    @Column(name = "date_naissance_user")
    private LocalDate dateNaissanceUser;

    @Column(name = "numero_piece_identite_user")
    private String numeroPieceIdentiteUser;

    @Column(name = "password_user", nullable = false)
    private String passwordUser;

    @Column(name = "email_user", nullable = false, unique = true)
    private String emailUser;

    @Column(nullable = false)
    private boolean archived = false;

    @Column(columnDefinition = "boolean default true")
    private boolean status = true;

    @Column(name = "password_changed", columnDefinition = "boolean default false")
    private boolean passwordChanged = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roles_id")
    private Role roles;
}
