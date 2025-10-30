package com.businesscenterservices.missions.service;

import com.businesscenterservices.missions.dto.MissionDTO;

import java.util.List;

public interface MissionService {

    MissionDTO createMission(MissionDTO dto);

    MissionDTO updateMission(Long id, MissionDTO dto);

    MissionDTO getMissionById(Long id);

    List<MissionDTO> getAllMissions();

    void deleteMission(Long id);

    MissionDTO archiveMission(Long id);
}
