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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerIntegrationTest {

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
    public void returnSuccessWhenCreateCustomer() {
        CustomerRequestDTO customer = new CustomerRequestDTO();
        customer.setName("John");

        Response response = given()
                .port(port)
                .body(customer)
                .header("Content-Type", "application/json")
                .post("/petstore/api/v1/customers")
                .andReturn();

        String location = response.getHeader("location");

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        assertTrue(location.matches(".*/petstore/api/v1/customers/[0-9]+"));
    }

    @Test
    public void returnErrorWhenCreateCustomerWithoutName() {
        CustomerRequestDTO customer = new CustomerRequestDTO();

        given()
                .port(port)
                .body(customer)
                .header("Content-Type", "application/json")
                .post("/petstore/api/v1/customers")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("errors.size()", Matchers.is(1))
                .body("errors[0]", Matchers.equalTo("name: O preenchimento do Nome é obrigatório"));
    }

    @Test
    public void returnErrorWhenCreateCustomerWithNameLimitExceding() {
        CustomerRequestDTO customer = new CustomerRequestDTO();
        customer.setName("Testando criação de cliente com nome excedendo limite máximo de 100 caracteres definido no CustomerRequestDTO");

        given()
                .port(port)
                .body(customer)
                .header("Content-Type", "application/json")
                .post("/petstore/api/v1/customers")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("errors.size()", Matchers.is(1))
                .body("errors[0]", Matchers.equalTo("name: Limite de caracteres (100) atingido."));
    }

    @Test
    public void returnSuccessWhenFindCustomers() {
        CustomerRequestDTO customer1 = new CustomerRequestDTO();
        customer1.setName("John");

        CustomerRequestDTO customer2 = new CustomerRequestDTO();
        customer2.setName("Maria");

        List<CustomerRequestDTO> customers = Arrays.asList(customer1, customer2);

        customers.forEach(customer -> given()
                .port(port)
                .body(customer)
                .header("Content-Type", "application/json")
                .post("/petstore/api/v1/customers"));

        given()
                .port(port)
                .get("/petstore/api/v1/customers")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", Matchers.is(2))
                .body("name[0]", Matchers.equalTo(customer1.getName()))
                .body("name[1]", Matchers.equalTo(customer2.getName()));
    }

    @Test
    public void returnSuccessWheFindCustomersById() {
        CustomerRequestDTO customer = new CustomerRequestDTO();
        customer.setName("John");

        Response response;

        response = given()
                .port(port)
                .body(customer)
                .header("Content-Type", "application/json")
                .post("/petstore/api/v1/customers")
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        CustomerResponseDTO customerResponse = given()
                .port(port)
                .get(response.getHeader("location"))
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .body()
                .as(CustomerResponseDTO.class);

        assertEquals(customer.getName(), customerResponse.getName());
    }

    @Test
    public void returnErrorWheFindCustomersById() {
        given()
                .port(port)
                .get("/petstore/api/v1/customers/1")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("errors.size()", Matchers.is(1))
                .body("errors[0]", Matchers.equalTo("Cliente 1 não encontrado!"));
    }

    @Test
    public void returnSuccessWhenUpdateCustomer() {
        CustomerRequestDTO customer = new CustomerRequestDTO();
        customer.setName("John");

        Response response;

        response = given()
                .port(port)
                .body(customer)
                .header("Content-Type", "application/json")
                .post("/petstore/api/v1/customers")
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        String location = response.getHeader("location");

        customer.setName("Pedro");

        response = given()
                .port(port)
                .body(customer)
                .header("Content-Type", "application/json")
                .put(location)
                .andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode());

        CustomerResponseDTO customerResponse = given()
                .port(port)
                .get(location)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .body()
                .as(CustomerResponseDTO.class);

        assertEquals(customer.getName(), customerResponse.getName());
    }

    @Test
    public void returnSuccessWhenDeleteCustomer() {
        CustomerRequestDTO customer = new CustomerRequestDTO();
        customer.setName("John");

        Response response;

        response = given()
                .port(port)
                .body(customer)
                .header("Content-Type", "application/json")
                .post("/petstore/api/v1/customers")
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
    public void returnErrorWhenDeleteCustomerNotFound() {
        given()
                .port(port)
                .header("Content-Type", "application/json")
                .delete("/petstore/api/v1/customers/1")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("errors.size()", Matchers.is(1))
                .body("errors[0]", Matchers.equalTo("Cliente 1 não encontrado!"));
    }

    @Test
    public void returnErrorWhenDeleteCustomersWithPets() {
        CustomerRequestDTO customer = new CustomerRequestDTO();
        customer.setName("John");

        Response response;

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

        SpecieRequestDTO specie = new SpecieRequestDTO();
        specie.setName("Cachorro");

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
                .delete(locationCustomer)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("errors.size()", Matchers.is(1))
                .body("errors[0]", Matchers.equalTo(String.format("Cliente %d não pode ser excluído pois possui Pets!", customerResponse.getId())));
    }

    @Test
    public void returnSuccessWhenFindCustomerPets() {
        CustomerRequestDTO customer = new CustomerRequestDTO();
        customer.setName("John");

        Response response;

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

        SpecieRequestDTO specie = new SpecieRequestDTO();
        specie.setName("Cachorro");

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
                .get(locationCustomer + "/pets")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", Matchers.is(1))
                .body("id[0]", Matchers.equalTo(petResponse.getId().intValue()))
                .body("name[0]", Matchers.equalTo(pet.getName()))
                .body("birthDate[0]", Matchers.equalTo(pet.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))))
                .body("customer[0].id", Matchers.equalTo(customerResponse.getId().intValue()))
                .body("customer[0].name", Matchers.equalTo(customer.getName()))
                .body("specie[0].id", Matchers.equalTo(specieResponse.getId().intValue()))
                .body("specie[0].name", Matchers.equalTo(specie.getName()));
    }
}