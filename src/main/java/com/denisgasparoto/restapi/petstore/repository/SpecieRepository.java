package com.denisgasparoto.restapi.petstore.repository;

import com.denisgasparoto.restapi.petstore.entity.Specie;
import org.springframework.data.repository.CrudRepository;

public interface SpecieRepository extends CrudRepository<Specie, Long> {

}
