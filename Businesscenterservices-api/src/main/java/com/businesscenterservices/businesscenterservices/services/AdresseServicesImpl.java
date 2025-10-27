package com.businesscenterservices.businesscenterservices.services;

import com.businesscenterservices.businesscenterservices.exceptions.ResourceNotFoundException;
import com.businesscenterservices.businesscenterservices.dto.AdresseDTO;
import com.businesscenterservices.businesscenterservices.dto.CentreHospitalierDTO;
import com.businesscenterservices.businesscenterservices.dto.MedecinDTO;
import com.businesscenterservices.businesscenterservices.entities.Adresse;
import com.businesscenterservices.businesscenterservices.repositories.AdresseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdresseServicesImpl implements AdresseServices {
    private final AdresseRepository adresseRepository;
    private final ModelMapper modelMapper;

    @Override
    public AdresseDTO createAdresse(AdresseDTO adresseDTO) {
        Adresse adresse = modelMapper.map(adresseDTO, Adresse.class);
        Adresse savedAdresse = adresseRepository.save(adresse);
        return modelMapper.map(savedAdresse, AdresseDTO.class);
    }

    @Override
    public AdresseDTO getAdresseById(Long adresseId) {
        Adresse adresse = adresseRepository.findById(adresseId)
            .orElseThrow(() -> new ResourceNotFoundException("Adresse", "id", adresseId));
        return modelMapper.map(adresse, AdresseDTO.class);
    }

    @Override
    public List<AdresseDTO> getAllAdresses() {
        List<Adresse> adresses = adresseRepository.findAll();
        return adresses.stream()
                .map(adresse -> modelMapper.map(adresse, AdresseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AdresseDTO updateAdresse(Long adresseId, AdresseDTO adresseDTO) {
        Adresse adresse = adresseRepository.findById(adresseId)
            .orElseThrow(() -> new ResourceNotFoundException("Adresse", "id", adresseId));
        
        modelMapper.map(adresseDTO, adresse);
        adresse.setId(adresseId);
        Adresse updatedAdresse = adresseRepository.save(adresse);
        return modelMapper.map(updatedAdresse, AdresseDTO.class);
    }

    @Override
    public void deleteAdresse(Long adresseId) {
        if (!adresseRepository.existsById(adresseId)) {
            throw new ResourceNotFoundException("Adresse", "id", adresseId);
        }
        adresseRepository.deleteById(adresseId);
    }

    @Override
    public List<CentreHospitalierDTO> getCentreHospitaliersByAdresse(Long adresseId) {
        Optional<Adresse> adresseOptional = adresseRepository.findById(adresseId);
        if (adresseOptional.isPresent()) {
            Adresse adresse = adresseOptional.get();
            return adresse.getCentreHospitalier().stream()
                    .map(centreHospitalier -> modelMapper.map(centreHospitalier, CentreHospitalierDTO.class))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public List<MedecinDTO> getMedecinsByAdresse(Long adresseId) {
        Optional<Adresse> adresseOptional = adresseRepository.findById(adresseId);
        if (adresseOptional.isPresent()) {
            Adresse adresse = adresseOptional.get();
            return adresse.getMedecins().stream()
                    .map(medecin -> modelMapper.map(medecin, MedecinDTO.class))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
