package com.github.gkttk.epam.logic.validator;

import com.github.gkttk.epam.dao.entity.impl.UserDaoImpl;
import com.github.gkttk.epam.dao.helper.DaoHelperImpl;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class UserLoginValidatorTest {

    private static UserDaoImpl userDaoMock;
    private static Validator userLoginValidator;

    @BeforeAll
    static void init() {
        DaoHelperFactory daoHelperFactoryMock = Mockito.mock(DaoHelperFactory.class);
        DaoHelperImpl daoHelperMock = Mockito.mock(DaoHelperImpl.class);
        userDaoMock = Mockito.mock(UserDaoImpl.class);
        userLoginValidator = new UserLoginValidator(daoHelperFactoryMock);
        when(daoHelperFactoryMock.createDaoHelper()).thenReturn(daoHelperMock);
        when(daoHelperMock.createUserDao()).thenReturn(userDaoMock);
    }

    @Test
    public void testValidateShouldReturnTrueIfGivenStringMatchesPatternAndSuchLoginIsNotExistInDb() throws ServiceException, DaoException {
        //given
        String login = "testLogin";
        Optional<User> emptyUserOpt = Optional.empty();
        when(userDaoMock.findByLogin(login)).thenReturn(emptyUserOpt);
        //when
        boolean result = userLoginValidator.validate(login);
        //then
        Assertions.assertTrue(result);
    }

    @Test
    public void testValidateShouldReturnFalseIfGivenStringMatchesPatternAndSuchLoginIsExistInDb() throws ServiceException, DaoException {
        //given
        String login = "testLogin";
        String password = "testPassword";
        Optional<User> userOpt = Optional.of(new User(login, password));
        when(userDaoMock.findByLogin(login)).thenReturn(userOpt);
        //when
        boolean result = userLoginValidator.validate(login);
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testValidateShouldReturnFalseIfGivenStringNotMatchesPattern() throws ServiceException {
        //given
        String login = "123NotMatchesString";
        //when
        boolean result = userLoginValidator.validate(login);
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testValidateShouldReturnFalseIfGivenStringIsNull() throws ServiceException {
        //given
        String login = null;
        //when
        boolean result = userLoginValidator.validate(login);
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testValidateShouldThrowAnExceptionWhenCantGetUserFromDb() throws ServiceException, DaoException {
        //given
        String login = "testLogin";
        when(userDaoMock.findByLogin(login)).thenThrow(DaoException.class);
        //when
        //then
        Assertions.assertThrows(ServiceException.class, () -> userLoginValidator.validate(login));
    }


}
