package com.denisgasparoto.restapi.petstore.repository;

import com.denisgasparoto.restapi.petstore.entity.Service;
import org.springframework.data.repository.CrudRepository;

public interface ServiceRepository extends CrudRepository<Service, Long> {

}
