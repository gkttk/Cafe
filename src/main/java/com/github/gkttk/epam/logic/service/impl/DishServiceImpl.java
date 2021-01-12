package com.github.gkttk.epam.logic.service.impl;

import com.github.gkttk.epam.dao.entity.DishDao;
import com.github.gkttk.epam.dao.helper.DaoHelper;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.DishService;
import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.enums.DishTypes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class DishServiceImpl implements DishService {


    @Override
    public List<Dish> getAllEnabled() throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            DishDao dishDao = daoHelper.createDishDao();
            return dishDao.findAllEnabled();
        } catch (DaoException e) {
            throw new ServiceException("Can't getAllEnabled()", e);
        }
    }

/*    @Override
    public List<Dish> getAll() throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            DishDao dishDao = daoHelper.createDishDao();
            return dishDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Can't getAllDishes()", e);
        }
    }*/

    @Override
    public List<Dish> getByType(DishTypes type) throws ServiceException {
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
            return dishDao.findById(dishId);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't getDishById with dishId: %d", dishId), e);
        }
    }

    @Override
    public void addDish(String dishName, BigDecimal dishCost, DishTypes dishType, String imgBase64) throws ServiceException {
        try (DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()) {
            DishDao dishDao = daoHelper.createDishDao();
            Dish newDish = new Dish(dishName,dishType,dishCost,imgBase64);
            dishDao.save(newDish);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't addDish(dishName, dishCost, dishType, imgBase64) with " +
                    "dishName: %s, dishCost: %.4f, dishType: %s, imgBase64 length: %d", dishName, dishCost,
                    dishType.name(), imgBase64.length()), e);
        }
    }

    @Override
    public void disableDish(long dishId) throws ServiceException {
        try(DaoHelper daoHelper = DaoHelperFactory.createDaoHelper()){
            DishDao dishDao = daoHelper.createDishDao();
            dishDao.disable(dishId);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't disableDish(dishId) with dishId: %d", dishId), e);
        }
    }
}
