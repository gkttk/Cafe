package com.github.gkttk.epam.dao;

import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.Dish;

import java.util.List;
import java.util.Optional;

public interface DishDao extends Dao<Dish> {


    List<Dish> findDishesByIds(List<Long> ids) throws DaoException;

    List<Dish> findDishesByOrderId(Long orderId) throws DaoException;

    List<Dish> findDishesByType(String dishType) throws DaoException;




}
