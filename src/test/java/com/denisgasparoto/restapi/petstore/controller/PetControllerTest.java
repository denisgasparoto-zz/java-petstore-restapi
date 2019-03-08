package com.denisgasparoto.restapi.petstore.controller;

import com.denisgasparoto.restapi.petstore.dto.response.CustomerResponseDTO;
import com.denisgasparoto.restapi.petstore.dto.response.PetResponseDTO;
import com.denisgasparoto.restapi.petstore.dto.response.ServiceResponseDTO;
import com.denisgasparoto.restapi.petstore.dto.response.SpecieResponseDTO;
import com.denisgasparoto.restapi.petstore.entity.ServiceType;
import com.denisgasparoto.restapi.petstore.service.PetService;
import com.denisgasparoto.restapi.petstore.service.ServiceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PetController.class)
public class PetControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PetService petService;

    @MockBean
    private ServiceService serviceService;

    private PetResponseDTO pet1;
    private PetResponseDTO pet2;
    private List<PetResponseDTO> pets;

    @Before
    public void before() {
        CustomerResponseDTO customer = new CustomerResponseDTO();
        customer.setId(1L);
        customer.setName("John");

        SpecieResponseDTO specie = new SpecieResponseDTO();
        specie.setId(1L);
        specie.setName("Cachorro");

        pet1 = new PetResponseDTO();
        pet1.setId(1L);
        pet1.setName("Rex");
        pet1.setBirthDate(LocalDate.parse("2018-01-01"));
        pet1.setCustomerResponseDTO(customer);
        pet1.setSpecieResponseDTO(specie);

        pet2 = new PetResponseDTO();
        pet2.setId(1L);
        pet2.setName("Totó");
        pet2.setBirthDate(LocalDate.parse("2018-01-01"));
        pet2.setCustomerResponseDTO(customer);
        pet2.setSpecieResponseDTO(specie);

        pets = Arrays.asList(pet1, pet2);
    }

    @Test
    public void checkSave() throws Exception {
        CustomerResponseDTO customer = new CustomerResponseDTO();
        customer.setId(1L);
        customer.setName("John");

        SpecieResponseDTO specie = new SpecieResponseDTO();
        specie.setId(1L);
        specie.setName("Cachorro");

        PetResponseDTO petResponse = new PetResponseDTO();
        petResponse.setId(1L);
        petResponse.setName("Nina");
        petResponse.setBirthDate(LocalDate.parse("2018-01-01"));
        petResponse.setCustomerResponseDTO(customer);
        petResponse.setSpecieResponseDTO(specie);

        Mockito.when(petService.save(Mockito.eq(null), Mockito.any())).thenReturn(petResponse);

        mvc.perform(post("/api/v1/pets").contentType(APPLICATION_JSON)
                .content("{ \"name\": \"Nina\"                 ,"
                        + "  \"birthDate\": \"01/01/2018\" ,"
                        + "  \"customerId\": \"1\"               ,"
                        + "  \"specieId\": \"1\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void checkFindAll() throws Exception {
        Mockito.when(petService.findAll()).thenReturn(pets);

        mvc.perform(get("/api/v1/pets").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(pets.size())))
                .andExpect(jsonPath("$[0].id", equalTo(pet1.getId().intValue())))
                .andExpect(jsonPath("$[0].name", equalTo(pet1.getName())))
                .andExpect(jsonPath("$[0].birthDate", equalTo(pet1.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
                .andExpect(jsonPath("$[0].customer.id", equalTo(pet1.getCustomerResponseDTO().getId().intValue())))
                .andExpect(jsonPath("$[0].customer.name", equalTo(pet1.getCustomerResponseDTO().getName())))
                .andExpect(jsonPath("$[0].specie.id", equalTo(pet1.getSpecieResponseDTO().getId().intValue())))
                .andExpect(jsonPath("$[0].specie.name", equalTo(pet1.getSpecieResponseDTO().getName())))
                .andExpect(jsonPath("$[1].id", equalTo(pet2.getId().intValue())))
                .andExpect(jsonPath("$[1].name", equalTo(pet2.getName())))
                .andExpect(jsonPath("$[1].birthDate", equalTo(pet2.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
                .andExpect(jsonPath("$[1].customer.id", equalTo(pet2.getCustomerResponseDTO().getId().intValue())))
                .andExpect(jsonPath("$[1].customer.name", equalTo(pet2.getCustomerResponseDTO().getName())))
                .andExpect(jsonPath("$[1].specie.id", equalTo(pet2.getSpecieResponseDTO().getId().intValue())))
                .andExpect(jsonPath("$[1].specie.name", equalTo(pet2.getSpecieResponseDTO().getName())));
    }

    @Test
    public void checkFindById() throws Exception {
        Mockito.when(petService.findById(pet1.getId())).thenReturn(pet1);

        mvc.perform(get("/api/v1/pets/" + pet1.getId()).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(pet1.getId().intValue())))
                .andExpect(jsonPath("$.name", equalTo(pet1.getName())))
                .andExpect(jsonPath("$.birthDate", equalTo(pet1.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
                .andExpect(jsonPath("$.customer.id", equalTo(pet1.getCustomerResponseDTO().getId().intValue())))
                .andExpect(jsonPath("$.customer.name", equalTo(pet1.getCustomerResponseDTO().getName())))
                .andExpect(jsonPath("$.specie.id", equalTo(pet1.getSpecieResponseDTO().getId().intValue())))
                .andExpect(jsonPath("$.specie.name", equalTo(pet1.getSpecieResponseDTO().getName())));
    }

    @Test
    public void checkFindServices() throws Exception {
        ServiceResponseDTO service1 = new ServiceResponseDTO();
        service1.setId(1L);
        service1.setObservation("Teste");
        service1.setDateHour(LocalDateTime.parse("2018-01-01T16:11:26.485"));
        service1.setServiceType(ServiceType.CONSULTATION.getDescription());
        service1.setValue(new BigDecimal(80));
        service1.setPetResponseDTO(pet1);

        List<ServiceResponseDTO> servicesPet1 = Arrays.asList(service1);

        Mockito.when(serviceService.findByPet_Id(service1.getId())).thenReturn(servicesPet1);

        mvc.perform(get("/api/v1/pets/" + pet1.getId() + "/services")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(servicesPet1.size())))
                .andExpect(jsonPath("$[0].id", equalTo(service1.getId().intValue())))
                .andExpect(jsonPath("$[0].observation", equalTo(service1.getObservation())))
                .andExpect(jsonPath("$[0].dateHour", equalTo(service1.getDateHour()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))))
                .andExpect(jsonPath("$[0].serviceType", equalTo(service1.getServiceType())))
                .andExpect(jsonPath("$[0].value", equalTo(service1.getValue().intValue())))
                .andExpect(jsonPath("$[0].pet.id", equalTo(service1.getPetResponseDTO().getId().intValue())))
                .andExpect(jsonPath("$[0].pet.name", equalTo(service1.getPetResponseDTO().getName())))
                .andExpect(jsonPath("$[0].pet.birthDate", equalTo(service1.getPetResponseDTO().getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
                .andExpect(jsonPath("$[0].pet.customer.id", equalTo(service1.getPetResponseDTO().getCustomerResponseDTO().getId().intValue())))
                .andExpect(jsonPath("$[0].pet.customer.name", equalTo(service1.getPetResponseDTO().getCustomerResponseDTO().getName())))
                .andExpect(jsonPath("$[0].pet.specie.id", equalTo(service1.getPetResponseDTO().getSpecieResponseDTO().getId().intValue())))
                .andExpect(jsonPath("$[0].pet.specie.name", equalTo(service1.getPetResponseDTO().getSpecieResponseDTO().getName())));
    }

    @Test
    public void checkSaveById() throws Exception {
        mvc.perform(put("/api/v1/pets/" + pet1.getId())
                .contentType(APPLICATION_JSON)
                .content("{ \"name\": \"Bidu\"                 ,"
                        + "  \"birthDate\": \"01/01/2018\" ,"
                        + "  \"customerId\": \"1\"               ,"
                        + "  \"specieId\": \"1\" }"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void checkDeleteById() throws Exception {
        mvc.perform(delete("/api/v1/pets/" + pet1.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
