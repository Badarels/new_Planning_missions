package com.businesscenterservices.businesscenterservices.repositories;

import com.businesscenterservices.businesscenterservices.entities.Besoins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BesoinsRepository extends JpaRepository<Besoins, Long> {

}
