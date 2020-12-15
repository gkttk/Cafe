package com.github.gkttk.epam.dao.entity;

import com.github.gkttk.epam.dao.Dao;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.Dish;

import java.util.List;

public interface DishDao extends Dao<Dish> {


    List<Dish> findDishesByIds(List<Long> ids) throws DaoException;

    List<Dish> findDishesByOrderId(Long orderId) throws DaoException;

    List<Dish> findDishesByType(String dishType) throws DaoException;




}
