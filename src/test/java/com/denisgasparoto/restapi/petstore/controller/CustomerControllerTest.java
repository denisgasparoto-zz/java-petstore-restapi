package com.denisgasparoto.restapi.petstore.controller;

import com.denisgasparoto.restapi.petstore.dto.response.CustomerResponseDTO;
import com.denisgasparoto.restapi.petstore.dto.response.PetResponseDTO;
import com.denisgasparoto.restapi.petstore.dto.response.SpecieResponseDTO;
import com.denisgasparoto.restapi.petstore.service.CustomerService;
import com.denisgasparoto.restapi.petstore.service.PetService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private PetService petService;

    private CustomerResponseDTO customer1;
    private CustomerResponseDTO customer2;
    private List<CustomerResponseDTO> customers;

    @Before
    public void before() {
        customer1 = new CustomerResponseDTO();
        customer1.setId(1L);
        customer1.setName("John");

        customer2 = new CustomerResponseDTO();
        customer2.setId(2L);
        customer2.setName("Maria");

        customers = Arrays.asList(customer1, customer2);
    }

    @Test
    public void checkSave() throws Exception {
        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setId(3L);
        customerResponseDTO.setName("Chad");

        Mockito.when(customerService.save(eq(null), Mockito.any())).thenReturn(customerResponseDTO);

        mvc.perform(post("/api/v1/customers")
                .contentType(APPLICATION_JSON)
                .contentType("{\"name\": \"Chad\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void checkFindAll() throws Exception {
        Mockito.when(customerService.findAll()).thenReturn(customers);

        mvc.perform(get("/api/v1/customers").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(customers.size())))
                .andExpect(jsonPath("$[0].id", equalTo(customer1.getId().intValue())))
                .andExpect(jsonPath("$[0].name", equalTo(customer1.getName())))
                .andExpect(jsonPath("$[1].id", equalTo(customer2.getId().intValue())))
                .andExpect(jsonPath("$[1].name", equalTo(customer2.getName())));
    }

    @Test
    public void checkFindById() throws Exception {
        Mockito.when(customerService.findById(customer1.getId())).thenReturn(customer1);

        mvc.perform(get("/api/v1/customers/" + customer1.getId()).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(customer1.getId().intValue())))
                .andExpect(jsonPath("$.name", equalTo(customer1.getName())));
    }

    @Test
    public void validaFindPets() throws Exception {
        SpecieResponseDTO specie = new SpecieResponseDTO();
        specie.setId(1L);
        specie.setName("Cachorro");

        PetResponseDTO pet1 = new PetResponseDTO();
        pet1.setId(1L);
        pet1.setName("Rex");
        pet1.setCustomerResponseDTO(customer1);
        pet1.setBirthDate(LocalDate.parse("2018-01-01"));
        pet1.setSpecieResponseDTO(specie);

        List<PetResponseDTO> petsCustomer1 = Arrays.asList(pet1);

        Mockito.when(petService.findByCustomer_Id(customer1.getId())).thenReturn(petsCustomer1);

        mvc.perform(get("/api/v1/customers/" + customer1.getId() + "/pets").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(petsCustomer1.size())))
                .andExpect(jsonPath("$[0].id", equalTo(pet1.getId().intValue())))
                .andExpect(jsonPath("$[0].name", equalTo(pet1.getName())))
                .andExpect(jsonPath("$[0].birthDate", equalTo(pet1.getBirthDate()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
                .andExpect(jsonPath("$[0].customer.id", equalTo(customer1.getId().intValue())))
                .andExpect(jsonPath("$[0].customer.name", equalTo(customer1.getName())))
                .andExpect(jsonPath("$[0].specie.id", equalTo(specie.getId().intValue())))
                .andExpect(jsonPath("$[0].specie.name", equalTo(specie.getName())));
    }

    @Test
    public void checkSaveById() throws Exception {
        mvc.perform(put("/api/v1/customers/" + customer1.getId()).contentType(APPLICATION_JSON)
                .content("{ \"name\": \"Johnzinho\" }"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void checkDeleteById() throws Exception {
        mvc.perform(delete("/api/v1/customers/" + customer1.getId()).contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
