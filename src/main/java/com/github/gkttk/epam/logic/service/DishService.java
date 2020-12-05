package com.github.gkttk.epam.logic.service;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.entities.Dish;

import java.util.List;

public interface DishService {

    List<Dish> getAllDishes() throws ServiceException;

    List<Dish> getDishesByIds(List<Long> ids) throws ServiceException;

}
