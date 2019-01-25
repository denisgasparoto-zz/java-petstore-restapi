package com.denisgasparoto.restapi.petstore.business.customer;

import com.denisgasparoto.restapi.petstore.entity.Customer;
import com.denisgasparoto.restapi.petstore.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerBusinessImpl implements CustomerBusiness {

    private CustomerRepository customerRepository;

    public CustomerBusinessImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().forEach(customers::add);
        return customers;
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }
}
