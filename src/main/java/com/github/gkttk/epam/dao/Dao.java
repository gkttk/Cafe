package com.github.gkttk.epam.dao;

import com.github.gkttk.epam.model.entities.Entity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<T extends Entity> {

    List<T> findAll();
    Optional<T> getById(Long id) throws SQLException;
    void save(T entity);
    void removeById(Long id);


}
