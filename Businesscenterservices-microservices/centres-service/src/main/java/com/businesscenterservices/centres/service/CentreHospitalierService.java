package com.businesscenterservices.centres.service;

import com.businesscenterservices.centres.dto.CentreHospitalierDTO;

import java.util.List;

public interface CentreHospitalierService {

    CentreHospitalierDTO create(CentreHospitalierDTO dto);

    CentreHospitalierDTO update(Long id, CentreHospitalierDTO dto);

    CentreHospitalierDTO getById(Long id);

    List<CentreHospitalierDTO> getAll();

    void delete(Long id);

    CentreHospitalierDTO archive(Long id);
}
