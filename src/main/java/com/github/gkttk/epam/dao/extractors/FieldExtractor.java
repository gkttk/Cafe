package com.github.gkttk.epam.dao.extractors;

import com.github.gkttk.epam.model.entities.Entity;

import java.util.Map;

public interface FieldExtractor<T extends Entity> {

    Map<String, Object> extractFields(T entity);

}
