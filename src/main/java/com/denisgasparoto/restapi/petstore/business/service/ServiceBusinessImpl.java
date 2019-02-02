package com.denisgasparoto.restapi.petstore.business.service;

import com.denisgasparoto.restapi.petstore.entity.Service;
import com.denisgasparoto.restapi.petstore.exception.RegisterNotFoundException;
import com.denisgasparoto.restapi.petstore.repository.ServiceRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceBusinessImpl implements ServiceBusiness {

    private ServiceRepository serviceRepository;

    public ServiceBusinessImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public Service save(Service service) {
        return serviceRepository.save(service);
    }

    @Override
    public List<Service> findAll() {
        List<Service> services = new ArrayList<>();
        serviceRepository.findAll().forEach(services::add);

        return services;
    }

    @Override
    public Service findById(Long serviceId) {
        Service service = serviceRepository.findById(serviceId).orElse(null);

        if (service == null) {
            throw new RegisterNotFoundException(String.format("Serviço %d não encontrado!", serviceId));
        }

        return service;
    }

    @Override
    public void deleteById(Long serviceId) {
        findById(serviceId);
        serviceRepository.deleteById(serviceId);
    }

    @Override
    public List<Service> findByPet_Id(Long petId) {
        return serviceRepository.findByPet_Id(petId);
    }

    @Override
    public List<Service> findByDateHourBetween(LocalDateTime initialDate, LocalDateTime finalDate) {
        return serviceRepository.findByDateHourBetween(initialDate, finalDate);
    }
}
