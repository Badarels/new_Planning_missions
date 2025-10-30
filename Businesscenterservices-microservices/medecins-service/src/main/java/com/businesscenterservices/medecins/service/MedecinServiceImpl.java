package com.businesscenterservices.medecins.service;

import com.businesscenterservices.medecins.dto.AdresseDTO;
import com.businesscenterservices.medecins.dto.MedecinDTO;
import com.businesscenterservices.medecins.dto.SpecialiteDTO;
import com.businesscenterservices.medecins.entity.Adresse;
import com.businesscenterservices.medecins.entity.Medecin;
import com.businesscenterservices.medecins.entity.Specialite;
import com.businesscenterservices.medecins.repository.MedecinRepository;
import com.businesscenterservices.medecins.repository.SpecialiteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MedecinServiceImpl implements MedecinService {

    private final MedecinRepository medecinRepository;
    private final SpecialiteRepository specialiteRepository;
    private final ModelMapper modelMapper;

    @Override
    public MedecinDTO createMedecin(MedecinDTO medecinDTO) {
        Medecin medecin = mapToEntity(medecinDTO);
        Medecin saved = medecinRepository.save(medecin);
        return mapToDto(saved);
    }

    @Override
    public MedecinDTO updateMedecin(Long id, MedecinDTO medecinDTO) {
        Medecin existing = medecinRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médecin introuvable"));

        updateEntity(existing, medecinDTO);
        Medecin updated = medecinRepository.save(existing);
        return mapToDto(updated);
    }

    @Override
    public MedecinDTO getMedecinById(Long id) {
        Medecin medecin = medecinRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médecin introuvable"));
        return mapToDto(medecin);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<MedecinDTO> getAllMedecins() {
        return medecinRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMedecin(Long id) {
        if (!medecinRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Médecin introuvable");
        }
        medecinRepository.deleteById(id);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<MedecinDTO> getMedecinsByVille(String ville) {
        return medecinRepository.findByAdresseVille(ville).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<SpecialiteDTO> getSpecialitesByMedecin(Long id) {
        if (!medecinRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Médecin introuvable");
        }
        return medecinRepository.findSpecialitesByMedecinId(id).stream()
                .map(specialite -> modelMapper.map(specialite, SpecialiteDTO.class))
                .collect(Collectors.toList());
    }

    private Medecin mapToEntity(MedecinDTO medecinDTO) {
        Medecin medecin = new Medecin();
        updateEntity(medecin, medecinDTO);
        return medecin;
    }

    private void updateEntity(Medecin medecin, MedecinDTO medecinDTO) {
        medecin.setNomMedecin(medecinDTO.getNomMedecin());
        medecin.setPrenomMedecin(medecinDTO.getPrenomMedecin());
        medecin.setEmailMedecin(medecinDTO.getEmailMedecin());
        medecin.setSexeMedecin(medecinDTO.getSexeMedecin());
        medecin.setDateDeNaissanceMedecin(medecinDTO.getDateDeNaissanceMedecin());
        medecin.setDateEcheance(medecinDTO.getDateEcheance());
        medecin.setLieuDeNaissanceMedecin(medecinDTO.getLieuDeNaissanceMedecin());
        medecin.setNumeroSecuriteSocialeMedecin(medecinDTO.getNumeroSecuriteSocialeMedecin());
        medecin.setTelephoneMedecin1(medecinDTO.getTelephoneMedecin1());
        medecin.setTelephoneMedecin2(medecinDTO.getTelephoneMedecin2());
        medecin.setStatutMedecin(medecinDTO.getStatutMedecin());
        medecin.setNumeroRpps(medecinDTO.getNumeroRpps());
        medecin.setQualifications(medecinDTO.getQualifications());
        medecin.setInscriptionAlOrdre(medecinDTO.getInscriptionAlOrdre());
        medecin.setCentreHospitalierId(medecinDTO.getCentreHospitalierId());

        if (medecinDTO.getAdresse() != null) {
            if (medecin.getAdresse() == null) {
                medecin.setAdresse(new Adresse());
            }
            copyAdresse(medecin.getAdresse(), medecinDTO.getAdresse());
        }

        List<Specialite> specialites = resolveSpecialites(medecinDTO.getSpecialites());
        medecin.setSpecialites(specialites);
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

    private List<Specialite> resolveSpecialites(List<SpecialiteDTO> specialiteDTOs) {
        if (specialiteDTOs == null || specialiteDTOs.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> ids = specialiteDTOs.stream()
                .map(SpecialiteDTO::getId)
                .collect(Collectors.toList());

        List<Specialite> specialites = specialiteRepository.findAllById(ids);
        if (specialites.size() != ids.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Certaines spécialités sont introuvables");
        }
        return specialites;
    }

    private MedecinDTO mapToDto(Medecin medecin) {
        MedecinDTO dto = new MedecinDTO();
        dto.setId(medecin.getId());
        dto.setNomMedecin(medecin.getNomMedecin());
        dto.setPrenomMedecin(medecin.getPrenomMedecin());
        dto.setEmailMedecin(medecin.getEmailMedecin());
        dto.setSexeMedecin(medecin.getSexeMedecin());
        dto.setDateDeNaissanceMedecin(medecin.getDateDeNaissanceMedecin());
        dto.setDateEcheance(medecin.getDateEcheance());
        dto.setLieuDeNaissanceMedecin(medecin.getLieuDeNaissanceMedecin());
        dto.setNumeroSecuriteSocialeMedecin(medecin.getNumeroSecuriteSocialeMedecin());
        dto.setTelephoneMedecin1(medecin.getTelephoneMedecin1());
        dto.setTelephoneMedecin2(medecin.getTelephoneMedecin2());
        dto.setStatutMedecin(medecin.getStatutMedecin());
        dto.setNumeroRpps(medecin.getNumeroRpps());
        dto.setQualifications(medecin.getQualifications());
        dto.setInscriptionAlOrdre(medecin.getInscriptionAlOrdre());
        dto.setCentreHospitalierId(medecin.getCentreHospitalierId());

        if (medecin.getAdresse() != null) {
            AdresseDTO adresseDTO = modelMapper.map(medecin.getAdresse(), AdresseDTO.class);
            dto.setAdresse(adresseDTO);
        }

        dto.setSpecialites(medecin.getSpecialites().stream()
                .map(s -> modelMapper.map(s, SpecialiteDTO.class))
                .collect(Collectors.toList()));

        return dto;
    }
}
