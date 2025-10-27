package com.businesscenterservices.businesscenterservices.controllers;
import com.businesscenterservices.businesscenterservices.dto.CentreHospitalierDTO;
import com.businesscenterservices.businesscenterservices.dto.MedecinDTO;
import com.businesscenterservices.businesscenterservices.services.CentreHospitalierServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/CentreHospitaliers")
public class CentreHospitalierController {

    @Autowired
    private CentreHospitalierServices centreHospitalierService;

    @GetMapping("/{centreHospitalierId}")
    public ResponseEntity<CentreHospitalierDTO> getCentreHospitalierById(@PathVariable Long centreHospitalierId) {
        CentreHospitalierDTO centreHospitalierDTO = centreHospitalierService.getCentreHospitalierById(centreHospitalierId);
        return ResponseEntity.ok(centreHospitalierDTO);
    }

    @GetMapping
    public ResponseEntity<List<CentreHospitalierDTO>> getAllCentreHospitaliers() {
        List<CentreHospitalierDTO> centreHospitalierDTOs = centreHospitalierService.getAllCentreHospitaliers();
        return ResponseEntity.ok(centreHospitalierDTOs);
    }

    @PutMapping("/{centreHospitalierId}/archive")
    public ResponseEntity<CentreHospitalierDTO> archiveCentreHospitalier(
            @PathVariable Long centreHospitalierId) {
        CentreHospitalierDTO archivedCentreHospitalier = centreHospitalierService.archiveCentreHospitalier(centreHospitalierId);
        if (archivedCentreHospitalier != null) {
            return ResponseEntity.ok(archivedCentreHospitalier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<CentreHospitalierDTO> createCentreHospitalier(@RequestBody CentreHospitalierDTO centreHospitalierDTO) {
        CentreHospitalierDTO createdCentreHospitalier = centreHospitalierService.createCentreHospitalier(centreHospitalierDTO);
        return new ResponseEntity<>(createdCentreHospitalier, HttpStatus.CREATED);
    }

    @PutMapping("/{centreHospitalierId}")
    public ResponseEntity<CentreHospitalierDTO> updateCentreHospitalier(
            @PathVariable Long centreHospitalierId, @RequestBody CentreHospitalierDTO centreHospitalierDTO) {
        CentreHospitalierDTO updatedCentreHospitalier = centreHospitalierService.updateCentreHospitalier(
                centreHospitalierId, centreHospitalierDTO);
        if (updatedCentreHospitalier != null) {
            return ResponseEntity.ok(updatedCentreHospitalier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{centreHospitalierId}")
    public ResponseEntity<Void> deleteCentreHospitalier(@PathVariable Long centreHospitalierId) {
        centreHospitalierService.deleteCentreHospitalier(centreHospitalierId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{centreHospitalierId}/medecins")
    public ResponseEntity<List<MedecinDTO>> getMedecinsByCentreHospitalier(@PathVariable Long centreHospitalierId) {
        List<MedecinDTO> medecinDTOs = centreHospitalierService.getMedecinsByCentreHospitalier(centreHospitalierId);
        return ResponseEntity.ok(medecinDTOs);
    }
}

