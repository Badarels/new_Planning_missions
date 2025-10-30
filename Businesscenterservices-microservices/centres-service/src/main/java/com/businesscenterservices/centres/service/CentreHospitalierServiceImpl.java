package com.businesscenterservices.centres.service;

import com.businesscenterservices.centres.dto.AdresseDTO;
import com.businesscenterservices.centres.dto.CentreHospitalierDTO;
import com.businesscenterservices.centres.entity.Adresse;
import com.businesscenterservices.centres.entity.CentreHospitalier;
import com.businesscenterservices.centres.repository.CentreHospitalierRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CentreHospitalierServiceImpl implements CentreHospitalierService {

    private final CentreHospitalierRepository centreHospitalierRepository;
    private final ModelMapper modelMapper;

    @Override
    public CentreHospitalierDTO create(CentreHospitalierDTO dto) {
        centreHospitalierRepository.findBySiret(dto.getSiret())
                .ifPresent(existing -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "SIRET déjà utilisé");
                });

        CentreHospitalier entity = mapToEntity(new CentreHospitalier(), dto);
        CentreHospitalier saved = centreHospitalierRepository.save(entity);
        return mapToDto(saved);
    }

    @Override
    public CentreHospitalierDTO update(Long id, CentreHospitalierDTO dto) {
        CentreHospitalier centreHospitalier = centreHospitalierRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Centre hospitalier introuvable"));

        if (!centreHospitalier.getSiret().equals(dto.getSiret())) {
            centreHospitalierRepository.findBySiret(dto.getSiret())
                    .ifPresent(existing -> {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "SIRET déjà utilisé");
                    });
        }

        CentreHospitalier updated = mapToEntity(centreHospitalier, dto);
        return mapToDto(centreHospitalierRepository.save(updated));
    }

    @Override
    @Transactional(readOnly = true)
    public CentreHospitalierDTO getById(Long id) {
        CentreHospitalier entity = centreHospitalierRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Centre hospitalier introuvable"));
        return mapToDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CentreHospitalierDTO> getAll() {
        return centreHospitalierRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!centreHospitalierRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Centre hospitalier introuvable");
        }
        centreHospitalierRepository.deleteById(id);
    }

    @Override
    public CentreHospitalierDTO archive(Long id) {
        CentreHospitalier entity = centreHospitalierRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Centre hospitalier introuvable"));
        entity.setArchived(true);
        return mapToDto(centreHospitalierRepository.save(entity));
    }

    private CentreHospitalier mapToEntity(CentreHospitalier entity, CentreHospitalierDTO dto) {
        entity.setSiret(dto.getSiret());
        entity.setNomCh(dto.getNomCh());
        entity.setEmailCh(dto.getEmailCh());
        entity.setTelephone(dto.getTelephone());
        entity.setArchived(dto.isArchived());

        if (dto.getAdresse() != null) {
            Adresse adresse = entity.getAdresse();
            if (adresse == null) {
                adresse = new Adresse();
                entity.setAdresse(adresse);
            }
            copyAdresse(adresse, dto.getAdresse());
        }

        return entity;
    }

    private void copyAdresse(Adresse adresse, AdresseDTO dto) {
        adresse.setNomRue(dto.getNomRue());
        adresse.setNumeroRue(dto.getNumeroRue());
        adresse.setComplement(dto.getComplement());
        adresse.setCodePostal(dto.getCodePostal());
        adresse.setVille(dto.getVille());
        adresse.setDepartement(dto.getDepartement());
        adresse.setRegion(dto.getRegion());
    }

    private CentreHospitalierDTO mapToDto(CentreHospitalier entity) {
        CentreHospitalierDTO dto = modelMapper.map(entity, CentreHospitalierDTO.class);
        if (entity.getAdresse() != null) {
            dto.setAdresse(modelMapper.map(entity.getAdresse(), AdresseDTO.class));
        }
        return dto;
    }
}
