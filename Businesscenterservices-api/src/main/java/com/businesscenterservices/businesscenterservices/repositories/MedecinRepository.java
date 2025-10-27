package com.businesscenterservices.businesscenterservices.repositories;

import com.businesscenterservices.businesscenterservices.entities.Medecin;
import com.businesscenterservices.businesscenterservices.entities.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    List<Medecin> findByAdresseVille(String ville);

    @Query("SELECT s FROM Specialite s JOIN s.medecins m WHERE m.id = :medecinId")
    List<Specialite> getSpecialitesByMedecin(@Param("medecinId") Long medecinId);
}
