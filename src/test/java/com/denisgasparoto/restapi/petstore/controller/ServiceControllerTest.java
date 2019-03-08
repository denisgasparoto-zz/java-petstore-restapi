package com.denisgasparoto.restapi.petstore.controller;

import com.denisgasparoto.restapi.petstore.dto.response.CustomerResponseDTO;
import com.denisgasparoto.restapi.petstore.dto.response.PetResponseDTO;
import com.denisgasparoto.restapi.petstore.dto.response.ServiceResponseDTO;
import com.denisgasparoto.restapi.petstore.dto.response.SpecieResponseDTO;
import com.denisgasparoto.restapi.petstore.entity.ServiceType;
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
@WebMvcTest(ServiceController.class)
public class ServiceControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ServiceService serviceService;

    private ServiceResponseDTO service1;
    private ServiceResponseDTO service2;
    private List<ServiceResponseDTO> services;

    @Before
    public void before() {
        CustomerResponseDTO customer = new CustomerResponseDTO();
        customer.setId(1L);
        customer.setName("John");

        SpecieResponseDTO specie = new SpecieResponseDTO();
        specie.setId(1L);
        specie.setName("Cachorro");

        PetResponseDTO pet = new PetResponseDTO();
        pet.setId(1L);
        pet.setName("Rex");
        pet.setBirthDate(LocalDate.parse("2018-01-01"));
        pet.setCustomerResponseDTO(customer);
        pet.setSpecieResponseDTO(specie);

        service1 = new ServiceResponseDTO();
        service1.setId(1L);
        service1.setObservation("Teste");
        service1.setDateHour(LocalDateTime.parse("2018-01-01T16:11:26.485"));
        service1.setServiceType(ServiceType.CONSULTATION.getDescription());
        service1.setValue(new BigDecimal(80));
        service1.setPetResponseDTO(pet);

        service2 = new ServiceResponseDTO();
        service2.setId(1L);
        service2.setObservation("Teste 2");
        service2.setDateHour(LocalDateTime.parse("2018-01-01T16:11:26.485"));
        service2.setServiceType(ServiceType.VACCINATION.getDescription());
        service2.setValue(new BigDecimal(100));
        service2.setPetResponseDTO(pet);

        services = Arrays.asList(service1, service2);
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
        petResponse = new PetResponseDTO();
        petResponse.setId(1L);
        petResponse.setName("Nina");
        petResponse.setBirthDate(LocalDate.parse("2018-01-01"));
        petResponse.setCustomerResponseDTO(customer);
        petResponse.setSpecieResponseDTO(specie);

        ServiceResponseDTO serviceResponse = new ServiceResponseDTO();
        serviceResponse.setId(1L);
        serviceResponse.setObservation("Teste 2");
        serviceResponse.setDateHour(LocalDateTime.parse("2018-01-01T16:11:26.485"));
        serviceResponse.setServiceType(ServiceType.VACCINATION.getDescription());
        serviceResponse.setValue(new BigDecimal(100));
        serviceResponse.setPetResponseDTO(petResponse);

        Mockito.when(serviceService.save(Mockito.eq(null), Mockito.any())).thenReturn(serviceResponse);

        mvc.perform(post("/api/v1/services").contentType(APPLICATION_JSON).content("{ \"observation\": \"Teste 2\" ,"
                + "  \"idServiceType\": \"4\" 	  ,"
                + "  \"value\": \"100\"          ,"
                + "  \"petId\": \"1\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void checkFindAll() throws Exception {
        Mockito.when(serviceService.findAll()).thenReturn(services);

        mvc.perform(get("/api/v1/services").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(services.size())))
                .andExpect(jsonPath("$[0].id", equalTo(service1.getId().intValue())))
                .andExpect(jsonPath("$[0].observation", equalTo(service1.getObservation())))
                .andExpect(jsonPath("$[0].dateHour", equalTo(service1.getDateHour().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))))
                .andExpect(jsonPath("$[0].serviceType", equalTo(service1.getServiceType())))
                .andExpect(jsonPath("$[0].value", equalTo(service1.getValue().intValue())))
                .andExpect(jsonPath("$[0].pet.id", equalTo(service1.getPetResponseDTO().getId().intValue())))
                .andExpect(jsonPath("$[0].pet.name", equalTo(service1.getPetResponseDTO().getName())))
                .andExpect(jsonPath("$[0].pet.birthDate", equalTo(service1.getPetResponseDTO().getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
                .andExpect(jsonPath("$[0].pet.customer.id", equalTo(service1.getPetResponseDTO().getCustomerResponseDTO().getId().intValue())))
                .andExpect(jsonPath("$[0].pet.customer.name", equalTo(service1.getPetResponseDTO().getCustomerResponseDTO().getName())))
                .andExpect(jsonPath("$[0].pet.specie.id", equalTo(service1.getPetResponseDTO().getSpecieResponseDTO().getId().intValue())))
                .andExpect(jsonPath("$[0].pet.specie.name", equalTo(service1.getPetResponseDTO().getSpecieResponseDTO().getName())))
                .andExpect(jsonPath("$[1].id", equalTo(service2.getId().intValue())))
                .andExpect(jsonPath("$[1].observation", equalTo(service2.getObservation())))
                .andExpect(jsonPath("$[1].dateHour", equalTo(service2.getDateHour().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))))
                .andExpect(jsonPath("$[1].serviceType", equalTo(service2.getServiceType())))
                .andExpect(jsonPath("$[1].value", equalTo(service2.getValue().intValue())))
                .andExpect(jsonPath("$[1].pet.id", equalTo(service2.getPetResponseDTO().getId().intValue())))
                .andExpect(jsonPath("$[1].pet.name", equalTo(service2.getPetResponseDTO().getName())))
                .andExpect(jsonPath("$[1].pet.birthDate", equalTo(service2.getPetResponseDTO().getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
                .andExpect(jsonPath("$[1].pet.customer.id", equalTo(service2.getPetResponseDTO().getCustomerResponseDTO().getId().intValue())))
                .andExpect(jsonPath("$[1].pet.customer.name", equalTo(service2.getPetResponseDTO().getCustomerResponseDTO().getName())))
                .andExpect(jsonPath("$[1].pet.specie.id", equalTo(service2.getPetResponseDTO().getSpecieResponseDTO().getId().intValue())))
                .andExpect(jsonPath("$[1].pet.specie.name", equalTo(service2.getPetResponseDTO().getSpecieResponseDTO().getName())));
    }

    @Test
    public void checkFindById() throws Exception {
        Mockito.when(serviceService.findById(service1.getId())).thenReturn(service1);

        mvc.perform(get("/api/v1/services/" + service1.getId()).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(service1.getId().intValue())))
                .andExpect(jsonPath("$.observation", equalTo(service1.getObservation())))
                .andExpect(jsonPath("$.dateHour", equalTo(service1.getDateHour().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))))
                .andExpect(jsonPath("$.serviceType", equalTo(service1.getServiceType())))
                .andExpect(jsonPath("$.value", equalTo(service1.getValue().intValue())))
                .andExpect(jsonPath("$.pet.id", equalTo(service1.getPetResponseDTO().getId().intValue())))
                .andExpect(jsonPath("$.pet.name", equalTo(service1.getPetResponseDTO().getName())))
                .andExpect(jsonPath("$.pet.birthDate", equalTo(service1.getPetResponseDTO().getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
                .andExpect(jsonPath("$.pet.customer.id", equalTo(service1.getPetResponseDTO().getCustomerResponseDTO().getId().intValue())))
                .andExpect(jsonPath("$.pet.customer.name", equalTo(service1.getPetResponseDTO().getCustomerResponseDTO().getName())))
                .andExpect(jsonPath("$.pet.specie.id", equalTo(service1.getPetResponseDTO().getSpecieResponseDTO().getId().intValue())))
                .andExpect(jsonPath("$.pet.specie.name", equalTo(service1.getPetResponseDTO().getSpecieResponseDTO().getName())));
    }

    @Test
    public void checkSaveById() throws Exception {
        mvc.perform(put("/api/v1/services/" + service1.getId()).contentType(APPLICATION_JSON).content("{ \"observation\": \"Teste 2\" ,"
                + "  \"idServiceType\": \"4\" 	 ,"
                + "  \"value\": \"100\"          ,"
                + "  \"petId\": \"1\" }"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void checkDeleteById() throws Exception {
        mvc.perform(delete("/api/v1/services/" + service1.getId()).contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void checkFindByDate() throws Exception {
        Mockito.when(serviceService.findByDateHourBetween(Mockito.eq(LocalDate.parse("2019-01-01")), Mockito.eq(LocalDate.parse("2019-02-01")))).thenReturn(services);

        mvc.perform(get("/api/v1/services/findByDate?initialDate=01/01/2019&finalDate=01/02/2019").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(services.size())))
                .andExpect(jsonPath("$[0].id", equalTo(service1.getId().intValue())))
                .andExpect(jsonPath("$[0].observation", equalTo(service1.getObservation())))
                .andExpect(jsonPath("$[0].dateHour", equalTo(service1.getDateHour().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))))
                .andExpect(jsonPath("$[0].serviceType", equalTo(service1.getServiceType())))
                .andExpect(jsonPath("$[0].value", equalTo(service1.getValue().intValue())))
                .andExpect(jsonPath("$[0].pet.id", equalTo(service1.getPetResponseDTO().getId().intValue())))
                .andExpect(jsonPath("$[0].pet.name", equalTo(service1.getPetResponseDTO().getName())))
                .andExpect(jsonPath("$[0].pet.birthDate", equalTo(service1.getPetResponseDTO().getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
                .andExpect(jsonPath("$[0].pet.customer.id", equalTo(service1.getPetResponseDTO().getCustomerResponseDTO().getId().intValue())))
                .andExpect(jsonPath("$[0].pet.customer.name", equalTo(service1.getPetResponseDTO().getCustomerResponseDTO().getName())))
                .andExpect(jsonPath("$[0].pet.specie.id", equalTo(service1.getPetResponseDTO().getSpecieResponseDTO().getId().intValue())))
                .andExpect(jsonPath("$[0].pet.specie.name", equalTo(service1.getPetResponseDTO().getSpecieResponseDTO().getName())))
                .andExpect(jsonPath("$[1].id", equalTo(service2.getId().intValue())))
                .andExpect(jsonPath("$[1].observation", equalTo(service2.getObservation())))
                .andExpect(jsonPath("$[1].dateHour", equalTo(service2.getDateHour().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))))
                .andExpect(jsonPath("$[1].serviceType", equalTo(service2.getServiceType())))
                .andExpect(jsonPath("$[1].value", equalTo(service2.getValue().intValue())))
                .andExpect(jsonPath("$[1].pet.id", equalTo(service2.getPetResponseDTO().getId().intValue())))
                .andExpect(jsonPath("$[1].pet.name", equalTo(service2.getPetResponseDTO().getName())))
                .andExpect(jsonPath("$[1].pet.birthDate", equalTo(service2.getPetResponseDTO().getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
                .andExpect(jsonPath("$[1].pet.customer.id", equalTo(service2.getPetResponseDTO().getCustomerResponseDTO().getId().intValue())))
                .andExpect(jsonPath("$[1].pet.customer.name", equalTo(service2.getPetResponseDTO().getCustomerResponseDTO().getName())))
                .andExpect(jsonPath("$[1].pet.specie.id", equalTo(service2.getPetResponseDTO().getSpecieResponseDTO().getId().intValue())))
                .andExpect(jsonPath("$[1].pet.specie.name", equalTo(service2.getPetResponseDTO().getSpecieResponseDTO().getName())));
    }
}