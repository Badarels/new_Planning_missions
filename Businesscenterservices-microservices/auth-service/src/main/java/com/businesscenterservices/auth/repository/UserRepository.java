package com.businesscenterservices.auth.repository;

import com.businesscenterservices.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailUser(String emailUser);

    Optional<User> findByNomUser(String nomUser);

    List<User> findByArchivedFalseAndStatusTrueAndRoles_NomRoles(String nomRoles);
}
