package com.denisgasparoto.restapi.petstore.controller;

import com.denisgasparoto.restapi.petstore.base.BaseController;
import com.denisgasparoto.restapi.petstore.dto.request.PetRequestDTO;
import com.denisgasparoto.restapi.petstore.dto.response.PetResponseDTO;
import com.denisgasparoto.restapi.petstore.dto.response.ServiceResponseDTO;
import com.denisgasparoto.restapi.petstore.service.PetService;
import com.denisgasparoto.restapi.petstore.service.ServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pets")
public class PetController extends BaseController {

    private PetService petService;
    private ServiceService serviceService;

    public PetController(PetService petService,
                         ServiceService serviceService) {
        this.petService = petService;
        this.serviceService = serviceService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> save(@Valid @RequestBody PetRequestDTO petRequestDTO) {
        PetResponseDTO petResponseDTO = petService.save(null, petRequestDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(petResponseDTO.getId())
                .toUri();

        return ResponseEntity.created(uri)
                .build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@PathVariable("id") Long petId,
                                       @Valid @RequestBody PetRequestDTO petRequestDTO) {
        petService.save(petId, petRequestDTO);

        return ResponseEntity
                .noContent()
                .build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PetResponseDTO> findById(@PathVariable("id") Long petId) {
        PetResponseDTO petResponseDTO = petService.findById(petId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(petResponseDTO);
    }

    @RequestMapping(value = "/{id}/services", method = RequestMethod.GET)
    public ResponseEntity<List<ServiceResponseDTO>> findServices(@PathVariable("id") Long petId) {
        List<ServiceResponseDTO> services = serviceService.findByPet_Id(petId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(services);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<PetResponseDTO>> findAll() {
        List<PetResponseDTO> petsResponseDTO = petService.findAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(petsResponseDTO);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long petId) {
        petService.deleteById(petId);

        return ResponseEntity
                .noContent()
                .build();
    }
}