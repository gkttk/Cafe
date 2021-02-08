package com.github.gkttk.epam.logic.service;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.enums.DishType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service for working with Dish entity.
 */
public interface DishService {

    List<Dish> getAllEnabled() throws ServiceException;

    List<Dish> getByType(DishType type) throws ServiceException;

    Optional<Dish> getDishById(long dishId) throws ServiceException;

    void addDish(String dishName, BigDecimal dishCost, DishType dishType, String byteString) throws ServiceException;

    void disableDish(long dishId) throws ServiceException;
}
