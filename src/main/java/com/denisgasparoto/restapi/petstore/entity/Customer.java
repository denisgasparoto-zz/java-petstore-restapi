package com.denisgasparoto.restapi.petstore.entity;

import com.denisgasparoto.restapi.petstore.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Customer extends BaseEntity {

    @Column(length = 100, nullable = false)
    private String name;

    @OneToMany(mappedBy = "customer")
    private List<Pet> pets;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
