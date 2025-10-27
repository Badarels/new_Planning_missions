package com.businesscenterservices.businesscenterservices.repositories;

import com.businesscenterservices.businesscenterservices.entities.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QualificationRepository extends JpaRepository<Qualification, Long> {
}
