package com.github.gkttk.epam.logic.service.impl;

import com.github.gkttk.epam.dao.DishDao;
import com.github.gkttk.epam.dao.helper.DaoHelper;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.DishService;
import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.enums.DishTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Dish> getDishesByIds(List<Long> ids) throws ServiceException {
        List<Dish> dishes = new ArrayList<>();
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            DishDao dishDao = daoHelper.createDishDao();
            dishes = dishDao.findDishesByIds(ids);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't get dishes by ids with ids: %s", ids.toString()), e);
        }

        return dishes;
    }

    @Override
    public List<Dish> getDishesByType(DishTypes type) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            DishDao dishDao = daoHelper.createDishDao();
            String dishTypeName = type.name();
            return dishDao.findDishesByType(dishTypeName);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't getDishesByType with type: %s", type.name()), e);
        }
    }

    @Override
    public Optional<Dish> getDishById(Long dishId) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            DishDao dishDao = daoHelper.createDishDao();
            return dishDao.getById(dishId);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't getDishById with dishId: %d", dishId), e);
        }
    }
}
