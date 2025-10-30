package com.businesscenterservices.medecins.repository;

import com.businesscenterservices.medecins.entity.Medecin;
import com.businesscenterservices.medecins.entity.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {

    List<Medecin> findByAdresseVille(String ville);

    @Query("SELECT s FROM Specialite s JOIN s.medecins m WHERE m.id = :medecinId")
    List<Specialite> findSpecialitesByMedecinId(@Param("medecinId") Long medecinId);
}
