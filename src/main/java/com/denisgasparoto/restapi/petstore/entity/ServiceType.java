package com.denisgasparoto.restapi.petstore.entity;

public enum ServiceType {

    BATH_CUT(1, "Banho e Tosa"),
    CONSULTATION(2, "Consulta"),
    VACCINATION(3, "Vacinação"),
    SURGERY(4, "Cirurgia");

    private int id;

    private String description;

    ServiceType(int id,
                String description) {
        this.id = id;
        this.description = description;
    }

    public static ServiceType valueOf(int id) {
        for (ServiceType serviceType : values()) {
            if (serviceType.id == id) {
                return serviceType;
            }
        }

        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
