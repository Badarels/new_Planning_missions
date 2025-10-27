package com.businesscenterservices.businesscenterservices.services;

import com.businesscenterservices.businesscenterservices.controllers.Exceptions.ResourceNotFoundException;
import com.businesscenterservices.businesscenterservices.dto.MedecinDTO;
import com.businesscenterservices.businesscenterservices.dto.SpecialiteDTO;
import com.businesscenterservices.businesscenterservices.entities.Medecin;
import com.businesscenterservices.businesscenterservices.entities.Specialite;
import com.businesscenterservices.businesscenterservices.repositories.SpecialiteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpecialiteServicesImp implements  SpecialiteServices {


    @Autowired
    private SpecialiteRepository specialiteRepository; // Supposons que vous avez une interface SpecialiteRepository

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SpecialiteDTO createSpecialite(SpecialiteDTO specialiteDTO) {
        Specialite specialite = modelMapper.map(specialiteDTO, Specialite.class);
        Specialite savedSpecialite = specialiteRepository.save(specialite);
        return modelMapper.map(savedSpecialite, SpecialiteDTO.class);
    }

    @Override
    public SpecialiteDTO getSpecialiteById(Long specialiteId) {
        Optional<Specialite> specialiteOptional = specialiteRepository.findById(specialiteId);
        return specialiteOptional.map(specialite -> modelMapper.map(specialite, SpecialiteDTO.class)).orElse(null);
    }

    @Override
    public List<SpecialiteDTO> getAllSpecialites() {
        List<Specialite> specialites = specialiteRepository.findAll();
        return specialites.stream()
                .map(specialite -> modelMapper.map(specialite, SpecialiteDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public SpecialiteDTO updateSpecialite(Long specialiteId, SpecialiteDTO specialiteDTO) {
        if (!specialiteRepository.existsById(specialiteId)) {
            // Gérer le cas où la spécialité n'existe pas
            return null;
        }

        Specialite specialiteToUpdate = modelMapper.map(specialiteDTO, Specialite.class);
        specialiteToUpdate.setId(specialiteId);

        Specialite updatedSpecialite = specialiteRepository.save(specialiteToUpdate);
        return modelMapper.map(updatedSpecialite, SpecialiteDTO.class);
    }

    @Override
    public void deleteSpecialite(Long specialiteId) {
        specialiteRepository.deleteById(specialiteId);
    }

    @Override
    public List<MedecinDTO> getMedecinsBySpecialite(Long specialiteId) {
        Optional<Specialite> specialiteOptional = specialiteRepository.findById(specialiteId);

        if (specialiteOptional.isPresent()) {
            Specialite specialite = specialiteOptional.get();
            List<Medecin> medecins = specialite.getMedecins();

            // Convertir les entités Medecin en DTOs MedecinDTO si nécessaire
            List<MedecinDTO> medecinDTOs = medecins.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return medecinDTOs;
        } else {
            throw new ResourceNotFoundException("Specialite not found with id: " + specialiteId);
        }
    }

    // Méthode utilitaire pour convertir un objet Medecin en MedecinDTO
    private MedecinDTO convertToDTO(Medecin medecin) {
        // Utilisez un outil de mappage comme ModelMapper ou effectuez la conversion manuellement
        // Exemple avec ModelMapper (assurez-vous d'ajouter la dépendance à votre projet)
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(medecin, MedecinDTO.class);
    }

}
