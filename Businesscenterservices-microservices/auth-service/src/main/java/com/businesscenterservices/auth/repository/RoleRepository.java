package com.businesscenterservices.auth.repository;

import com.businesscenterservices.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByNomRoles(String nomRoles);
}
