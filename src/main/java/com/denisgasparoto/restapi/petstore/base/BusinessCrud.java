package com.denisgasparoto.restapi.petstore.base;

import java.util.List;

public interface BusinessCrud<Entity, Id> {

    Entity save(Entity entity);

    List<Entity> findAll();

    Entity findById(Id id);

    void deleteById(Id id);
}
