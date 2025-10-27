package com.businesscenterservices.businesscenterservices.services;

import com.businesscenterservices.businesscenterservices.dto.MedecinDTO;
import com.businesscenterservices.businesscenterservices.dto.MissionsDTO;

import java.util.List;

public interface MissionsServices {

    // Create a new Mission
    MissionsDTO createMission(MissionsDTO missionsDTO);
    // Retrieve Mission information by ID
    MissionsDTO getMissionById(long missionId);
    // Retrieve a list of all Missions
    List<MissionsDTO> getAllMissions();
    // Update Mission information
    MissionsDTO updateMission(long missionId, MissionsDTO missionsDTO);
    // Delete a Mission
    void deleteMission(long missionId);
    // Retrieve a list of Medecins associated with a Mission
    List<MedecinDTO> getMedecinsByMission(long missionId);
    // Archive a Mission
    MissionsDTO archiveMission(long id);
}
