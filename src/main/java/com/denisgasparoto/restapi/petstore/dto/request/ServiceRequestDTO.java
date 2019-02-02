package com.denisgasparoto.restapi.petstore.dto.request;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ServiceRequestDTO {

    @NotNull(message = "O preenchimento da observação é obrigatório.")
    private String observation;

    @NotNull(message = "O preenchimento do tipo de serviço é obrigatório.")
    private int serviceTypeId;

    @NotNull(message = "O preenchimento do valor é obrigatório.")
    private BigDecimal value;

    @NotNull(message = "O preenchimento do Pet é obrigatório.")
    private Long petId;

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }
}