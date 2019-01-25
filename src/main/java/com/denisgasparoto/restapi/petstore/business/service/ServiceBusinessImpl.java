package com.denisgasparoto.restapi.petstore.business.service;

import com.denisgasparoto.restapi.petstore.entity.Service;
import com.denisgasparoto.restapi.petstore.repository.ServiceRepository;
import org.springframework.stereotype.Component;

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
    public Service findById(Long id) {
        return serviceRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        serviceRepository.deleteById(id);
    }
}
