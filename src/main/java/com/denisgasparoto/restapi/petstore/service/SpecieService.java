package com.denisgasparoto.restapi.petstore.service;

import com.denisgasparoto.restapi.petstore.dto.request.SpecieRequestDTO;
import com.denisgasparoto.restapi.petstore.dto.response.SpecieResponseDTO;
import com.denisgasparoto.restapi.petstore.entity.Specie;

public interface SpecieService extends CrudService<SpecieRequestDTO, SpecieResponseDTO, Specie, Long> {
}
