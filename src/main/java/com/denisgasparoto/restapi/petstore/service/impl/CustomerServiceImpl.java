package com.denisgasparoto.restapi.petstore.service.impl;

import com.denisgasparoto.restapi.petstore.business.customer.CustomerBusiness;
import com.denisgasparoto.restapi.petstore.dto.request.CustomerRequestDTO;
import com.denisgasparoto.restapi.petstore.dto.response.CustomerResponseDTO;
import com.denisgasparoto.restapi.petstore.entity.Customer;
import com.denisgasparoto.restapi.petstore.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerBusiness customerBusiness;

    public CustomerServiceImpl(CustomerBusiness customerBusiness) {
        this.customerBusiness = customerBusiness;
    }

    @Override
    public CustomerResponseDTO save(Long customerId, CustomerRequestDTO requestDTO) {
        Customer customer = convertRequestDTOToEntity(customerId, requestDTO);
        customer = customerBusiness.save(customer);

        return convertEntityToResponseDTO(customer);
    }

    @Override
    public List<CustomerResponseDTO> findAll() {
        List<Customer> customers = customerBusiness.findAll();
        List<CustomerResponseDTO> response = new ArrayList<>();
        customers.forEach(customer -> response.add(convertEntityToResponseDTO(customer)));

        return response;
    }

    @Override
    public CustomerResponseDTO findById(Long customerId) {
        Customer customer = customerBusiness.findById(customerId);

        return convertEntityToResponseDTO(customer);
    }

    @Override
    public Customer findEntityById(Long customerId) {
        return customerBusiness.findById(customerId);
    }

    @Override
    public void deleteById(Long id) {
        customerBusiness.deleteById(id);
    }

    @Override
    public Customer convertRequestDTOToEntity(Long customerId, CustomerRequestDTO requestDTO) {
        Customer customer = customerId == null ? new Customer() : customerBusiness.findById(customerId);
        customer.setName(requestDTO.getName());

        return customer;
    }

    @Override
    public CustomerResponseDTO convertEntityToResponseDTO(Customer entity) {
        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setId(entity.getId());
        customerResponseDTO.setName(entity.getName());

        return customerResponseDTO;
    }
}