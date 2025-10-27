package com.businesscenterservices.businesscenterservices.services;

import com.businesscenterservices.businesscenterservices.dto.MedecinDTO;
import com.businesscenterservices.businesscenterservices.dto.SpecialiteDTO;

import java.util.List;

public interface MedecinServices {

    MedecinDTO createMedecin(MedecinDTO medecinDTO);

    MedecinDTO getMedecinById(Long medecinId);

    List<MedecinDTO> getAllMedecins();

    MedecinDTO updateMedecin(Long medecinId, MedecinDTO medecinDTO);

    void deleteMedecin(Long medecinId);
    // Rechercher des médecins par ville
    List<MedecinDTO> getMedecinsByVille(String ville);
    // Recherche de spécialités par médecin
    List<SpecialiteDTO> getSpecialitesByMedecin(Long medecinId);


}
