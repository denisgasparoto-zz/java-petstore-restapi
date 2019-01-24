package com.denisgasparoto.restapi.petstore;

import com.denisgasparoto.restapi.petstore.entity.Specie;
import com.denisgasparoto.restapi.petstore.repository.SpecieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    private SpecieRepository specieRepository;

    @Override
    public void run(String... args) {
        System.out.println("Running!");
        
        Specie specie = new Specie();
        specie.setDescription("Dog");
        specieRepository.save(specie);
    }
}
