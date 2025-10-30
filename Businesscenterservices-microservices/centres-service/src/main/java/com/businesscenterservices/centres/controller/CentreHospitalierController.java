package com.businesscenterservices.centres.controller;

import com.businesscenterservices.centres.dto.CentreHospitalierDTO;
import com.businesscenterservices.centres.service.CentreHospitalierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/centres")
@RequiredArgsConstructor
public class CentreHospitalierController {

    private final CentreHospitalierService centreHospitalierService;

    @GetMapping
    public ResponseEntity<List<CentreHospitalierDTO>> getCentres() {
        return ResponseEntity.ok(centreHospitalierService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CentreHospitalierDTO> getCentre(@PathVariable Long id) {
        return ResponseEntity.ok(centreHospitalierService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CentreHospitalierDTO> createCentre(@Valid @RequestBody CentreHospitalierDTO dto) {
        CentreHospitalierDTO created = centreHospitalierService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CentreHospitalierDTO> updateCentre(@PathVariable Long id,
                                                             @Valid @RequestBody CentreHospitalierDTO dto) {
        return ResponseEntity.ok(centreHospitalierService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCentre(@PathVariable Long id) {
        centreHospitalierService.delete(id);
    }

    @PutMapping("/{id}/archive")
    public ResponseEntity<CentreHospitalierDTO> archiveCentre(@PathVariable Long id) {
        return ResponseEntity.ok(centreHospitalierService.archive(id));
    }
}
