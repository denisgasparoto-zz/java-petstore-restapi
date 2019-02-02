package com.denisgasparoto.restapi.petstore.business.pet;

import com.denisgasparoto.restapi.petstore.base.BusinessCrud;
import com.denisgasparoto.restapi.petstore.entity.Pet;

import java.util.List;

public interface PetBusiness extends BusinessCrud<Pet, Long> {

    List<Pet> findBySpecie_Id(Long id);

    List<Pet> findByCustomer_Id(Long id);
}
