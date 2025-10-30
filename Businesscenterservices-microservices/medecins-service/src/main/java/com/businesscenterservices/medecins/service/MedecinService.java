package com.businesscenterservices.medecins.service;

import com.businesscenterservices.medecins.dto.MedecinDTO;
import com.businesscenterservices.medecins.dto.SpecialiteDTO;

import java.util.List;

public interface MedecinService {

    MedecinDTO createMedecin(MedecinDTO medecinDTO);

    MedecinDTO updateMedecin(Long id, MedecinDTO medecinDTO);

    MedecinDTO getMedecinById(Long id);

    List<MedecinDTO> getAllMedecins();

    void deleteMedecin(Long id);

    List<MedecinDTO> getMedecinsByVille(String ville);

    List<SpecialiteDTO> getSpecialitesByMedecin(Long id);
}
