package com.github.gkttk.epam.logic.service.impl;

import com.github.gkttk.epam.dao.entity.impl.DishDaoImpl;
import com.github.gkttk.epam.dao.helper.DaoHelperImpl;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.entities.Dish;
import com.github.gkttk.epam.model.enums.DishTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class DishServiceImplTest {

    private static DishServiceImpl dishService;
    private static DishDaoImpl dishDaoMock;

    @BeforeEach
    void init() {
        DaoHelperFactory daoHelperFactoryMock = Mockito.mock(DaoHelperFactory.class);
        DaoHelperImpl daoHelperMock = Mockito.mock(DaoHelperImpl.class);
        dishService = new DishServiceImpl(daoHelperFactoryMock);
        dishDaoMock = Mockito.mock(DishDaoImpl.class);

        when(daoHelperFactoryMock.createDaoHelper()).thenReturn(daoHelperMock);
        when(daoHelperMock.createDishDao()).thenReturn(dishDaoMock);
    }

    @Test
    public void testGetAllEnabledShouldReturnDishList() throws ServiceException, DaoException {
        //given
        int expectedSize = 3;
        when(dishDaoMock.findAllEnabled()).thenReturn(Arrays.asList(null, null, null));
        //when
        List<Dish> result = dishService.getAllEnabled();
        //then
        verify(dishDaoMock, times(1)).findAllEnabled();
        Assertions.assertEquals(expectedSize, result.size());
    }

    @Test
    public void testGetAllEnabledShouldThrowExceptionWhenFindAllEnabledAndCantGetAccessToDb() throws DaoException {
        //given
        when(dishDaoMock.findAllEnabled()).thenThrow(new DaoException());
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> dishService.getAllEnabled());
    }

    @Test
    public void testGetByTypeShouldReturnDishList() throws DaoException, ServiceException {
        //given
        int expectedSize = 2;
        DishTypes type = DishTypes.SALAD;
        String typeName = type.name();
        when(dishDaoMock.findDishesByType(typeName)).thenReturn(Arrays.asList(null, null));
        //when
        List<Dish> result = dishService.getByType(type);
        //then
        verify(dishDaoMock, times(1)).findDishesByType(typeName);
        Assertions.assertEquals(expectedSize, result.size());
    }

    @Test
    public void testGetByTypeShouldThrowExceptionWhenFindDishesByTypeAndCantGetAccessToDb() throws DaoException {
        //given
        DishTypes type = DishTypes.SALAD;
        String typeName = type.name();
        when(dishDaoMock.findDishesByType(typeName)).thenThrow(new DaoException());
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> dishService.getByType(type));
    }

    @Test
    public void testGetDishByIdShouldReturnNotEmptyOptional() throws DaoException, ServiceException {
        //given
        long dishId = 1L;
        Optional<Dish> expectedOpt = Optional.of(new Dish(dishId, "testName", DishTypes.SALAD, new BigDecimal("0.1"),
                "imgBase64Test"));
        when(dishDaoMock.findById(dishId)).thenReturn(expectedOpt);
        //when
        Optional<Dish> result = dishService.getDishById(dishId);
        //then
        verify(dishDaoMock).findById(dishId);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(expectedOpt, result);
    }

    @Test
    public void testGetDishByIdShouldReturnEmptyOptional() throws DaoException, ServiceException {
        //given
        long dishId = 1L;
        Optional<Dish> expectedOpt = Optional.empty();
        when(dishDaoMock.findById(dishId)).thenReturn(expectedOpt);
        //when
        Optional<Dish> result = dishService.getDishById(dishId);
        //then
        verify(dishDaoMock).findById(dishId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void testGetDishByIdShouldThrowExceptionWhenFindByIdAndCantGetAccessToDb() throws DaoException {
        //given
        long dishId = 1L;
        when(dishDaoMock.findById(dishId)).thenThrow(new DaoException());
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> dishService.getDishById(dishId));
    }

    @Test
    public void testAddDishShouldInvokeMethods() throws ServiceException, DaoException {
        //given
        String dishName = "testName";
        BigDecimal dishCost = new BigDecimal("0.1");
        DishTypes dishType = DishTypes.BEVERAGE;
        String dishImg = "imgBase64Test";
        Dish dishForDb = new Dish(dishName, dishType, dishCost, dishImg);
        //when
        dishService.addDish(dishName, dishCost, dishType, dishImg);
        //then
        verify(dishDaoMock).save(dishForDb);
    }

    @Test
    public void testAddDishShouldThrowExceptionWhenSaveAndCantGetAccessToDb() throws DaoException {
        //given
        String dishName = "testName";
        BigDecimal dishCost = new BigDecimal("0.1");
        DishTypes dishType = DishTypes.BEVERAGE;
        String dishImg = "imgBase64Test";
        Dish dishForDb = new Dish(dishName, dishType, dishCost, dishImg);
        when(dishDaoMock.save(dishForDb)).thenThrow(new DaoException());
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> dishService.addDish(dishName, dishCost, dishType, dishImg));
    }

    @Test
    public void testDisableDishShouldInvokeMethods() throws ServiceException, DaoException {
        //given
        long dishId = 1L;
        //when
        dishService.disableDish(dishId);
        //then
        verify(dishDaoMock).disable(dishId);
    }

    @Test
    public void testDisableDishShouldThrowExceptionWhenDisableAndCantGetAccessToDb() throws DaoException {
        //given
        long dishId = 1L;
        doThrow(new DaoException()).when(dishDaoMock).disable(dishId);
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> dishService.disableDish(dishId));
    }


}
