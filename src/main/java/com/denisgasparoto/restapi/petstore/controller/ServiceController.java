package com.denisgasparoto.restapi.petstore.controller;

import com.denisgasparoto.restapi.petstore.dto.request.ServiceRequestDTO;
import com.denisgasparoto.restapi.petstore.dto.response.ServiceResponseDTO;
import com.denisgasparoto.restapi.petstore.service.ServiceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
public class ServiceController {

    private ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> save(@Valid @RequestBody ServiceRequestDTO serviceRequestDTO) {
        ServiceResponseDTO serviceResponseDTO = serviceService.save(null, serviceRequestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(serviceResponseDTO.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@PathVariable("id") Long serviceId,
                                       @Valid @RequestBody ServiceRequestDTO serviceRequestDTO) {
        serviceService.save(serviceId, serviceRequestDTO);

        return ResponseEntity
                .noContent()
                .build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ServiceResponseDTO> findById(@PathVariable("id") Long serviceId) {
        ServiceResponseDTO serviceResponseDTO = serviceService.findById(serviceId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceResponseDTO);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ServiceResponseDTO>> findAll() {
        List<ServiceResponseDTO> servicesResponseDTO = serviceService.findAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(servicesResponseDTO);
    }

    @RequestMapping(value = "/findByDate", method = RequestMethod.GET)
    public ResponseEntity<List<ServiceResponseDTO>> findByDate(@RequestParam(name = "initialDate", required = true)
                                                               @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate initialDate,
                                                               @RequestParam(name = "finalDate", required = true)
                                                               @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate finalDate) {
        List<ServiceResponseDTO> services = serviceService.findByDateHourBetween(initialDate, finalDate);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(services);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long serviceId) {
        serviceService.deleteById(
                serviceId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
