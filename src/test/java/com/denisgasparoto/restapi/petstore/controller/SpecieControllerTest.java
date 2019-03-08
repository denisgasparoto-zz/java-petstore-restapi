package com.denisgasparoto.restapi.petstore.controller;

import com.denisgasparoto.restapi.petstore.dto.response.CustomerResponseDTO;
import com.denisgasparoto.restapi.petstore.dto.response.PetResponseDTO;
import com.denisgasparoto.restapi.petstore.dto.response.SpecieResponseDTO;
import com.denisgasparoto.restapi.petstore.service.PetService;
import com.denisgasparoto.restapi.petstore.service.SpecieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
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
@WebMvcTest(SpecieController.class)
public class SpecieControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SpecieService specieService;

    @MockBean
    private PetService petService;

    private SpecieResponseDTO specie1;
    private SpecieResponseDTO specie2;
    private List<SpecieResponseDTO> species;

    @Before
    public void before() {
        specie1 = new SpecieResponseDTO();
        specie1.setId(1L);
        specie1.setName("Cachorro");

        specie2 = new SpecieResponseDTO();
        specie2.setId(2L);
        specie2.setName("Gato");

        species = Arrays.asList(specie1, specie2);
    }

    @Test
    public void checkSave() throws Exception {
        SpecieResponseDTO specieResponse = new SpecieResponseDTO();
        specieResponse.setId(3L);
        specieResponse.setName("Coelho");

        Mockito.when(specieService.save(Mockito.eq(null), Mockito.any())).thenReturn(specieResponse);

        mvc.perform(post("/api/v1/species").contentType(APPLICATION_JSON).content("{ \"name\": \"Coelho\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void checkFindAll() throws Exception {
        Mockito.when(specieService.findAll()).thenReturn(species);

        mvc.perform(get("/api/v1/species").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(species.size())))
                .andExpect(jsonPath("$[0].id", equalTo(specie1.getId().intValue())))
                .andExpect(jsonPath("$[0].name", equalTo(specie1.getName())))
                .andExpect(jsonPath("$[1].id", equalTo(specie2.getId().intValue())))
                .andExpect(jsonPath("$[1].name", equalTo(specie2.getName())));
    }

    @Test
    public void checkFindById() throws Exception {
        Mockito.when(specieService.findById(specie1.getId())).thenReturn(specie1);

        mvc.perform(get("/api/v1/species/" + specie1.getId()).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(specie1.getId().intValue())))
                .andExpect(jsonPath("$.name", equalTo(specie1.getName())));
    }

    @Test
    public void checkFindPets() throws Exception {
        CustomerResponseDTO customer = new CustomerResponseDTO();
        customer.setId(1L);
        customer.setName("John");

        PetResponseDTO pet1 = new PetResponseDTO();
        pet1.setId(1L);
        pet1.setName("Rex");
        pet1.setCustomerResponseDTO(customer);
        pet1.setBirthDate(LocalDate.parse("2018-01-01"));
        pet1.setSpecieResponseDTO(specie1);

        List<PetResponseDTO> petsSpecie1 = Arrays.asList(pet1);

        Mockito.when(petService.findBySpecie_Id(specie1.getId())).thenReturn(petsSpecie1);

        mvc.perform(get("/api/v1/species/" + specie1.getId() + "/pets").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(petsSpecie1.size())))
                .andExpect(jsonPath("$[0].id", equalTo(pet1.getId().intValue())))
                .andExpect(jsonPath("$[0].name", equalTo(pet1.getName())))
                .andExpect(jsonPath("$[0].birthDate", equalTo(pet1.getBirthDate()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
                .andExpect(jsonPath("$[0].customer.id", equalTo(customer.getId().intValue())))
                .andExpect(jsonPath("$[0].customer.name", equalTo(customer.getName())))
                .andExpect(jsonPath("$[0].specie.id", equalTo(specie1.getId().intValue())))
                .andExpect(jsonPath("$[0].specie.name", equalTo(specie1.getName())));
    }

    @Test
    public void checkSaveById() throws Exception {
        mvc.perform(put("/api/v1/species/" + specie1.getId())
                .contentType(APPLICATION_JSON).content("{ \"name\": \"Peixe\" }"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void checkDeleteById() throws Exception {
        mvc.perform(delete("/api/v1/species/" + specie1.getId()).contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }

}