package com.github.gkttk.epam.dao;

import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.Entity;

import java.util.List;
import java.util.Optional;

public interface Dao<T extends Entity> {

    List<T> findAll() throws DaoException;

    Optional<T> findById(long id) throws DaoException;

    long save(T entity) throws DaoException;

    void removeById(long id) throws DaoException;


}
