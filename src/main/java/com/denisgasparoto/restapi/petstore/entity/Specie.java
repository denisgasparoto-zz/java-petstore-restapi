package com.denisgasparoto.restapi.petstore.entity;

import com.denisgasparoto.restapi.petstore.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Specie extends BaseEntity {

    @Column(length = 50, nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
