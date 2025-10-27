package com.businesscenterservices.businesscenterservices.repositories;

import com.businesscenterservices.businesscenterservices.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {

    Roles findByNomRoles(String nomRoles);
}
