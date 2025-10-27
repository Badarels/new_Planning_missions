package com.businesscenterservices.businesscenterservices.controllers;

import com.businesscenterservices.businesscenterservices.dto.MedecinDTO;
import com.businesscenterservices.businesscenterservices.dto.MissionsDTO;
import com.businesscenterservices.businesscenterservices.services.MissionsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/missions")
public class MissionsController {

    @Autowired
    private MissionsServiceImpl missionsService;

    @PostMapping
    public ResponseEntity<MissionsDTO> createMission(@RequestBody MissionsDTO missionsDTO) {
        MissionsDTO createdMission = missionsService.createMission(missionsDTO);
        return new ResponseEntity<>(createdMission, HttpStatus.CREATED);
    }

    @GetMapping("/{missionId}")
    public ResponseEntity<MissionsDTO> getMissionById(@PathVariable long missionId) {
        MissionsDTO mission = missionsService.getMissionById(missionId);
        if (mission != null) {
            return ResponseEntity.ok(mission);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<MissionsDTO>> getAllMissions() {
        List<MissionsDTO> missionsDTOs = missionsService.getAllMissions();
        return ResponseEntity.ok(missionsDTOs);
    }

    @PutMapping("/{missionId}")
    public ResponseEntity<MissionsDTO> updateMission(@PathVariable long missionId, @RequestBody MissionsDTO missionsDTO) {
        MissionsDTO updatedMission = missionsService.updateMission(missionId, missionsDTO);
        if (updatedMission != null) {
            return ResponseEntity.ok(updatedMission);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{missionId}")
    public ResponseEntity<Void> deleteMission(@PathVariable long missionId) {
        missionsService.deleteMission(missionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{missionId}/medecins")
    public ResponseEntity<List<MedecinDTO>> getMedecinsByMission(@PathVariable long missionId) {
        List<MedecinDTO> medecinDTOs = missionsService.getMedecinsByMission(missionId);
        return ResponseEntity.ok(medecinDTOs);
    }

    @PutMapping("/{missionId}/archive")
    public ResponseEntity<MissionsDTO> archiveMission(@PathVariable long missionId) {
        MissionsDTO archivedMission = missionsService.archiveMission(missionId);
        if (archivedMission != null) {
            return ResponseEntity.ok(archivedMission);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
