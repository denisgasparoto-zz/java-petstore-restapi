package com.denisgasparoto.restapi.petstore.entity;

import com.denisgasparoto.restapi.petstore.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Specie extends BaseEntity {

    @Column(length = 50, nullable = false)
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
