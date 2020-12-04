package com.github.gkttk.epam.logic.service.impl;

import com.github.gkttk.epam.dao.DishDao;
import com.github.gkttk.epam.dao.helper.DaoHelper;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.DishService;
import com.github.gkttk.epam.model.entities.Dish;

import java.util.ArrayList;
import java.util.List;

public class DishServiceImpl implements DishService {


    @Override
    public List<Dish> getAllDishes() throws ServiceException {
        List<Dish> dishes = new ArrayList<>();
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            DishDao dishDao = daoHelper.createDishDao();
            dishes = dishDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Can't getAllDishes()", e);
        }

        return dishes;
    }
}
