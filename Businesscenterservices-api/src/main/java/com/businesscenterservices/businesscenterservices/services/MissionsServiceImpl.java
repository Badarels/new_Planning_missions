package com.businesscenterservices.businesscenterservices.services;

import com.businesscenterservices.businesscenterservices.controllers.Exceptions.EntityNotFoundException;
import com.businesscenterservices.businesscenterservices.dto.MissionsDTO;
import com.businesscenterservices.businesscenterservices.dto.MedecinDTO;
import com.businesscenterservices.businesscenterservices.entities.CentreHospitalier;
import com.businesscenterservices.businesscenterservices.entities.Medecin;
import com.businesscenterservices.businesscenterservices.entities.Missions;
import com.businesscenterservices.businesscenterservices.entities.Specialite;
import com.businesscenterservices.businesscenterservices.repositories.MissionsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MissionsServiceImpl implements MissionsServices {

    @Autowired
    private MissionsRepository missionsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MissionsDTO createMission(MissionsDTO missionsDTO) {
        CentreHospitalier centreHospitalier = modelMapper.map(missionsDTO.getCentreHospitalier(), CentreHospitalier.class);
        // En supposant que getMedecin() renvoie un seul Medecin
        Medecin medecin = modelMapper.map(missionsDTO.getMedecin(), Medecin.class);
        Specialite specialite= modelMapper.map(missionsDTO.getSpecialite(), Specialite.class);
        Missions missionToCreate = new Missions();
        missionToCreate.setDetailMission(missionsDTO.getDetailMission());
        missionToCreate.setDateMissions(missionsDTO.getDateMissions());
        missionToCreate.setDateFinMissions(missionsDTO.getDateFinMissions());
        missionToCreate.setEndTime(missionsDTO.getEndTime());
        missionToCreate.setStartTime(missionsDTO.getStartTime());
        missionToCreate.setSalaireMission(missionsDTO.getSalaireMission());
        missionToCreate.setStatutAnnonce(missionsDTO.getStatutAnnonce());
        missionToCreate.setTypeHoraireMission(missionsDTO.getTypeHoraireMission());
        missionToCreate.setSpecialite(specialite);
        missionToCreate.setCentreHospitalier(centreHospitalier);
        missionToCreate.setMedecin(medecin);

        Missions createdMission = missionsRepository.save(missionToCreate);
        return modelMapper.map(createdMission, MissionsDTO.class);
    }

    @Override
    public MissionsDTO getMissionById(long missionId) {
        Optional<Missions> missionOptional = missionsRepository.findById(missionId);
        return missionOptional.map(mission -> modelMapper.map(mission, MissionsDTO.class)).orElse(null);
    }

    @Override
    public List<MissionsDTO> getAllMissions() {
        List<Missions> missions = missionsRepository.findAll();
        return missions.stream()
                .map(mission -> modelMapper.map(mission, MissionsDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MissionsDTO updateMission(long missionId, MissionsDTO missionsDTO) {
        Optional<Missions> missionOptional = missionsRepository.findById(missionId);
        CentreHospitalier centreHospitalier = modelMapper.map(missionsDTO.getCentreHospitalier(), CentreHospitalier.class);
        // En supposant que getMedecin() renvoie un seul Medecin
        Medecin medecin = modelMapper.map(missionsDTO.getMedecin(), Medecin.class);
        Specialite specialite= modelMapper.map(missionsDTO.getSpecialite(), Specialite.class);
        if (missionOptional.isPresent()){
            Missions missionToUpdate = missionOptional.get();
            // Mettez à jour les propriétés de missionToUpdate avec les valeurs de missionsDTO
            missionToUpdate.setDetailMission(missionsDTO.getDetailMission());
            missionToUpdate.setDateMissions(missionsDTO.getDateMissions());
            missionToUpdate.setDateFinMissions(missionsDTO.getDateFinMissions());
            missionToUpdate.setEndTime(missionsDTO.getEndTime());
            missionToUpdate.setStartTime(missionsDTO.getStartTime());
            missionToUpdate.setSalaireMission(missionsDTO.getSalaireMission());
            missionToUpdate.setStatutAnnonce(missionsDTO.getStatutAnnonce());
            missionToUpdate.setTypeHoraireMission(missionsDTO.getTypeHoraireMission());
            missionToUpdate.setSpecialite(specialite);
            missionToUpdate.setCentreHospitalier(centreHospitalier);
            missionToUpdate.setMedecin(medecin);
            // ... mettez à jour d'autres propriétés selon vos besoins
            Missions updatedMission = missionsRepository.save(missionToUpdate);
            return modelMapper.map(updatedMission, MissionsDTO.class);
        } else {
            throw new EntityNotFoundException("Mission avec l'ID " + missionId + " n'existe pas.");
        }
    }

    @Override
    public MissionsDTO archiveMission(long missionId) {
        Optional<Missions> optionalMission = missionsRepository.findById(missionId);
        if (optionalMission.isPresent()) {
            Missions mission = optionalMission.get();
            mission.setArchived(true); // Update only the archived field
            missionsRepository.save(mission);
            return modelMapper.map(mission, MissionsDTO.class);
        }
        return null;
    }

    @Override
    public void deleteMission(long missionId) {
        missionsRepository.deleteById(missionId);
    }

    @Override
    public List<MedecinDTO> getMedecinsByMission(long missionId) {
        Optional<Missions> missionOptional = missionsRepository.findById(missionId);
        if (missionOptional.isPresent()) {
            Missions mission = missionOptional.get();
            // Utilisez directement map pour mapper le Medecin sans utiliser stream
            return mission.getMedecin() != null ?
                    Collections.singletonList(modelMapper.map(mission.getMedecin(), MedecinDTO.class)) :
                    Collections.emptyList();
        }
        return Collections.emptyList();
    }
}
    