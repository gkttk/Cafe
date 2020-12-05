package com.github.gkttk.epam.dao;

import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.Dish;

import java.util.List;

public interface DishDao extends Dao<Dish> {


    List<Dish> findDishesByIds(List<Long> ids) throws DaoException;


}
