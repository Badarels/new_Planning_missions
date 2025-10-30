package com.businesscenterservices.medecins.controller;

import com.businesscenterservices.medecins.dto.MedecinDTO;
import com.businesscenterservices.medecins.dto.SpecialiteDTO;
import com.businesscenterservices.medecins.service.MedecinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medecins")
@RequiredArgsConstructor
public class MedecinController {

    private final MedecinService medecinService;

    @GetMapping
    public ResponseEntity<List<MedecinDTO>> getMedecins(@RequestParam(value = "ville", required = false) String ville) {
        List<MedecinDTO> result = (ville == null || ville.isBlank())
                ? medecinService.getAllMedecins()
                : medecinService.getMedecinsByVille(ville);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedecinDTO> getMedecin(@PathVariable Long id) {
        MedecinDTO medecin = medecinService.getMedecinById(id);
        return ResponseEntity.ok(medecin);
    }

    @PostMapping
    public ResponseEntity<MedecinDTO> createMedecin(@Valid @RequestBody MedecinDTO medecinDTO) {
        MedecinDTO created = medecinService.createMedecin(medecinDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedecinDTO> updateMedecin(@PathVariable Long id, @Valid @RequestBody MedecinDTO medecinDTO) {
        MedecinDTO updated = medecinService.updateMedecin(id, medecinDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMedecin(@PathVariable Long id) {
        medecinService.deleteMedecin(id);
    }

    @GetMapping("/{id}/specialites")
    public ResponseEntity<List<SpecialiteDTO>> getSpecialites(@PathVariable Long id) {
        return ResponseEntity.ok(medecinService.getSpecialitesByMedecin(id));
    }
}
