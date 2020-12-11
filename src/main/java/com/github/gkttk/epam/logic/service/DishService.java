package com.github.gkttk.epam.logic.service;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.enums.DishTypes;

import java.util.List;
import java.util.Optional;

public interface DishService {

    List<Dish> getAllDishes() throws ServiceException;

    List<Dish> getDishesByIds(List<Long> ids) throws ServiceException;

    List<Dish> getDishesByType(DishTypes type) throws ServiceException;

    Optional<Dish> getDishById(Long dishId) throws ServiceException;

}
