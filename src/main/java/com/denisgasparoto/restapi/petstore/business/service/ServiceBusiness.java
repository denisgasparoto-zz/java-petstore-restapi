package com.denisgasparoto.restapi.petstore.business.service;

import com.denisgasparoto.restapi.petstore.base.BusinessCrud;
import com.denisgasparoto.restapi.petstore.entity.Service;

import java.time.LocalDateTime;
import java.util.List;

public interface ServiceBusiness extends BusinessCrud<Service, Long> {

    List<Service> findByPet_Id(Long id);

    List<Service> findByDateHourBetween(LocalDateTime initialDate, LocalDateTime finalDate);
}
