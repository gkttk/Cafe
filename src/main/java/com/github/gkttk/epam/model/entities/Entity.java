package com.github.gkttk.epam.model.entities;

import java.io.Serializable;

public abstract class Entity implements Cloneable, Serializable {

    private final Long id;

    public Entity(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }
}
