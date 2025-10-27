package com.businesscenterservices.businesscenterservices.services;

import com.businesscenterservices.businesscenterservices.dto.MedecinDTO;
import com.businesscenterservices.businesscenterservices.dto.SpecialiteDTO;

import java.util.List;

public interface SpecialiteServices {
    SpecialiteDTO createSpecialite(SpecialiteDTO specialiteDTO);
    SpecialiteDTO getSpecialiteById(Long specialiteId);
    List<SpecialiteDTO> getAllSpecialites();
    SpecialiteDTO updateSpecialite(Long specialiteId, SpecialiteDTO specialiteDTO);
    void deleteSpecialite(Long specialiteId);
    List<MedecinDTO> getMedecinsBySpecialite(Long specialiteId);
}
