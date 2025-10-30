package com.businesscenterservices.missions.controller;

import com.businesscenterservices.missions.dto.MissionDTO;
import com.businesscenterservices.missions.service.MissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @GetMapping
    public ResponseEntity<List<MissionDTO>> getMissions() {
        return ResponseEntity.ok(missionService.getAllMissions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissionDTO> getMission(@PathVariable Long id) {
        return ResponseEntity.ok(missionService.getMissionById(id));
    }

    @PostMapping
    public ResponseEntity<MissionDTO> createMission(@Valid @RequestBody MissionDTO dto) {
        MissionDTO created = missionService.createMission(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MissionDTO> updateMission(@PathVariable Long id,
                                                    @Valid @RequestBody MissionDTO dto) {
        return ResponseEntity.ok(missionService.updateMission(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMission(@PathVariable Long id) {
        missionService.deleteMission(id);
    }

    @PutMapping("/{id}/archive")
    public ResponseEntity<MissionDTO> archiveMission(@PathVariable Long id) {
        return ResponseEntity.ok(missionService.archiveMission(id));
    }
}
