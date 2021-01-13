package com.github.gkttk.epam.logic.service.impl;

import com.github.gkttk.epam.dao.dto.impl.UserInfoDaoImpl;
import com.github.gkttk.epam.dao.entity.impl.UserDaoImpl;
import com.github.gkttk.epam.dao.helper.DaoHelperImpl;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.dto.UserInfo;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.UserRole;
import com.github.gkttk.epam.model.enums.UserStatus;
import com.tngtech.java.junit.dataprovider.DataProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    private static UserServiceImpl userService;
    private static UserDaoImpl userDaoMock;
    private static UserInfoDaoImpl userInfoDaoMock;

    private final static User TEST_USER = new User(1L, "testLogin", "testPassword", UserRole.USER,
            50, new BigDecimal(25), false, "imgBase64Test");

    private final static UserInfo TEST_USER_INFO = new UserInfo(TEST_USER.getId(), TEST_USER.getLogin(),
            TEST_USER.getRole(), TEST_USER.getPoints(), TEST_USER.isBlocked());

    @BeforeEach
    void init() {
        DaoHelperFactory daoHelperFactoryMock = Mockito.mock(DaoHelperFactory.class);
        DaoHelperImpl daoHelperMock = Mockito.mock(DaoHelperImpl.class);
        userService = new UserServiceImpl(daoHelperFactoryMock);
        userDaoMock = Mockito.mock(UserDaoImpl.class);
        userInfoDaoMock = Mockito.mock(UserInfoDaoImpl.class);

        when(daoHelperFactoryMock.createDaoHelper()).thenReturn(daoHelperMock);
        when(daoHelperMock.createUserDao()).thenReturn(userDaoMock);
        when(daoHelperMock.createUserInfoDao()).thenReturn(userInfoDaoMock);
    }


    @Test
    public void testLoginShouldReturnOptionalWithUserWhenSuchUserExistsInDb() throws ServiceException, DaoException {
        //given
        String login = TEST_USER.getLogin();
        String password = TEST_USER.getPassword();

        when(userDaoMock.findByLoginAndPassword(login, password)).thenReturn(Optional.of(TEST_USER));
        //when
        Optional<User> resultOpt = userService.login(login, password);
        //then
        verify(userDaoMock).findByLoginAndPassword(login, password);
        Assertions.assertTrue(resultOpt.isPresent());
        resultOpt.ifPresent(user -> {
            Assertions.assertEquals(login, user.getLogin());
            Assertions.assertEquals(password, user.getPassword());
        });
    }

    @Test
    public void testLoginShouldReturnEmptyOptionalWhenSuchUserDoesntExistInDb() throws ServiceException, DaoException {
        //given
        String login = TEST_USER.getLogin();
        String password = TEST_USER.getPassword();
        when(userDaoMock.findByLoginAndPassword(login, password)).thenReturn(Optional.empty());
        //when
        Optional<User> resultOpt = userService.login(login, password);
        //then
        verify(userDaoMock).findByLoginAndPassword(login, password);
        Assertions.assertFalse(resultOpt.isPresent());
    }

    @Test
    public void testLoginShouldThrowExceptionWhenCantGetAccessToDb() throws DaoException {
        //given
        String login = TEST_USER.getLogin();
        String password = TEST_USER.getPassword();
        when(userDaoMock.findByLoginAndPassword(login, password)).thenThrow(new DaoException());
        //when
        //then
        Assertions.assertThrows(ServiceException.class,
                () -> userService.login(login, password));
        verify(userDaoMock).findByLoginAndPassword(login, password);
    }


    @Test
    public void testGetByIdShouldReturnOptionalWithUserWhenSuchUserExistsInDb() throws ServiceException, DaoException {
        //given
        long userId = TEST_USER.getId();
        when(userDaoMock.findById(userId)).thenReturn(Optional.of(TEST_USER));
        //when
        Optional<User> result = userService.getById(userId);
        //then
        verify(userDaoMock).findById(userId);
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void testGetByIdShouldReturnEmptyOptionalWhenSuchUserDoesntExistInDb() throws ServiceException, DaoException {
        //given
        long userId = TEST_USER.getId();
        when(userDaoMock.findById(userId)).thenReturn(Optional.empty());
        //when
        Optional<User> result = userService.getById(userId);
        //then
        verify(userDaoMock).findById(userId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void testGetByIdShouldThrowExceptionWhenCantGetAccessToDb() throws DaoException {
        //given
        long userId = TEST_USER.getId();
        when(userDaoMock.findById(userId)).thenThrow(new DaoException());
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> userService.getById(userId));
        verify(userDaoMock).findById(userId);
    }

    @Test
    public void testGetAllShouldReturnUserInfoList() throws DaoException, ServiceException {
        //given
        int expectedSize = 3;
        when(userInfoDaoMock.findAll()).thenReturn(Arrays.asList(null, null, null));
        //when
        List<UserInfo> result = userService.getAll();
        //then
        verify(userInfoDaoMock).findAll();
        Assertions.assertEquals(expectedSize, result.size());
    }

    @Test
    public void testGetAllShouldThrowExceptionWhenCantGetAccessToDb() throws DaoException {
        //given
        when(userInfoDaoMock.findAll()).thenThrow(new DaoException());
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> userService.getAll());
        verify(userInfoDaoMock).findAll();
    }

    @Test
    public void testChangeUserStatusShouldChangeUserStatusAndReturnOptionalWithUserInfo() throws DaoException, ServiceException {
        //given
        long userId = TEST_USER.getId();
        boolean differentStatus = !TEST_USER.isBlocked();

        UserInfo changedUserInfo = new UserInfo(TEST_USER.getId(), TEST_USER.getLogin(),
                TEST_USER.getRole(), TEST_USER.getPoints(), differentStatus);

        when(userInfoDaoMock.findById(userId)).thenReturn(Optional.of(TEST_USER_INFO));
        //when
        Optional<UserInfo> result = userService.changeUserStatus(userId, differentStatus);
        //then
        verify(userInfoDaoMock).findById(userId);
        verify(userInfoDaoMock).save(changedUserInfo);
        Assertions.assertTrue(result.isPresent());
        result.ifPresent(userInfo -> Assertions.assertNotEquals(TEST_USER.isBlocked(), userInfo.isBlocked()));
    }

    @Test
    public void testChangeUserStatusShouldReturnEmptyOptionalWithUserInfoWhenUserStatusesAreEqual()
            throws DaoException, ServiceException {
        //given
        long userId = TEST_USER.getId();
        boolean equalStatus = TEST_USER.isBlocked();
        when(userInfoDaoMock.findById(userId)).thenReturn(Optional.empty());
        //when
        Optional<UserInfo> result = userService.changeUserStatus(userId, equalStatus);
        //then
        verify(userInfoDaoMock).findById(userId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void testChangeUserStatusShouldThrowExceptionWhenCantGetAccessToDb() throws DaoException {
        //given
        long userId = TEST_USER.getId();
        boolean status = TEST_USER.isBlocked();
        when(userInfoDaoMock.findById(userId)).thenThrow(new DaoException());
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> userService.changeUserStatus(userId, status));
        verify(userInfoDaoMock).findById(userId);
    }

    @Test
    public void testRegistrationShouldReturnTrueWhenCouldSaveUser() throws DaoException, ServiceException {
        //given
        String login = TEST_USER.getLogin();
        String password = TEST_USER.getPassword();
        User userForDb = new User(login, password);
        //when
        boolean result = userService.registration(login, password);
        //then
        verify(userDaoMock).save(userForDb);
        Assertions.assertTrue(result);
    }

    @Test
    public void testRegistrationShouldThrowExceptionWhenCantGetAccessToDb() throws DaoException {
        //given
        String login = TEST_USER.getLogin();
        String password = TEST_USER.getPassword();
        when(userDaoMock.save(any())).thenThrow(new DaoException());
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> userService.registration(login, password));
        verify(userDaoMock).save(any());
    }

    @Test
    public void testChangeAvatarShouldChangeUserAvatarAndInvokeMethods() throws DaoException, ServiceException {
        //given
        String newAvatarBase64 = "newAvatarTest";
        User changedUser = new User(1L, "testLogin", "testPassword", UserRole.USER,
                50, new BigDecimal(25), false, newAvatarBase64);
        //when
        userService.changeAvatar(TEST_USER, newAvatarBase64);
        //then
        verify(userDaoMock).save(changedUser);
    }

    @Test
    public void testChangeAvatarShouldThrowExceptionWhenCantGetAccessToDb() throws DaoException {
        //given
        String newAvatarBase64 = "newAvatarTest";
        when(userDaoMock.save(any())).thenThrow(new DaoException());
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> userService.changeAvatar(TEST_USER, newAvatarBase64));
        verify(userDaoMock).save(any());
    }

    @ParameterizedTest
    @MethodSource("parameterTestGetByStatusShouldReturnUserInfoListProvider")
    public void testGetByStatusShouldReturnUserInfoList(UserStatus status) throws DaoException, ServiceException {
        //given
        int expectedSize = 3;
        boolean statusBoolean = status.isBlocked();
        when(userInfoDaoMock.findAllByStatus(statusBoolean)).thenReturn(Arrays.asList(null, null, null));
        //when
        List<UserInfo> result = userService.getByStatus(status);
        //then
        verify(userInfoDaoMock).findAllByStatus(statusBoolean);
        Assertions.assertEquals(expectedSize, result.size());

    }

    @ParameterizedTest
    @MethodSource("parameterTestGetByStatusShouldReturnUserInfoListProvider")
    public void testGetByStatusShouldThrowExceptionWhenCantGetAccessToDb(UserStatus status) throws DaoException {
        //given
        boolean statusBoolean = status.isBlocked();
        when(userInfoDaoMock.findAllByStatus(statusBoolean)).thenThrow(new DaoException());
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> userService.getByStatus(status));
        verify(userInfoDaoMock).findAllByStatus(statusBoolean);
    }

    @DataProvider
    public static Object[][] parameterTestGetByStatusShouldReturnUserInfoListProvider() {
        return new Object[][]{
                {UserStatus.ACTIVE},
                {UserStatus.BLOCKED}
        };
    }

    @ParameterizedTest
    @MethodSource("parameterChangePointsShouldChangePointsIfUserExistsInDbProvider")
    public void testChangePointsShouldChangePointsIfUserExistsInDb(boolean isAddPoints)
            throws DaoException, ServiceException {
        //given
        long userId = TEST_USER.getId();
        int points = TEST_USER.getPoints();

        int newUserPoints = isAddPoints ? TEST_USER.getPoints() + points : TEST_USER.getPoints() - points;
        UserInfo changedUserInfo = new UserInfo(1L, "testLogin", UserRole.USER, newUserPoints, false);

        when(userInfoDaoMock.findById(userId)).thenReturn(Optional.of(TEST_USER_INFO));
        //when
        userService.changePoints(userId, points, isAddPoints);
        //then
        verify(userInfoDaoMock).findById(userId);
        verify(userInfoDaoMock).save(changedUserInfo);
        Assertions.assertNotEquals(changedUserInfo.getPoints(), TEST_USER_INFO.getPoints());
    }

    @ParameterizedTest
    @MethodSource("parameterChangePointsShouldChangePointsIfUserExistsInDbProvider")
    public void testChangePointsShouldDoNothingIfUserDoesntExistInDb(boolean isAddPoints)
            throws DaoException, ServiceException {
        //given
        long incorrectId = -100L;
        int points = TEST_USER.getPoints();
        when(userInfoDaoMock.findById(incorrectId)).thenReturn(Optional.empty());
        //when
        userService.changePoints(incorrectId, points, isAddPoints);
        //then
        verify(userInfoDaoMock).findById(incorrectId);
    }

    @ParameterizedTest
    @MethodSource("parameterChangePointsShouldChangePointsIfUserExistsInDbProvider")
    public void testChangePointsShouldThrowExceptionWhenCantGetAccessToDb(boolean isAddPoints) throws DaoException {
        //given
        long userId = TEST_USER.getId();
        int points = TEST_USER.getPoints();
        when(userInfoDaoMock.findById(userId)).thenThrow(new DaoException());
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> userService.changePoints(userId, points, isAddPoints));
        verify(userInfoDaoMock).findById(userId);
    }


    @DataProvider
    public static Object[][] parameterChangePointsShouldChangePointsIfUserExistsInDbProvider() {
        return new Object[][]{
                {true},
                {false}
        };
    }

    @Test
    public void testAddMoneyShouldAddMoneyToUserAndInvokeMethods() throws DaoException, ServiceException {
        //given
        long userId = TEST_USER.getId();
        BigDecimal extraMoney = new BigDecimal(20);

        BigDecimal newUserMoney = TEST_USER.getMoney().add(extraMoney);
        User changedUser = new User(TEST_USER.getId(), TEST_USER.getLogin(), TEST_USER.getPassword(), TEST_USER.getRole(),
                TEST_USER.getPoints(), newUserMoney, TEST_USER.isBlocked(), TEST_USER.getImgBase64());
        when(userDaoMock.findById(userId)).thenReturn(Optional.of(TEST_USER));
        //when
        userService.addMoney(userId, extraMoney);
        //then
        verify(userDaoMock).findById(userId);
        verify(userDaoMock).save(changedUser);
        Assertions.assertNotEquals(changedUser.getMoney(), TEST_USER.getMoney());
    }

    @Test
    public void testAddMoneyShouldDoNothingIfUserWithGivenIdDoesntExistInDb() throws DaoException, ServiceException {
        //given
        long incorrectId = -100L;
        BigDecimal extraMoney = new BigDecimal(20);

        when(userDaoMock.findById(incorrectId)).thenReturn(Optional.empty());
        //when
        userService.addMoney(incorrectId, extraMoney);
        //then
        verify(userDaoMock).findById(incorrectId);
        verify(userDaoMock, never()).save(any());
    }

    @Test
    public void testAddMoneyShouldThrowExceptionWhenCantGetAccessToDb() throws DaoException {
        //given
        long userId = TEST_USER.getId();
        BigDecimal extraMoney = new BigDecimal(20);
        when(userDaoMock.findById(userId)).thenThrow(new DaoException());
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> userService.addMoney(userId, extraMoney));
        verify(userDaoMock).findById(userId);
    }


}
