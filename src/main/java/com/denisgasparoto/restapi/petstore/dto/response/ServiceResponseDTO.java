package com.denisgasparoto.restapi.petstore.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ServiceResponseDTO {

    private Long id;

    private String observation;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dateHour;

    private String serviceType;

    private BigDecimal value;

    @JsonProperty("pet")
    private PetResponseDTO petResponseDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public LocalDateTime getDateHour() {
        return dateHour;
    }

    public void setDateHour(LocalDateTime dateHour) {
        this.dateHour = dateHour;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public PetResponseDTO getPetResponseDTO() {
        return petResponseDTO;
    }

    public void setPetResponseDTO(PetResponseDTO petResponseDTO) {
        this.petResponseDTO = petResponseDTO;
    }
}
