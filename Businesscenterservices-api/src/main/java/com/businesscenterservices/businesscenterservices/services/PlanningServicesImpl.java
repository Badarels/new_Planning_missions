package com.businesscenterservices.businesscenterservices.services;
import com.businesscenterservices.businesscenterservices.controllers.Exceptions.EntityNotFoundException;
import com.businesscenterservices.businesscenterservices.dto.MissionsDTO;
import com.businesscenterservices.businesscenterservices.dto.PlanningDTO;
import com.businesscenterservices.businesscenterservices.entities.Planning;
import com.businesscenterservices.businesscenterservices.repositories.PlanningRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlanningServicesImpl implements PlanningServices {

    @Autowired
    private PlanningRepository planningRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PlanningDTO createPlanning(PlanningDTO planningDTO) {
        Planning planningToCreate = modelMapper.map(planningDTO, Planning.class);
        Planning createdPlanning = planningRepository.save(planningToCreate);
        return modelMapper.map(createdPlanning, PlanningDTO.class);
    }

    @Override
    public PlanningDTO getPlanningById(long planningId) {
        Optional<Planning> planningOptional = planningRepository.findById(planningId);
        return planningOptional.map(planning -> modelMapper.map(planning, PlanningDTO.class)).orElse(null);
    }

    @Override
    public List<PlanningDTO> getAllPlannings() {
        List<Planning> plannings = planningRepository.findAll();
        return plannings.stream()
                .map(planning -> modelMapper.map(planning, PlanningDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PlanningDTO updatePlanning(long planningId, PlanningDTO planningDTO) {
        Optional<Planning> planningOptional = planningRepository.findById(planningId);
        if (planningOptional.isPresent()){
            Planning planningToUpdate = planningOptional.get();
            modelMapper.map(planningDTO, planningToUpdate); // Update properties using modelMapper
            Planning updatedPlanning = planningRepository.save(planningToUpdate);
            return modelMapper.map(updatedPlanning, PlanningDTO.class);
        } else {
            throw new EntityNotFoundException("Planning with ID " + planningId + " does not exist.");
        }
    }

    @Override
    public PlanningDTO archivePlanning(long id) {
        Optional<Planning> optionalPlanning = planningRepository.findById(id);
        if (optionalPlanning.isPresent()) {
            Planning planning = optionalPlanning.get();
            planning.setArchived(true); // Update only the archived field
            planningRepository.save(planning);
            return modelMapper.map(planning, PlanningDTO.class);
        }
        return null;
    }

    @Override
    public void deletePlanning(long planningId) {
        planningRepository.deleteById(planningId);
    }

    @Override
    public List<MissionsDTO> getMissionsByPlanning(long planningId) {
        Optional<Planning> planningOptional = planningRepository.findById(planningId);
        if (planningOptional.isPresent()) {
            Planning planning = planningOptional.get();
            return planning.getMissions() != null ?
                    planning.getMissions().stream()
                            .map(mission -> modelMapper.map(mission, MissionsDTO.class))
                            .collect(Collectors.toList()) :
                    Collections.emptyList();
        }
        return Collections.emptyList();
    }
}