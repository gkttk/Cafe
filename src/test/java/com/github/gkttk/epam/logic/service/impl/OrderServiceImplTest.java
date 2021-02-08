package com.github.gkttk.epam.logic.service.impl;

import com.github.gkttk.epam.dao.entity.DishDao;
import com.github.gkttk.epam.dao.entity.impl.DishDaoImpl;
import com.github.gkttk.epam.dao.entity.impl.OrderDaoImpl;
import com.github.gkttk.epam.dao.entity.impl.UserDaoImpl;
import com.github.gkttk.epam.dao.helper.DaoHelperImpl;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.dto.OrderInfo;
import com.github.gkttk.epam.model.entities.Order;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.OrderSortType;
import com.github.gkttk.epam.model.enums.OrderStatus;
import com.github.gkttk.epam.model.enums.UserRole;
import com.tngtech.java.junit.dataprovider.DataProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class OrderServiceImplTest {
    private static DaoHelperImpl daoHelperMock;
    private static OrderServiceImpl orderService;
    private static OrderDaoImpl orderDaoMock;
    private static UserDaoImpl userDaoMock;
    private static DishDao dishDaoMock;

    private final static int DEFAULT_PENALTY = 10;
    private final static int DEFAULT_BONUS = 15;

    private final static User TEST_USER = new User(3L, "testLogin", UserRole.USER,
            50, new BigDecimal(25), false, "imgBase64Test");

    private final static Order TEST_ORDER = new Order(1L, new BigDecimal(10), LocalDateTime.MAX,
            OrderStatus.ACTIVE, 3L);

    @BeforeEach
    void init() {
        DaoHelperFactory daoHelperFactoryMock = Mockito.mock(DaoHelperFactory.class);
        daoHelperMock = Mockito.mock(DaoHelperImpl.class);
        orderService = new OrderServiceImpl(daoHelperFactoryMock);
        orderDaoMock = Mockito.mock(OrderDaoImpl.class);
        userDaoMock = Mockito.mock(UserDaoImpl.class);
        dishDaoMock = Mockito.mock(DishDaoImpl.class);

        when(daoHelperFactoryMock.createDaoHelper()).thenReturn(daoHelperMock);
        when(daoHelperMock.createOrderDao()).thenReturn(orderDaoMock);
        when(daoHelperMock.createUserDao()).thenReturn(userDaoMock);
        when(daoHelperMock.createDishDao()).thenReturn(dishDaoMock);
    }


    @Test
    public void testGetOrderInfoShouldReturnOptionalWithOrderInfoIfOrderWithGivenIdExistsInDb() throws DaoException, ServiceException {
        //given
        long orderId = 1L;
        BigDecimal orderCost = new BigDecimal(10);
        LocalDateTime orderDate = LocalDateTime.MIN;
        List<String> dishNames = Arrays.asList("name1", "name2");
        Order expectedOrder = new Order(orderId, orderCost, orderDate, 2L);
        OrderInfo expectedOrderInfo = new OrderInfo(orderCost, orderDate, dishNames);

        when(orderDaoMock.findById(orderId)).thenReturn(Optional.of(expectedOrder));
        when(dishDaoMock.findDishNamesByOrderId(orderId)).thenReturn(dishNames);
        //when
        Optional<OrderInfo> resultOpt = orderService.getOrderInfo(orderId);
        //then
        verify(daoHelperMock).createOrderDao();
        verify(daoHelperMock).createDishDao();
        verify(orderDaoMock).findById(orderId);
        verify(dishDaoMock).findDishNamesByOrderId(orderId);

        Assertions.assertTrue(resultOpt.isPresent());
        resultOpt.ifPresent(result -> Assertions.assertEquals(result, expectedOrderInfo));
    }

    @Test
    public void testGetOrderInfoShouldReturnOptionalWithOrderInfoIfOrderWithGivenIdDoesNotExistInDb() throws DaoException, ServiceException {
        //given
        long incorrectId = 1L;
        when(orderDaoMock.findById(incorrectId)).thenReturn(Optional.empty());
        //when
        Optional<OrderInfo> resultOpt = orderService.getOrderInfo(incorrectId);
        //then
        verify(daoHelperMock).createOrderDao();
        verify(daoHelperMock).createDishDao();
        verify(orderDaoMock).findById(incorrectId);

        Assertions.assertFalse(resultOpt.isPresent());
    }

    @Test
    public void testGetOrderInfoShouldThrowExceptionWhenCantGetAccessToDb() throws DaoException {
        //given
        long orderId = 1L;
        when(orderDaoMock.findById(orderId)).thenThrow(new DaoException());
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> orderService.getOrderInfo(orderId));
        verify(daoHelperMock).createOrderDao();
        verify(daoHelperMock).createDishDao();
        verify(orderDaoMock).findById(orderId);
    }


    @Test
    public void testMakeOrderShouldInvokeMethods() throws DaoException, ServiceException {
        //given
        BigDecimal orderCost = new BigDecimal(10);
        LocalDateTime orderDate = LocalDateTime.MIN;
        long userId = 1L;
        List<Long> dishIds = Arrays.asList(2L, 3L, 4L);

        Order orderForDb = new Order(null, orderCost, orderDate, userId);
        long expectedOrderId = 10L;

        when(orderDaoMock.save(orderForDb)).thenReturn(expectedOrderId);
        //when
        orderService.makeOrder(orderCost, orderDate, userId, dishIds);
        //then
        verify(daoHelperMock).startTransaction();
        verify(orderDaoMock).save(orderForDb);

        for (Long dishId : dishIds) {
            verify(orderDaoMock).saveOrderDish(expectedOrderId, dishId);
        }
        verify(daoHelperMock).commit();
        verify(daoHelperMock).endTransaction();
        verify(daoHelperMock).close();
    }


    @Test
    public void testMakeOrderShouldThrowExceptionWhenCantGetAccessToDb() throws DaoException {
        //given
        BigDecimal orderCost = new BigDecimal(10);
        LocalDateTime orderDate = LocalDateTime.MIN;
        long userId = 1L;
        List<Long> dishIds = Arrays.asList(2L, 3L, 4L);
        doThrow(new DaoException()).when(daoHelperMock).startTransaction();
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> orderService.makeOrder(orderCost, orderDate, userId, dishIds));
        verify(daoHelperMock).rollback();
        verify(daoHelperMock).endTransaction();
        verify(daoHelperMock).close();
    }

    @Test
    public void testTakeOrderShouldReturnTrueWhenUserHasEnoughMoney() throws ServiceException, DaoException {
        //given
        BigDecimal userMoney = TEST_USER.getMoney();
        BigDecimal orderCost = TEST_ORDER.getCost();
        BigDecimal newUserMoney = userMoney.subtract(orderCost);
        int newUserPoints = TEST_USER.getPoints() + DEFAULT_BONUS;

        User changedUser = new User(3L, "testLogin", UserRole.USER, newUserPoints,
                newUserMoney, false, "imgBase64Test");

        Order changedOrder = new Order(1L, new BigDecimal(10), LocalDateTime.MAX, OrderStatus.RETRIEVED, 3L);
        //when
        boolean result = orderService.takeOrder(TEST_ORDER, TEST_USER);
        //then
        verify(daoHelperMock).startTransaction();
        verify(orderDaoMock).save(changedOrder);
        verify(userDaoMock).save(changedUser);
        verify(daoHelperMock).commit();
        verify(daoHelperMock).endTransaction();
        verify(daoHelperMock).close();
        Assertions.assertTrue(result);
    }

    @Test
    public void testTakeOrderShouldReturnFalseWhenUserDontHaveEnoughMoney() throws ServiceException {
        //given
        User user = new User(3L, "testLogin", UserRole.USER, 50,
                new BigDecimal(1), false, "imgBase64Test");
        //when
        boolean result = orderService.takeOrder(TEST_ORDER, user);
        //then
        Assertions.assertFalse(result);
    }


    @Test
    public void testTakeOrderShouldThrowExceptionWhenCantGetAccessToDb() throws DaoException {
        //given
        doThrow(new DaoException()).when(daoHelperMock).startTransaction();
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> orderService.takeOrder(TEST_ORDER, TEST_USER));
        verify(daoHelperMock).rollback();
        verify(daoHelperMock).endTransaction();
        verify(daoHelperMock).close();
    }


    @Test
    public void testGetAllActiveWithExpiredDateShouldReturnOrderList() throws ServiceException, DaoException {
        //given
        int expectedSize = 3;
        when(orderDaoMock.findAllActiveWithExpiredDate()).thenReturn(Arrays.asList(null, null, null));
        //when
        List<Order> result = orderService.getAllActiveWithExpiredDate();
        //then
        verify(orderDaoMock).findAllActiveWithExpiredDate();
        Assertions.assertEquals(expectedSize, result.size());
    }

    @Test
    public void testGetAllActiveWithExpiredDateShouldThrowExceptionWhenCantGetAccessToDb() throws DaoException {
        //given
        when(orderDaoMock.findAllActiveWithExpiredDate()).thenThrow(new DaoException());
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> orderService.getAllActiveWithExpiredDate());
    }

    @Test
    public void testBlockOrderShouldInvokeMethods() throws ServiceException, DaoException {
        //given
        Order changedOrder = new Order(1L, new BigDecimal(10), LocalDateTime.MAX, OrderStatus.BLOCKED, 3L);
        Long userId = TEST_ORDER.getUserId();

        int newUserPoints = TEST_USER.getPoints() - DEFAULT_PENALTY;
        User changedUser = new User(3L, "testLogin", UserRole.USER, newUserPoints,
                new BigDecimal(25), false, "imgBase64Test");

        when(userDaoMock.findById(userId)).thenReturn(Optional.of(TEST_USER));
        //when
        orderService.blockOrder(TEST_ORDER);
        //then
        verify(daoHelperMock).startTransaction();
        verify(orderDaoMock).save(changedOrder);
        verify(userDaoMock).save(changedUser);
        verify(daoHelperMock).commit();
    }

    @Test
    public void testBlockOrderShouldThrowExceptionWhenCantGetAccessToDb() throws DaoException {
        //given
        doThrow(new DaoException()).when(daoHelperMock).startTransaction();
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> orderService.blockOrder(TEST_ORDER));
        verify(daoHelperMock).rollback();
        verify(daoHelperMock).endTransaction();
        verify(daoHelperMock).close();
    }

    @Test
    public void testCancelOrderShouldInvokeMethods() throws ServiceException, DaoException {
        //given
        Order changedOrder = new Order(1L, new BigDecimal(10), LocalDateTime.MAX, OrderStatus.CANCELLED, 3L);

        int newUserPoints = TEST_USER.getPoints() - DEFAULT_PENALTY;
        User changedUser = new User(3L, "testLogin", UserRole.USER, newUserPoints,
                new BigDecimal(25), false, "imgBase64Test");
        //when
        orderService.cancelOrder(TEST_ORDER, TEST_USER);
        //then
        verify(daoHelperMock).startTransaction();
        verify(orderDaoMock).save(changedOrder);
        verify(userDaoMock).save(changedUser);
        verify(daoHelperMock).commit();
    }

    @Test
    public void testCancelOrderShouldThrowExceptionWhenCantGetAccessToDb() throws DaoException {
        //given
        doThrow(new DaoException()).when(daoHelperMock).startTransaction();
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> orderService.cancelOrder(TEST_ORDER, TEST_USER));
        verify(daoHelperMock).rollback();
        verify(daoHelperMock).endTransaction();
        verify(daoHelperMock).close();
    }

    @ParameterizedTest
    @MethodSource("parameterTestGetAllActiveByUserIdAndStatusShouldReturnOrdersListProvider")
    public void testGetAllActiveByUserIdAndStatusShouldReturnOrdersList(OrderSortType sortType, int expectedValue)
            throws ServiceException, DaoException {
        //given
        long userId = 1L;
        when(orderDaoMock.findAllNotActiveByUserId(userId)).thenReturn(Arrays.asList(null, null));
        when(orderDaoMock.findAllActiveByUserId(userId)).thenReturn(Arrays.asList(null, null, null));
        //when
        List<Order> result = orderService.getAllActiveByUserIdAndStatus(userId, sortType);
        //then
        Assertions.assertEquals(expectedValue, result.size());


    }

    @DataProvider
    public static Object[][] parameterTestGetAllActiveByUserIdAndStatusShouldReturnOrdersListProvider() {
        return new Object[][]{
                {OrderSortType.ACTIVE, 3},
                {OrderSortType.EXPIRED, 2}

        };
    }

    @Test
    public void testGetAllActiveByUserIdAndStatusShouldThrowExceptionWhenCantGetAccessToDb() throws DaoException {
        //given
        long userId = 1L;
        when(orderDaoMock.findAllActiveByUserId(userId)).thenThrow(new DaoException());
        when(orderDaoMock.findAllNotActiveByUserId(userId)).thenThrow(new DaoException());
        //when
        //then
        for (OrderSortType type : OrderSortType.values()) {
            Assertions.assertThrows(ServiceException.class,
                    () -> orderService.getAllActiveByUserIdAndStatus(userId, type));
        }
        verify(orderDaoMock).findAllActiveByUserId(userId);
        verify(orderDaoMock).findAllNotActiveByUserId(userId);
    }


}
