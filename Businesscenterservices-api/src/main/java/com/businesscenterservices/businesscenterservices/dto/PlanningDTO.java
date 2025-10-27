package com.businesscenterservices.businesscenterservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanningDTO {

    private Long id;
    private String commentaire;
    private boolean archived;
    private List<MissionsDTO> missionsList;
}
