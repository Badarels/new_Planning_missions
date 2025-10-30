package com.businesscenterservices.centres.repository;

import com.businesscenterservices.centres.entity.CentreHospitalier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CentreHospitalierRepository extends JpaRepository<CentreHospitalier, Long> {

    Optional<CentreHospitalier> findBySiret(String siret);
}
