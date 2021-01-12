package com.github.gkttk.epam.logic.service;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.enums.DishTypes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface DishService {


    List<Dish> getAllEnabled() throws ServiceException;

   /* List<Dish> getAll() throws ServiceException;*/

    List<Dish> getByType(DishTypes type) throws ServiceException;

    Optional<Dish> getDishById(Long dishId) throws ServiceException;

    void addDish(String dishName, BigDecimal dishCost, DishTypes dishType, String byteString) throws ServiceException;

    void disableDish(long dishId) throws ServiceException;
}
