package com.denisgasparoto.restapi.petstore.repository;

import com.denisgasparoto.restapi.petstore.entity.Service;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ServiceRepository extends CrudRepository<Service, Long> {

    List<Service> findByPet_Id(Long petId);

    List<Service> findByDateHourBetween(LocalDateTime initialDate, LocalDateTime finalDate);
}
