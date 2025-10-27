package com.businesscenterservices.businesscenterservices.services;

import com.businesscenterservices.businesscenterservices.dto.CentreHospitalierDTO;
import com.businesscenterservices.businesscenterservices.dto.MedecinDTO;

import java.util.List;

public interface CentreHospitalierServices {
    CentreHospitalierDTO createCentreHospitalier(CentreHospitalierDTO centreHospitalierDTO);
    CentreHospitalierDTO getCentreHospitalierById(Long centreHospitalierId);
    List<CentreHospitalierDTO> getAllCentreHospitaliers();
    CentreHospitalierDTO updateCentreHospitalier(Long centreHospitalierId, CentreHospitalierDTO centreHospitalierDTO);
    void deleteCentreHospitalier(Long centreHospitalierId);
    List<MedecinDTO> getMedecinsByCentreHospitalier(Long centreHospitalierId);
    CentreHospitalierDTO archiveCentreHospitalier(Long id);
}
