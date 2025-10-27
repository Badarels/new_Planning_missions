package com.businesscenterservices.businesscenterservices.services;

import com.businesscenterservices.businesscenterservices.dto.AdresseDTO;
import com.businesscenterservices.businesscenterservices.dto.CentreHospitalierDTO;
import com.businesscenterservices.businesscenterservices.dto.MedecinDTO;

import java.util.List;

public interface AdresseServices {
    AdresseDTO createAdresse(AdresseDTO adresseDTO);
    AdresseDTO getAdresseById(Long adresseId);
    List<AdresseDTO> getAllAdresses();
    AdresseDTO updateAdresse(Long adresseId, AdresseDTO adresseDTO);
    void deleteAdresse(Long adresseId);
    List<CentreHospitalierDTO> getCentreHospitaliersByAdresse(Long adresseId);
    List<MedecinDTO> getMedecinsByAdresse(Long adresseId);
}
