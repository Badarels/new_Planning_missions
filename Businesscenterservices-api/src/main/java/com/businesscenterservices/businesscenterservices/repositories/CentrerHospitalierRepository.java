package com.businesscenterservices.businesscenterservices.repositories;

import com.businesscenterservices.businesscenterservices.entities.CentreHospitalier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CentrerHospitalierRepository extends JpaRepository<CentreHospitalier, Long> {
}
