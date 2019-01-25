package com.denisgasparoto.restapi.petstore.entity;

import com.denisgasparoto.restapi.petstore.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Service extends BaseEntity {

    @Column(length = 500, nullable = false)
    private String observation;

    @Column(nullable = false)
    private LocalDateTime dateHour;

    @Column(nullable = false)
    private int serviceType;

    @Column(nullable = false)
    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

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

    public ServiceType getServiceType() {
        return ServiceType.valueOf(serviceType);
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType.getId();
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
