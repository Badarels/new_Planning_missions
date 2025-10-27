package com.businesscenterservices.businesscenterservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {

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
    private boolean status;
    private boolean passwordChanged;
    private RolesDTO roles;
    private List<AppelDTO> appelList;
}
