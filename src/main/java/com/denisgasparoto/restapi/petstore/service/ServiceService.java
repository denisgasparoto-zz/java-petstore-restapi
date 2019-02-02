package com.denisgasparoto.restapi.petstore.service;

import com.denisgasparoto.restapi.petstore.dto.request.ServiceRequestDTO;
import com.denisgasparoto.restapi.petstore.dto.response.ServiceResponseDTO;
import com.denisgasparoto.restapi.petstore.entity.Service;

import java.time.LocalDate;
import java.util.List;

public interface ServiceService extends CrudService<ServiceRequestDTO, ServiceResponseDTO, Service, Long> {

    List<ServiceResponseDTO> findByPet_Id(Long idPet);

    List<ServiceResponseDTO> findByDateHourBetween(LocalDate initialDate, LocalDate finalDate);
}
