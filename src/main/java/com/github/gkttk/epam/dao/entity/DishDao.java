package com.github.gkttk.epam.dao.entity;

import com.github.gkttk.epam.dao.Dao;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.Dish;

import java.util.List;

/**
 * Dao for working with Dish entity.
 */
public interface DishDao extends Dao<Dish> {

    List<Dish> findAllByType(String dishType) throws DaoException;

    List<Dish> findAllEnabled() throws DaoException;

    void disable(long dishId) throws DaoException;


}
