package com.denisgasparoto.restapi.petstore.business.pet;

import com.denisgasparoto.restapi.petstore.business.service.ServiceBusiness;
import com.denisgasparoto.restapi.petstore.entity.Pet;
import com.denisgasparoto.restapi.petstore.entity.Service;
import com.denisgasparoto.restapi.petstore.exception.BusinessException;
import com.denisgasparoto.restapi.petstore.exception.RegisterNotFoundException;
import com.denisgasparoto.restapi.petstore.repository.PetRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PetBusinessImpl implements PetBusiness {

    private PetRepository petRepository;
    private ServiceBusiness serviceBusiness;

    public PetBusinessImpl(PetRepository petRepository,
                           ServiceBusiness serviceBusiness) {
        this.petRepository = petRepository;
        this.serviceBusiness = serviceBusiness;
    }

    @Override
    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public List<Pet> findAll() {
        List<Pet> pets = new ArrayList<>();
        petRepository.findAll().forEach(pets::add);

        return pets;
    }

    @Override
    public Pet findById(Long petId) {
        Pet pet = petRepository.findById(petId).orElse(null);

        if (pet == null) {
            throw new RegisterNotFoundException(String.format("Pet %d não encontrado!", petId));
        }

        return pet;
    }

    @Override
    public void deleteById(Long petId) {
        findById(petId);
        List<Service> services = serviceBusiness.findByPet_Id(petId);

        if (!services.isEmpty()) {
            throw new BusinessException(String.format("Pet %d não pode ser excluído pois possui Serviços!", petId));
        }

        petRepository.deleteById(petId);
    }

    @Override
    public List<Pet> findBySpecie_Id(Long petId) {
        return petRepository.findBySpecie_Id(petId);
    }

    @Override
    public List<Pet> findByCustomer_Id(Long petId) {
        return petRepository.findByCustomer_Id(petId);
    }
}
