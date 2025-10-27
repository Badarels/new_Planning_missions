package com.businesscenterservices.businesscenterservices.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualificationDTO {

    private Long id;
    private String libeleQualification;
    private Long medecinId;

}