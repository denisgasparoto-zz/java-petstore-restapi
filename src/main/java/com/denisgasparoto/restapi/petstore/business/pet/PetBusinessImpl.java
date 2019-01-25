package com.denisgasparoto.restapi.petstore.business.pet;

import com.denisgasparoto.restapi.petstore.entity.Pet;
import com.denisgasparoto.restapi.petstore.repository.PetRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PetBusinessImpl implements PetBusiness {

    private PetRepository petRepository;

    public PetBusinessImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
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
    public Pet findById(Long id) {
        return petRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        petRepository.deleteById(id);
    }
}
