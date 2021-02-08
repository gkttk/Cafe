package com.github.gkttk.epam.dao.extractors;

import java.util.Map;


/**
 * Implementations of the interface decompose given entity to map with column name : value.
 */
public interface FieldExtractor<T> {

    Map<String, Object> extractFields(T entity);

}
