package com.denisgasparoto.restapi.petstore.service;

import com.denisgasparoto.restapi.petstore.base.BaseEntity;

import java.util.List;

public interface CrudService<RequestDTO, ResponseDTO, Entity extends BaseEntity, Id> {

    ResponseDTO save(Id id, RequestDTO requestDTO);

    void deleteById(Id id);

    ResponseDTO findById(Id id);

    Entity findEntityById(Id id);

    List<ResponseDTO> findAll();

    Entity convertRequestDTOToEntity(Id id, RequestDTO requestDTO);

    ResponseDTO convertEntityToResponseDTO(Entity entity);
}
