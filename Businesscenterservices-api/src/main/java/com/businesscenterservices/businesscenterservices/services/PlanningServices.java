package com.businesscenterservices.businesscenterservices.services;


import com.businesscenterservices.businesscenterservices.dto.MissionsDTO;
import com.businesscenterservices.businesscenterservices.dto.PlanningDTO;

import java.util.List;

public interface PlanningServices {
    // Create a new Planning
    PlanningDTO createPlanning(PlanningDTO planningDTO);
    // Retrieve Planning information by ID
    PlanningDTO getPlanningById(long planningId);
    // Retrieve a list of all Plannings
    List<PlanningDTO> getAllPlannings();
    // Update Planning information
    PlanningDTO updatePlanning(long planningId, PlanningDTO planningDTO);
    // Delete a Planning
    void deletePlanning(long planningId);
    // Retrieve a list of Missions associated with a Planning
    List<MissionsDTO> getMissionsByPlanning(long planningId);
    // Archive a Planning
    PlanningDTO archivePlanning(long id);

}
