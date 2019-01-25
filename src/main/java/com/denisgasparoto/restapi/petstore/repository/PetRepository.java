package com.denisgasparoto.restapi.petstore.repository;

import com.denisgasparoto.restapi.petstore.entity.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long> {

}
