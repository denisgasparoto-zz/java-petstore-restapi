package com.denisgasparoto.restapi.petstore.service;

import com.denisgasparoto.restapi.petstore.dto.request.PetRequestDTO;
import com.denisgasparoto.restapi.petstore.dto.response.PetResponseDTO;
import com.denisgasparoto.restapi.petstore.entity.Pet;

import java.util.List;

public interface PetService extends CrudService<PetRequestDTO, PetResponseDTO, Pet, Long> {

    List<PetResponseDTO> findBySpecie_Id(Long id);

    List<PetResponseDTO> findByCustomer_Id(Long id);
}
