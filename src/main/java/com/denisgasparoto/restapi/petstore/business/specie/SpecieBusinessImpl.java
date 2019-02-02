package com.denisgasparoto.restapi.petstore.business.specie;

import com.denisgasparoto.restapi.petstore.business.pet.PetBusiness;
import com.denisgasparoto.restapi.petstore.entity.Pet;
import com.denisgasparoto.restapi.petstore.entity.Specie;
import com.denisgasparoto.restapi.petstore.exception.BusinessException;
import com.denisgasparoto.restapi.petstore.exception.RegisterNotFoundException;
import com.denisgasparoto.restapi.petstore.repository.SpecieRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SpecieBusinessImpl implements SpecieBusiness {

    private SpecieRepository specieRepository;
    private PetBusiness petBusiness;

    public SpecieBusinessImpl(SpecieRepository specieRepository,
                              PetBusiness petBusiness) {
        this.specieRepository = specieRepository;
        this.petBusiness = petBusiness;
    }

    @Override
    public Specie save(Specie specie) {
        return specieRepository.save(specie);
    }

    @Override
    public List<Specie> findAll() {
        List<Specie> species = new ArrayList<>();
        specieRepository.findAll().forEach(species::add);

        return species;
    }

    @Override
    public Specie findById(Long specieId) {
        Specie specie = specieRepository.findById(specieId).orElse(null);

        if (specie == null) {
            throw new RegisterNotFoundException(String.format("Espécie %d não encontrada", specieId));
        }

        return specie;
    }

    @Override
    public void deleteById(Long specieId) {
        findById(specieId);
        List<Pet> pets = petBusiness.findBySpecie_Id(specieId);

        if (!pets.isEmpty()) {
            throw new BusinessException(String.format("Espécie %d não pode ser excluída pois possui Pets!", specieId));
        }

        specieRepository.deleteById(specieId);
    }
}
