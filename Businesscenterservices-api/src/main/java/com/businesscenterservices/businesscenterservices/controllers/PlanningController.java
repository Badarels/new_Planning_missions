package com.businesscenterservices.businesscenterservices.controllers;

import com.businesscenterservices.businesscenterservices.dto.MissionsDTO;
import com.businesscenterservices.businesscenterservices.dto.PlanningDTO;
import com.businesscenterservices.businesscenterservices.services.PlanningServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planning")
public class PlanningController {

    @Autowired
    private PlanningServicesImpl planningService;

    @PostMapping
    public ResponseEntity<PlanningDTO> createPlanning(@RequestBody PlanningDTO planningDTO) {
        PlanningDTO createdPlanning = planningService.createPlanning(planningDTO);
        return new ResponseEntity<>(createdPlanning, HttpStatus.CREATED);
    }

    @GetMapping("/{planningId}")
    public ResponseEntity<PlanningDTO> getPlanningById(@PathVariable long planningId) {
        PlanningDTO planning = planningService.getPlanningById(planningId);
        if (planning != null) {
            return ResponseEntity.ok(planning);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PlanningDTO>> getAllPlannings() {
        List<PlanningDTO> planningDTOs = planningService.getAllPlannings();
        return ResponseEntity.ok(planningDTOs);
    }

    @PutMapping("/{planningId}")
    public ResponseEntity<PlanningDTO> updatePlanning(@PathVariable long planningId, @RequestBody PlanningDTO planningDTO) {
        PlanningDTO updatedPlanning = planningService.updatePlanning(planningId, planningDTO);
        if (updatedPlanning != null) {
            return ResponseEntity.ok(updatedPlanning);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{planningId}")
    public ResponseEntity<Void> deletePlanning(@PathVariable long planningId) {
        planningService.deletePlanning(planningId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{planningId}/missions")
    public ResponseEntity<List<MissionsDTO>> getMissionsByPlanning(@PathVariable long planningId) {
        List<MissionsDTO> missionsDTOs = planningService.getMissionsByPlanning(planningId);
        return ResponseEntity.ok(missionsDTOs);
    }

    @PutMapping("/{planningId}/archive")
    public ResponseEntity<PlanningDTO> archivePlanning(@PathVariable long planningId) {
        PlanningDTO archivedPlanning = planningService.archivePlanning(planningId);
        if (archivedPlanning != null) {
            return ResponseEntity.ok(archivedPlanning);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
