package com.businesscenterservices.businesscenterservices.controllers;

import com.businesscenterservices.businesscenterservices.dto.MedecinDTO;
import com.businesscenterservices.businesscenterservices.dto.SpecialiteDTO;
import com.businesscenterservices.businesscenterservices.services.MedecinServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Medecins")
public class MedecinsController{

    @Autowired
    private MedecinServices medecinService;

    @GetMapping("/{medecinId}")
    public ResponseEntity<MedecinDTO> getMedecinById(@PathVariable Long medecinId) {
        MedecinDTO medecinDTO = medecinService.getMedecinById(medecinId);
        return ResponseEntity.ok(medecinDTO);
    }

    @GetMapping
    public ResponseEntity<List<MedecinDTO>> getAllMedecins() {
        List<MedecinDTO> medecinDTOs = medecinService.getAllMedecins();
        return ResponseEntity.ok(medecinDTOs);
    }

    @PostMapping
    public ResponseEntity<MedecinDTO> createMedecin(@RequestBody MedecinDTO medecinDTO) {
        MedecinDTO createdMedecin = medecinService.createMedecin(medecinDTO);
        return new ResponseEntity<>(createdMedecin, HttpStatus.CREATED);
    }

    @PutMapping("/{medecinId}")
    public ResponseEntity<MedecinDTO> updateMedecin(@PathVariable Long medecinId, @RequestBody MedecinDTO medecinDTO) {
        MedecinDTO updatedMedecin = medecinService.updateMedecin(medecinId, medecinDTO);
        if (updatedMedecin != null) {
            return ResponseEntity.ok(updatedMedecin);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{medecinId}")
    public ResponseEntity<Void> deleteMedecin(@PathVariable Long medecinId) {
        medecinService.deleteMedecin(medecinId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{medecinId}/specialites")
    public ResponseEntity<List<SpecialiteDTO>> getSpecialitesByMedecin(@PathVariable Long medecinId) {
        List<SpecialiteDTO> specialiteDTOs = medecinService.getSpecialitesByMedecin(medecinId);
        return ResponseEntity.ok(specialiteDTOs);
    }

}
