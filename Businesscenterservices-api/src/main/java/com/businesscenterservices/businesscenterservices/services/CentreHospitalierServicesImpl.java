package com.businesscenterservices.businesscenterservices.services;


import com.businesscenterservices.businesscenterservices.controllers.Exceptions.EntityNotFoundException;
import com.businesscenterservices.businesscenterservices.dto.CentreHospitalierDTO;
import com.businesscenterservices.businesscenterservices.dto.MedecinDTO;
import com.businesscenterservices.businesscenterservices.entities.CentreHospitalier;
import com.businesscenterservices.businesscenterservices.repositories.CentrerHospitalierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CentreHospitalierServicesImpl implements CentreHospitalierServices {

    @Autowired
    private CentrerHospitalierRepository centreHospitalierRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CentreHospitalierDTO createCentreHospitalier(CentreHospitalierDTO centreHospitalierDTO) {
        CentreHospitalier centreHospitalier = modelMapper.map(centreHospitalierDTO, CentreHospitalier.class);
        CentreHospitalier savedCentreHospitalier = centreHospitalierRepository.save(centreHospitalier);
        return modelMapper.map(savedCentreHospitalier, CentreHospitalierDTO.class);
    }

        @Override
        public CentreHospitalierDTO getCentreHospitalierById(Long centreHospitalierId) {
            Optional<CentreHospitalier> centreHospitalierOptional = centreHospitalierRepository.findById(centreHospitalierId);
            return centreHospitalierOptional.map(centreHospitalier -> modelMapper.map(centreHospitalier, CentreHospitalierDTO.class)).orElse(null);
        }

    @Override
    public List<CentreHospitalierDTO> getAllCentreHospitaliers() {
        List<CentreHospitalier> centreHospitaliers = centreHospitalierRepository.findAll();
        return centreHospitaliers.stream()
                .map(centreHospitalier -> modelMapper.map(centreHospitalier, CentreHospitalierDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CentreHospitalierDTO updateCentreHospitalier(Long centreHospitalierId, CentreHospitalierDTO centreHospitalierDTO) {
        if (!centreHospitalierRepository.existsById(centreHospitalierId)) {
            throw new EntityNotFoundException("Centre Hospitalier avec l'ID " + centreHospitalierId + " n'existe pas.");
        }

        CentreHospitalier centreHospitalierToUpdate = modelMapper.map(centreHospitalierDTO, CentreHospitalier.class);
        centreHospitalierToUpdate.setId(centreHospitalierId);

        CentreHospitalier updatedCentreHospitalier = centreHospitalierRepository.save(centreHospitalierToUpdate);
        return modelMapper.map(updatedCentreHospitalier, CentreHospitalierDTO.class);
    }

    @Override
    public CentreHospitalierDTO archiveCentreHospitalier(Long centreHospitalierId) {
        Optional<CentreHospitalier> optionalCentreHospitalier = centreHospitalierRepository.findById(centreHospitalierId);
        if (optionalCentreHospitalier.isPresent()) {
            CentreHospitalier centreHospitalier = optionalCentreHospitalier.get();
            centreHospitalier.setArchived(true); // Mettre à jour seulement le champ archived
            centreHospitalierRepository.save(centreHospitalier);
            return convertToDTO(centreHospitalier);
        }
        return null;
    }

    private CentreHospitalierDTO convertToDTO(CentreHospitalier centreHospitalier) {
        return modelMapper.map(centreHospitalier, CentreHospitalierDTO.class);
    }

    @Override
    public void deleteCentreHospitalier(Long centreHospitalierId) {
        centreHospitalierRepository.deleteById(centreHospitalierId);
    }

    @Override
    public List<MedecinDTO> getMedecinsByCentreHospitalier(Long centreHospitalierId) {
        Optional<CentreHospitalier> centreHospitalierOptional = centreHospitalierRepository.findById(centreHospitalierId);
        if (centreHospitalierOptional.isPresent()) {
            CentreHospitalier centreHospitalier = centreHospitalierOptional.get();
            return centreHospitalier.getMedecins().stream()
                    .map(medecin -> modelMapper.map(medecin, MedecinDTO.class))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}


