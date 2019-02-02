package com.denisgasparoto.restapi.petstore.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class PetRequestDTO {

    @NotNull(message = "O preenchimento do nome é obrigatório.")
    @Size(max = 100, message = "Limite de caracteres (100) atingido.")
    private String name;

    @NotNull(message = "O preenchimento da data de nascimento é obrigatório.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    @NotNull(message = "O preenchimento do Cliente é obrigatório.")
    private Long customerId;

    @NotNull(message = "O preenchimento do Espécie é obrigatório.")
    private Long specieId;

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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getSpecieId() {
        return specieId;
    }

    public void setSpecieId(Long specieId) {
        this.specieId = specieId;
    }
}