package com.denisgasparoto.restapi.petstore.repository;

import com.denisgasparoto.restapi.petstore.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
