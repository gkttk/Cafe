package com.github.gkttk.epam.dao.parsers;

import com.github.gkttk.epam.model.entities.Entity;

import java.util.List;

public interface EntityParser<T extends Entity> {

    List<Object> parse(T entity);

}
