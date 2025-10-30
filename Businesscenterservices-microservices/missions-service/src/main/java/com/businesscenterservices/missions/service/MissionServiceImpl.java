package com.businesscenterservices.missions.service;

import com.businesscenterservices.missions.dto.MissionDTO;
import com.businesscenterservices.missions.entity.Mission;
import com.businesscenterservices.missions.event.MissionNotificationEvent;
import com.businesscenterservices.missions.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MissionServiceImpl implements MissionService {

    private final MissionRepository missionRepository;
    private final KafkaTemplate<String, MissionNotificationEvent> missionEventKafkaTemplate;

    @Override
    public MissionDTO createMission(MissionDTO dto) {
        Mission mission = missionRepository.save(mapToEntity(new Mission(), dto));
        MissionDTO response = mapToDto(mission);
        publishEvent(response);
        return response;
    }

    @Override
    public MissionDTO updateMission(Long id, MissionDTO dto) {
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mission introuvable"));
        Mission updated = missionRepository.save(mapToEntity(mission, dto));
        MissionDTO response = mapToDto(updated);
        publishEvent(response);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public MissionDTO getMissionById(Long id) {
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mission introuvable"));
        return mapToDto(mission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MissionDTO> getAllMissions() {
        return missionRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMission(Long id) {
        if (!missionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mission introuvable");
        }
        missionRepository.deleteById(id);
    }

    @Override
    public MissionDTO archiveMission(Long id) {
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mission introuvable"));
        mission.setArchived(true);
        Mission saved = missionRepository.save(mission);
        MissionDTO response = mapToDto(saved);
        publishEvent(response);
        return response;
    }

    private Mission mapToEntity(Mission entity, MissionDTO dto) {
        entity.setDetail(dto.getDetailMission());
        entity.setDateDebut(dto.getDateMissions());
        entity.setDateFin(dto.getDateFinMissions());
        entity.setRemuneration(dto.getSalaireMission());
        entity.setStatut(dto.getStatutAnnonce());
        entity.setTypeHoraire(dto.getTypeHoraireMission());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setArchived(dto.isArchived());
        entity.setCentreHospitalierId(dto.getCentreHospitalierId());
        entity.setMedecinId(dto.getMedecinId());
        entity.setSpecialiteId(dto.getSpecialiteId());
        entity.setPlanningId(dto.getPlanningId());
        return entity;
    }

    private MissionDTO mapToDto(Mission mission) {
        MissionDTO dto = new MissionDTO();
        dto.setId(mission.getId());
        dto.setDetailMission(mission.getDetail());
        dto.setDateMissions(mission.getDateDebut());
        dto.setDateFinMissions(mission.getDateFin());
        dto.setSalaireMission(mission.getRemuneration());
        dto.setStatutAnnonce(mission.getStatut());
        dto.setTypeHoraireMission(mission.getTypeHoraire());
        dto.setStartTime(mission.getStartTime());
        dto.setEndTime(mission.getEndTime());
        dto.setArchived(mission.isArchived());
        dto.setCentreHospitalierId(mission.getCentreHospitalierId());
        dto.setMedecinId(mission.getMedecinId());
        dto.setSpecialiteId(mission.getSpecialiteId());
        dto.setPlanningId(mission.getPlanningId());
        return dto;
    }

    private void publishEvent(MissionDTO mission) {
        MissionNotificationEvent event = MissionNotificationEvent.builder()
                .missionId(mission.getId())
                .medecinId(mission.getMedecinId())
                .centreHospitalierId(mission.getCentreHospitalierId())
                .detailMission(mission.getDetailMission())
                .dateDebut(mission.getDateMissions())
                .dateFin(mission.getDateFinMissions())
                .startTime(mission.getStartTime())
                .endTime(mission.getEndTime())
                .remuneration(mission.getSalaireMission())
                .statut(mission.getStatutAnnonce())
                .typeHoraire(mission.getTypeHoraireMission())
                .archived(mission.isArchived())
                .build();
        missionEventKafkaTemplate.send("missions.events", String.valueOf(event.getMissionId()), event);
    }
}
