package com.businesscenterservices.businesscenterservices.services;

import com.businesscenterservices.businesscenterservices.dto.QualificationDTO;

import java.util.List;

public interface Qualification {
    QualificationDTO createQualification(QualificationDTO qualificationDTO);

    QualificationDTO getQualificationById(Long qualificationId);

    List<QualificationDTO> getAllQualifications();

    QualificationDTO updateQualification(Long qualificationId, QualificationDTO qualificationDTO);

    void deleteQualification(Long qualificationId);
}
