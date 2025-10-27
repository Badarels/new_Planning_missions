package com.businesscenterservices.businesscenterservices.controllers;

import com.businesscenterservices.businesscenterservices.dto.MedecinDTO;
import com.businesscenterservices.businesscenterservices.dto.SpecialiteDTO;
import com.businesscenterservices.businesscenterservices.services.SpecialiteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class SpecialiteController {


    @Autowired
    private SpecialiteServices specialiteService;

    @GetMapping("Specialites/{specialiteId}")
    public ResponseEntity<SpecialiteDTO> getSpecialiteById(@PathVariable Long specialiteId) {
        SpecialiteDTO specialiteDTO = specialiteService.getSpecialiteById(specialiteId);
        return ResponseEntity.ok(specialiteDTO);
    }

    @GetMapping("Specialites")
    public ResponseEntity<List<SpecialiteDTO>> getAllSpecialites() {
        List<SpecialiteDTO> specialiteDTOs = specialiteService.getAllSpecialites();
        return ResponseEntity.ok(specialiteDTOs);
    }

    @PostMapping("Specialites")
    public ResponseEntity<SpecialiteDTO> createSpecialite(@RequestBody SpecialiteDTO specialiteDTO) {
        SpecialiteDTO createdSpecialite = specialiteService.createSpecialite(specialiteDTO);
        return new ResponseEntity<>(createdSpecialite, HttpStatus.CREATED);
    }

    @PutMapping("Specialites/{specialiteId}")
    public ResponseEntity<SpecialiteDTO> updateSpecialite(
            @PathVariable Long specialiteId, @RequestBody SpecialiteDTO specialiteDTO) {
        SpecialiteDTO updatedSpecialite = specialiteService.updateSpecialite(specialiteId, specialiteDTO);
        if (updatedSpecialite != null) {
            return ResponseEntity.ok(updatedSpecialite);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("Specialites/{specialiteId}")
    public ResponseEntity<Void> deleteSpecialite(@PathVariable Long specialiteId) {
        specialiteService.deleteSpecialite(specialiteId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("Specialites/{specialiteId}/medecins")
    public ResponseEntity<List<MedecinDTO>> getMedecinsBySpecialite(@PathVariable Long specialiteId) {
        List<MedecinDTO> medecinDTOs = specialiteService.getMedecinsBySpecialite(specialiteId);
        return ResponseEntity.ok(medecinDTOs);
    }
}
