package com.github.gkttk.epam.dao.extractors;

import java.util.Map;

public interface FieldExtractor<T> {

    Map<String, Object> extractFields(T entity);

}
