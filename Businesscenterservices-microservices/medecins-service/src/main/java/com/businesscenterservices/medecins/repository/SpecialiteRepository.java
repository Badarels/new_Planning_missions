package com.businesscenterservices.medecins.repository;

import com.businesscenterservices.medecins.entity.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecialiteRepository extends JpaRepository<Specialite, Long> {

    Optional<Specialite> findByNomSpecialite(String nomSpecialite);
}
