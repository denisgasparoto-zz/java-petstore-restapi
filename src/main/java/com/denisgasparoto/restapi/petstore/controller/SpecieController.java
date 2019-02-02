package com.denisgasparoto.restapi.petstore.controller;

import com.denisgasparoto.restapi.petstore.base.BaseController;
import com.denisgasparoto.restapi.petstore.dto.request.SpecieRequestDTO;
import com.denisgasparoto.restapi.petstore.dto.response.PetResponseDTO;
import com.denisgasparoto.restapi.petstore.dto.response.SpecieResponseDTO;
import com.denisgasparoto.restapi.petstore.service.PetService;
import com.denisgasparoto.restapi.petstore.service.SpecieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/species")
public class SpecieController extends BaseController {

    private SpecieService specieService;
    private PetService petService;

    public SpecieController(SpecieService specieService,
                            PetService petService) {
        this.specieService = specieService;
        this.petService = petService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> save(@Valid @RequestBody SpecieRequestDTO specieRequestDTO) {
        SpecieResponseDTO specieResponseDTO = specieService.save(null, specieRequestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(specieResponseDTO.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@PathVariable("id") Long serviceId,
                                       @Valid @RequestBody SpecieRequestDTO specieRequestDTO) {
        specieService.save(serviceId, specieRequestDTO);

        return ResponseEntity
                .noContent()
                .build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<SpecieResponseDTO> findById(@PathVariable("id") Long serviceId) {
        SpecieResponseDTO specieResponseDTO = specieService.findById(serviceId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(specieResponseDTO);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SpecieResponseDTO>> findAll() {
        List<SpecieResponseDTO> speciesResponseDTO = specieService.findAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(speciesResponseDTO);
    }

    @RequestMapping(value = "/{id}/pets", method = RequestMethod.GET)
    public ResponseEntity<List<PetResponseDTO>> findPets(@PathVariable("id") Long petId) {
        List<PetResponseDTO> pets = petService.findBySpecie_Id(petId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pets);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long serviceId) {
        specieService.deleteById(serviceId);

        return ResponseEntity
                .noContent()
                .build();
    }
}