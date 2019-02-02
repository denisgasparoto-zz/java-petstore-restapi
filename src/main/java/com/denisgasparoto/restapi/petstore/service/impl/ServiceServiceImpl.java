package com.denisgasparoto.restapi.petstore.service.impl;

import com.denisgasparoto.restapi.petstore.business.service.ServiceBusiness;
import com.denisgasparoto.restapi.petstore.dto.request.ServiceRequestDTO;
import com.denisgasparoto.restapi.petstore.dto.response.ServiceResponseDTO;
import com.denisgasparoto.restapi.petstore.entity.Service;
import com.denisgasparoto.restapi.petstore.entity.ServiceType;
import com.denisgasparoto.restapi.petstore.service.PetService;
import com.denisgasparoto.restapi.petstore.service.ServiceService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    private ServiceBusiness businessService;
    private PetService petService;

    public ServiceServiceImpl(ServiceBusiness businessService,
                              PetService petService) {
        this.businessService = businessService;
        this.petService = petService;
    }

    @Override
    public ServiceResponseDTO save(Long serviceId, ServiceRequestDTO requestDTO) {
        Service service = convertRequestDTOToEntity(serviceId, requestDTO);
        service = businessService.save(service);

        return convertEntityToResponseDTO(service);
    }

    @Override
    public List<ServiceResponseDTO> findAll() {
        List<Service> services = businessService.findAll();
        List<ServiceResponseDTO> response = new ArrayList<>();
        services.forEach(service -> response.add(convertEntityToResponseDTO(service)));

        return response;
    }

    @Override
    public ServiceResponseDTO findById(Long serviceId) {
        Service service = businessService.findById(serviceId);

        return convertEntityToResponseDTO(service);
    }

    @Override
    public Service findEntityById(Long serviceId) {
        return businessService.findById(serviceId);
    }

    @Override
    public void deleteById(Long serviceId) {
        businessService.deleteById(serviceId);
    }

    @Override
    public List<ServiceResponseDTO> findByPet_Id(Long petId) {
        List<Service> services = businessService.findByPet_Id(petId);

        return convertEntityListToResponseListDTO(services);
    }

    @Override
    public List<ServiceResponseDTO> findByDateHourBetween(LocalDate initialDate, LocalDate finalDate) {
        List<Service> services = businessService.findByDateHourBetween(initialDate.atTime(0, 0, 0),
                finalDate.atTime(23, 59, 59));

        return convertEntityListToResponseListDTO(services);
    }

    private List<ServiceResponseDTO> convertEntityListToResponseListDTO(List<Service> services) {
        List<ServiceResponseDTO> response = new ArrayList<>();
        services.forEach(service -> response.add(convertEntityToResponseDTO(service)));

        return response;
    }

    @Override
    public Service convertRequestDTOToEntity(Long serviceId, ServiceRequestDTO requestDTO) {
        Service service = serviceId == null ? new Service() : businessService.findById(serviceId);
        service.setDateHour(LocalDateTime.now());
        service.setObservation(requestDTO.getObservation());
        service.setPet(petService.findEntityById(requestDTO.getPetId()));
        service.setServiceType(ServiceType.valueOf(requestDTO.getServiceTypeId()));
        service.setValue(requestDTO.getValue());

        return service;
    }

    @Override
    public ServiceResponseDTO convertEntityToResponseDTO(Service entity) {
        ServiceResponseDTO serviceResponseDTO = new ServiceResponseDTO();
        serviceResponseDTO.setDateHour(entity.getDateHour());
        serviceResponseDTO.setId(entity.getId());
        serviceResponseDTO.setObservation(entity.getObservation());
        serviceResponseDTO.setPetResponseDTO(petService.convertEntityToResponseDTO(entity.getPet()));
        serviceResponseDTO.setServiceType(entity.getServiceType().getDescription());
        serviceResponseDTO.setValue(entity.getValue());

        return serviceResponseDTO;
    }
}