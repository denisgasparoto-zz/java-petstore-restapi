package com.denisgasparoto.restapi.petstore.service.impl;

import com.denisgasparoto.restapi.petstore.business.pet.PetBusiness;
import com.denisgasparoto.restapi.petstore.dto.request.PetRequestDTO;
import com.denisgasparoto.restapi.petstore.dto.response.PetResponseDTO;
import com.denisgasparoto.restapi.petstore.entity.Pet;
import com.denisgasparoto.restapi.petstore.service.CustomerService;
import com.denisgasparoto.restapi.petstore.service.PetService;
import com.denisgasparoto.restapi.petstore.service.SpecieService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    private PetBusiness petBusiness;
    private CustomerService customerService;
    private SpecieService specieService;

    public PetServiceImpl(PetBusiness petBusiness,
                          CustomerService customerService,
                          SpecieService specieService) {
        this.petBusiness = petBusiness;
        this.customerService = customerService;
        this.specieService = specieService;
    }

    @Override
    public PetResponseDTO save(Long petId, PetRequestDTO requestDTO) {
        Pet pet = convertRequestDTOToEntity(petId, requestDTO);
        pet = petBusiness.save(pet);

        return convertEntityToResponseDTO(pet);
    }

    @Override
    public List<PetResponseDTO> findAll() {
        List<Pet> pets = petBusiness.findAll();

        return convertEntityListToResponseListDTO(pets);
    }

    @Override
    public PetResponseDTO findById(Long petId) {
        Pet pet = petBusiness.findById(petId);

        return convertEntityToResponseDTO(pet);
    }

    @Override
    public Pet findEntityById(Long petId) {
        return petBusiness.findById(petId);
    }

    @Override
    public void deleteById(Long petId) {
        petBusiness.deleteById(petId);
    }

    @Override
    public Pet convertRequestDTOToEntity(Long petId, PetRequestDTO requestDTO) {
        Pet pet = petId == null ? new Pet() : petBusiness.findById(petId);
        pet.setCustomer(customerService.findEntityById(requestDTO.getCustomerId()));
        pet.setBirthDate(requestDTO.getBirthDate());
        pet.setSpecie(specieService.findEntityById(requestDTO.getSpecieId()));
        pet.setName(requestDTO.getName());

        return pet;
    }

    @Override
    public PetResponseDTO convertEntityToResponseDTO(Pet entity) {
        PetResponseDTO petResponseDTO = new PetResponseDTO();
        petResponseDTO.setCustomerResponseDTO(customerService.convertEntityToResponseDTO(entity.getCustomer()));
        petResponseDTO.setBirthDate(entity.getBirthDate());
        petResponseDTO.setSpecieResponseDTO(specieService.convertEntityToResponseDTO(entity.getSpecie()));
        petResponseDTO.setId(entity.getId());
        petResponseDTO.setName(entity.getName());

        return petResponseDTO;
    }

    @Override
    public List<PetResponseDTO> findBySpecie_Id(Long specieId) {
        List<Pet> species = petBusiness.findBySpecie_Id(specieId);

        return convertEntityListToResponseListDTO(species);
    }

    @Override
    public List<PetResponseDTO> findByCustomer_Id(Long customerId) {
        List<Pet> pets = petBusiness.findByCustomer_Id(customerId);

        return convertEntityListToResponseListDTO(pets);
    }

    private List<PetResponseDTO> convertEntityListToResponseListDTO(List<Pet> pets) {
        List<PetResponseDTO> response = new ArrayList<>();
        pets.forEach(pet -> response.add(convertEntityToResponseDTO(pet)));

        return response;
    }

}