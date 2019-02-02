package com.denisgasparoto.restapi.petstore.service;

import com.denisgasparoto.restapi.petstore.dto.request.CustomerRequestDTO;
import com.denisgasparoto.restapi.petstore.dto.response.CustomerResponseDTO;
import com.denisgasparoto.restapi.petstore.entity.Customer;

public interface CustomerService extends CrudService<CustomerRequestDTO, CustomerResponseDTO, Customer, Long> {

}
