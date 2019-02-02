package com.denisgasparoto.restapi.petstore.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SpecieRequestDTO {

    @NotNull(message = "O preenchimento da descrição é obrigatório.")
    @Size(max = 100, message = "Limite de caracteres (100) atingido.")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
