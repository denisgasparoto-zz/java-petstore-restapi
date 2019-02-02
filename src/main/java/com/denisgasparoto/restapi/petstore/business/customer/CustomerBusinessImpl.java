package com.denisgasparoto.restapi.petstore.business.customer;

import com.denisgasparoto.restapi.petstore.business.pet.PetBusiness;
import com.denisgasparoto.restapi.petstore.entity.Customer;
import com.denisgasparoto.restapi.petstore.entity.Pet;
import com.denisgasparoto.restapi.petstore.exception.BusinessException;
import com.denisgasparoto.restapi.petstore.exception.RegisterNotFoundException;
import com.denisgasparoto.restapi.petstore.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerBusinessImpl implements CustomerBusiness {

    private CustomerRepository customerRepository;
    private PetBusiness petBusiness;

    public CustomerBusinessImpl(CustomerRepository customerRepository,
                                PetBusiness petBusiness) {
        this.customerRepository = customerRepository;
        this.petBusiness = petBusiness;
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
    public Customer findById(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) {
            throw new RegisterNotFoundException(String.format("Cliente %d não encontrado!", customerId));
        }

        return customer;
    }

    @Override
    public void deleteById(Long customerId) {
        findById(customerId);
        List<Pet> pets = petBusiness.findByCustomer_Id(customerId);

        if (!pets.isEmpty()) {
            throw new BusinessException(String.format("Cliente %d não pode ser excluído pois possui Pets!", customerId));
        }

        customerRepository.deleteById(customerId);
    }
}
