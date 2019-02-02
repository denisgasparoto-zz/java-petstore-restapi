package com.denisgasparoto.restapi.petstore.controller;

import com.denisgasparoto.restapi.petstore.base.BaseController;
import com.denisgasparoto.restapi.petstore.dto.request.CustomerRequestDTO;
import com.denisgasparoto.restapi.petstore.dto.response.CustomerResponseDTO;
import com.denisgasparoto.restapi.petstore.dto.response.PetResponseDTO;
import com.denisgasparoto.restapi.petstore.service.CustomerService;
import com.denisgasparoto.restapi.petstore.service.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController extends BaseController {

    private CustomerService customerService;
    private PetService petService;

    public CustomerController(CustomerService customerService,
                              PetService petService) {
        this.customerService = customerService;
        this.petService = petService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> save(@Valid @RequestBody CustomerRequestDTO customerRequestDTO) {
        CustomerResponseDTO customerResponseDTO = customerService.save(null, customerRequestDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(customerResponseDTO.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@PathVariable("id") Long customerId,
                                       @Valid @RequestBody CustomerRequestDTO customerRequestDTO) {
        customerService.save(customerId, customerRequestDTO);

        return ResponseEntity
                .noContent()
                .build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CustomerResponseDTO> findById(@PathVariable("id") Long customerId) {
        CustomerResponseDTO customerResponseDTO = customerService.findById(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerResponseDTO);
    }

    @RequestMapping(value = "/{id}/pets", method = RequestMethod.GET)
    public ResponseEntity<List<PetResponseDTO>> findPets(@PathVariable("id") Long petId) {
        List<PetResponseDTO> pets = petService.findByCustomer_Id(petId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pets);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CustomerResponseDTO>> findAll() {
        List<CustomerResponseDTO> customersResponseDTO = customerService.findAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customersResponseDTO);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long customerId) {
        customerService.deleteById(customerId);

        return ResponseEntity
                .noContent()
                .build();
    }
}