package com.denisgasparoto.restapi.petstore.service.impl;

import com.denisgasparoto.restapi.petstore.business.specie.SpecieBusiness;
import com.denisgasparoto.restapi.petstore.dto.request.SpecieRequestDTO;
import com.denisgasparoto.restapi.petstore.dto.response.SpecieResponseDTO;
import com.denisgasparoto.restapi.petstore.entity.Specie;
import com.denisgasparoto.restapi.petstore.service.SpecieService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecieServiceImpl implements SpecieService {

    private SpecieBusiness specieBusiness;

    public SpecieServiceImpl(SpecieBusiness specieBusiness) {
        this.specieBusiness = specieBusiness;
    }

    @Override
    public SpecieResponseDTO save(Long specieId, SpecieRequestDTO requestDTO) {
        Specie specie = convertRequestDTOToEntity(specieId, requestDTO);
        specie = specieBusiness.save(specie);

        return convertEntityToResponseDTO(specie);
    }

    @Override
    public List<SpecieResponseDTO> findAll() {
        List<Specie> species = specieBusiness.findAll();
        List<SpecieResponseDTO> response = new ArrayList<>();
        species.forEach(specie -> response.add(convertEntityToResponseDTO(specie)));

        return response;
    }

    @Override
    public SpecieResponseDTO findById(Long specieId) {
        Specie specie = specieBusiness.findById(specieId);

        return convertEntityToResponseDTO(specie);
    }

    @Override
    public Specie findEntityById(Long specieId) {
        return specieBusiness.findById(specieId);
    }

    @Override
    public void deleteById(Long specieId) {
        specieBusiness.deleteById(specieId);
    }

    @Override
    public Specie convertRequestDTOToEntity(Long specieId, SpecieRequestDTO requestDTO) {
        Specie specie = specieId == null ? new Specie() : specieBusiness.findById(specieId);
        specie.setName(requestDTO.getName());

        return specie;
    }


    @Override
    public SpecieResponseDTO convertEntityToResponseDTO(Specie entity) {
        SpecieResponseDTO specieResponseDTO = new SpecieResponseDTO();
        specieResponseDTO.setName(entity.getName());
        specieResponseDTO.setId(entity.getId());

        return specieResponseDTO;
    }
}