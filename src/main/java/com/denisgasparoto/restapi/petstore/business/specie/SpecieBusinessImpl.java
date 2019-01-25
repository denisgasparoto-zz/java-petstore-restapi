package com.denisgasparoto.restapi.petstore.business.specie;

import com.denisgasparoto.restapi.petstore.entity.Specie;
import com.denisgasparoto.restapi.petstore.repository.SpecieRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
    public class SpecieBusinessImpl implements SpecieBusiness {

    private SpecieRepository specieRepository;

    public SpecieBusinessImpl(SpecieRepository specieRepository) {
        this.specieRepository = specieRepository;
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
    public Specie findById(Long id) {
        return specieRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        specieRepository.deleteById(id);
    }
}
