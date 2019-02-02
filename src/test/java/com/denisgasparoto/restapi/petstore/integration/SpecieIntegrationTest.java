package com.denisgasparoto.restapi.petstore.integration;

import com.denisgasparoto.restapi.petstore.dto.request.CustomerRequestDTO;
import com.denisgasparoto.restapi.petstore.dto.request.PetRequestDTO;
import com.denisgasparoto.restapi.petstore.dto.request.SpecieRequestDTO;
import com.denisgasparoto.restapi.petstore.dto.response.CustomerResponseDTO;
import com.denisgasparoto.restapi.petstore.dto.response.PetResponseDTO;
import com.denisgasparoto.restapi.petstore.dto.response.SpecieResponseDTO;
import com.denisgasparoto.restapi.petstore.repository.CustomerRepository;
import com.denisgasparoto.restapi.petstore.repository.PetRepository;
import com.denisgasparoto.restapi.petstore.repository.ServiceRepository;
import com.denisgasparoto.restapi.petstore.repository.SpecieRepository;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpecieIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private SpecieRepository specieRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Before
    public void clearData() {
        serviceRepository.deleteAll();
        petRepository.deleteAll();
        specieRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    public void returnSuccessWhenCreateSpecie() {
        SpecieRequestDTO specie = new SpecieRequestDTO();
        specie.setName("Cachorro");

        Response response = given()
                .port(port)
                .body(specie)
                .header("Content-Type", "application/json")
                .post("/petstore/api/v1/species")
                .andReturn();

        String location = response.getHeader("location");

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        assertTrue(location.matches(".*/petstore/api/v1/species/[0-9]+"));
    }

    @Test
    public void returnErrorWhenCreateSpecieWithoutName() {
        SpecieRequestDTO specie = new SpecieRequestDTO();

        given()
                .port(port)
                .body(specie)
                .header("Content-Type", "application/json")
                .post("/petstore/api/v1/species")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("errors.size()", Matchers.is(1))
                .body("errors[0]", Matchers.equalTo("nome: Nome é de preenchimento obrigatório."));
    }

    @Test
    public void returnErrorWhenCreateSpecieWithNameLimitExceding() {
        SpecieRequestDTO customer = new SpecieRequestDTO();
        customer.setName("Testando criação de espécie com nome excedendo limite máximo de 100 caracteres definido no SpecieRequestDTO");

        given()
                .port(port)
                .body(customer)
                .header("Content-Type", "application/json")
                .post("/petstore/api/v1/species")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("errors.size()", Matchers.is(1))
                .body("errors[0]", Matchers.equalTo("nome: Nome não pode ultrapassar 100 caracteres."));
    }

    @Test
    public void returnSuccessWhenFindSpecies() {
        SpecieRequestDTO specie1 = new SpecieRequestDTO();
        specie1.setName("Cachorro");

        SpecieRequestDTO specie2 = new SpecieRequestDTO();
        specie2.setName("Gato");

        List<SpecieRequestDTO> species = Arrays.asList(specie1, specie2);

        species.forEach(specie -> given()
                .port(port)
                .body(specie)
                .header("Content-Type", "application/json")
                .post("/petstore/api/v1/species"));

        given()
                .port(port)
                .get("/petstore/api/v1/species")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", Matchers.is(2))
                .body("name[0]", Matchers.equalTo(specie1.getName()))
                .body("name[1]", Matchers.equalTo(specie2.getName()));
    }

    @Test
    public void returnSuccessWheFindSpeciesById() {
        SpecieRequestDTO specie = new SpecieRequestDTO();
        specie.setName("Cachorro");

        Response response;

        response = given()
                .port(port)
                .body(specie)
                .header("Content-Type", "application/json")
                .post("/petstore/api/v1/species")
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        SpecieResponseDTO specieResponse = given()
                .port(port)
                .get(response.getHeader("location"))
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .body()
                .as(SpecieResponseDTO.class);

        assertEquals(specie.getName(), specieResponse.getName());
    }

    @Test
    public void returnErrorWheFindSpeciesById() {
        given()
                .port(port)
                .get("/petstore/api/v1/species/1")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("errors.size()", Matchers.is(1))
                .body("errors[0]", Matchers.equalTo("Espécie 1 não encontrada!"));
    }

    @Test
    public void returnSuccessWhenUpdateSpecie() {
        SpecieRequestDTO specie = new SpecieRequestDTO();
        specie.setName("Cachorro");

        Response response;

        response = given()
                .port(port)
                .body(specie)
                .header("Content-Type", "application/json")
                .post("/petstore/api/v1/species")
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        String location = response.getHeader("location");

        specie.setName("Gato");

        response = given()
                .port(port)
                .body(specie)
                .header("Content-Type", "application/json")
                .put(location)
                .andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode());

        SpecieResponseDTO specieResponse = given()
                .port(port)
                .get(location)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .body()
                .as(SpecieResponseDTO.class);

        assertEquals(specie.getName(), specieResponse.getName());
    }

    @Test
    public void returnSuccessWhenDeleteSpecie() {
        SpecieRequestDTO specie = new SpecieRequestDTO();
        specie.setName("Cachorro");

        Response response;

        response = given()
                .port(port)
                .body(specie)
                .header("Content-Type", "application/json")
                .post("/petstore/api/v1/species")
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        String location = response.getHeader("location");

        response = given()
                .port(port)
                .header("Content-Type", "application/json")
                .delete(location)
                .andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode());

        given()
                .port(port)
                .get(location)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("errors.size()", Matchers.is(1));
    }

    @Test
    public void returnErrorWhenDeleteSpecieNotFound() {
        given()
                .port(port)
                .header("Content-Type", "application/json")
                .delete("/petstore/api/v1/species/1")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("errors.size()", Matchers.is(1))
                .body("errors[0]", Matchers.equalTo("Espécie 1 não encontrada!"));
    }

    @Test
    public void returnErrorWhenDeleteSpeciesWithPets() {
        SpecieRequestDTO specie = new SpecieRequestDTO();
        specie.setName("Cachorro");

        Response response;

        response = given()
                .port(port)
                .body(specie)
                .header("Content-Type", "application/json")
                .post("/petstore/api/v1/species")
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        String locationSpecie = response.getHeader("location");

        SpecieResponseDTO specieResponse = given()
                .port(port)
                .get(locationSpecie)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .body()
                .as(SpecieResponseDTO.class);

        CustomerRequestDTO customer = new CustomerRequestDTO();
        customer.setName("João");

        response = given()
                .port(port)
                .body(customer)
                .header("Content-Type", "application/json")
                .post("/petstore/api/v1/customers")
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        String locationCustomer = response.getHeader("location");

        CustomerResponseDTO customerResponse = given()
                .port(port)
                .get(locationCustomer)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .body()
                .as(CustomerResponseDTO.class);

        PetRequestDTO pet = new PetRequestDTO();
        pet.setName("Rex");
        pet.setBirthDate(LocalDate.parse("2018-01-01"));
        pet.setCustomerId(customerResponse.getId());
        pet.setSpecieId(specieResponse.getId());

        response = given()
                .port(port)
                .body(pet)
                .header("Content-Type", "application/json")
                .post("/petstore/api/v1/pets")
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        given()
                .port(port)
                .header("Content-Type", "application/json")
                .delete(locationSpecie)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("errors.size()", Matchers.is(1))
                .body("errors[0]", Matchers.equalTo(String
                        .format("Espécie %d não pode ser excluída pois possui Pets!", specieResponse.getId())));
    }

    @Test
    public void returnSuccessWhenFindSpeciePets() {
        SpecieRequestDTO specie = new SpecieRequestDTO();
        specie.setName("Cachorro");

        Response response;

        response = given()
                .port(port)
                .body(specie)
                .header("Content-Type", "application/json")
                .post("/petstore/api/v1/species")
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        String locationSpecie = response.getHeader("location");

        SpecieResponseDTO specieResponse = given()
                .port(port)
                .get(locationSpecie)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .body()
                .as(SpecieResponseDTO.class);

        CustomerRequestDTO customer = new CustomerRequestDTO();
        customer.setName("João");

        response = given()
                .port(port)
                .body(customer)
                .header("Content-Type", "application/json")
                .post("/petstore/api/v1/customers")
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        String locationCustomer = response.getHeader("location");

        CustomerResponseDTO customerResponse = given()
                .port(port)
                .get(locationCustomer)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .body()
                .as(CustomerResponseDTO.class);

        PetRequestDTO pet = new PetRequestDTO();
        pet.setName("Rex");
        pet.setBirthDate(LocalDate.parse("2018-01-01"));
        pet.setCustomerId(customerResponse.getId());
        pet.setSpecieId(specieResponse.getId());

        response = given()
                .port(port)
                .body(pet)
                .header("Content-Type", "application/json")
                .post("/petstore/api/v1/pets")
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        PetResponseDTO petResponse = given()
                .port(port)
                .get(response.getHeader("location"))
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .body()
                .as(PetResponseDTO.class);

        given()
                .port(port)
                .get(locationSpecie + "/pets")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", Matchers.is(1))
                .body("id[0]", Matchers.equalTo(petResponse.getId().intValue()))
                .body("name[0]", Matchers.equalTo(pet.getName()))
                .body("birthDate[0]", Matchers.equalTo(pet.getBirthDate()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))))
                .body("customer[0].id", Matchers.equalTo(customerResponse.getId().intValue()))
                .body("customer[0].name", Matchers.equalTo(customer.getName()))
                .body("specie[0].id", Matchers.equalTo(specieResponse.getId().intValue()))
                .body("specie[0].name", Matchers.equalTo(specie.getName()));
    }
}