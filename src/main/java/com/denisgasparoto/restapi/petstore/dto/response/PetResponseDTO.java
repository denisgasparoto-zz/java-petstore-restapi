package com.denisgasparoto.restapi.petstore.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class PetResponseDTO {

    private Long id;

    private String name;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    @JsonProperty("customer")
    private CustomerResponseDTO customerResponseDTO;

    @JsonProperty("specie")
    private SpecieResponseDTO specieResponseDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public CustomerResponseDTO getCustomerResponseDTO() {
        return customerResponseDTO;
    }

    public void setCustomerResponseDTO(CustomerResponseDTO customerResponseDTO) {
        this.customerResponseDTO = customerResponseDTO;
    }

    public SpecieResponseDTO getSpecieResponseDTO() {
        return specieResponseDTO;
    }

    public void setSpecieResponseDTO(SpecieResponseDTO specieResponseDTO) {
        this.specieResponseDTO = specieResponseDTO;
    }
}
