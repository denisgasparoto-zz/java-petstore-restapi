package com.denisgasparoto.restapi.petstore.repository;

import com.denisgasparoto.restapi.petstore.entity.Pet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PetRepository extends CrudRepository<Pet, Long> {

    List<Pet> findBySpecie_Id(Long id);

    List<Pet> findByCustomer_Id(Long id);

    List<Pet> findBySpecie_IdAndBirthDate(Long id, LocalDate birthDate);

    @Query("SELECT p FROM Pet p WHERE p.specie.id = :id")
    List<Pet> findBySpecieIdWithJPQL(@Param("id") Long id);

    @Query(value = "SELECT * FROM Pet p INNER JOIN Specie e ON e.id = p.id_specie WHERE e.id = :id", nativeQuery = true)
    List<Pet> findBySpecieIdWithNativeSQL(@Param("id") Long id);
}
