package com.businesscenterservices.businesscenterservices.controllers;

import com.businesscenterservices.businesscenterservices.dto.AdresseDTO;
import com.businesscenterservices.businesscenterservices.dto.CentreHospitalierDTO;
import com.businesscenterservices.businesscenterservices.dto.MedecinDTO;
import com.businesscenterservices.businesscenterservices.services.AdresseServicesImpl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/")
@Validated
@RequiredArgsConstructor
public class AdresseController {
    private final AdresseServicesImpl adresseService;

    @GetMapping("Adresses/{adresseId}")
    public ResponseEntity<AdresseDTO> getAdresseById(@PathVariable @Positive(message = "L'ID doit être positif") Long adresseId) {
        AdresseDTO adresseDTO = adresseService.getAdresseById(adresseId);
        return ResponseEntity.ok(adresseDTO);
    }

    @GetMapping("Adresses")
    public ResponseEntity<List<AdresseDTO>> getAllAdresses() {
        List<AdresseDTO> adresseDTOs = adresseService.getAllAdresses();
        return ResponseEntity.ok(adresseDTOs);
    }

    @PostMapping("Adresses")
    public ResponseEntity<AdresseDTO> createAdresse(@Valid @RequestBody AdresseDTO adresseDTO) {
        AdresseDTO createdAdresse = adresseService.createAdresse(adresseDTO);
        return new ResponseEntity<>(createdAdresse, HttpStatus.CREATED);
    }

    @PutMapping("Adresses/{adresseId}")
    public ResponseEntity<AdresseDTO> updateAdresse(
            @PathVariable @Positive(message = "L'ID doit être positif") Long adresseId,
            @Valid @RequestBody AdresseDTO adresseDTO) {
        AdresseDTO updatedAdresse = adresseService.updateAdresse(adresseId, adresseDTO);
        return ResponseEntity.ok(updatedAdresse);
    }

    @DeleteMapping("Adresses/{adresseId}")
    public ResponseEntity<Void> deleteAdresse(@PathVariable @Positive(message = "L'ID doit être positif") Long adresseId) {
        adresseService.deleteAdresse(adresseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("Adresses/{adresseId}/centreHospitaliers")
    public ResponseEntity<List<CentreHospitalierDTO>> getCentreHospitaliersByAdresse(
            @PathVariable @Positive(message = "L'ID doit être positif") Long adresseId) {
        List<CentreHospitalierDTO> centreHospitalierDTOs = adresseService.getCentreHospitaliersByAdresse(adresseId);
        return ResponseEntity.ok(centreHospitalierDTOs);
    }

    @GetMapping("/{adresseId}/medecins")
    public ResponseEntity<List<MedecinDTO>> getMedecinsByAdresse(
            @PathVariable @Positive(message = "L'ID doit être positif") Long adresseId) {
        List<MedecinDTO> medecinDTOs = adresseService.getMedecinsByAdresse(adresseId);
        return ResponseEntity.ok(medecinDTOs);
    }
}
