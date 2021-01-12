package com.github.gkttk.epam.logic.validator;

import com.github.gkttk.epam.dao.entity.UserDao;
import com.github.gkttk.epam.dao.helper.DaoHelper;
import com.github.gkttk.epam.dao.helper.DaoHelperImpl;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactory;
import com.github.gkttk.epam.dao.helper.factory.DaoHelperFactoryImpl;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.model.entities.User;

import java.util.Optional;

public class UserLoginValidator implements Validator {

    private final static String LOGIN_REGEX = "\\D([a-zA-Z1-9]{3,9})";

    private final DaoHelperFactory daoHelperFactory;

    public UserLoginValidator(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    @Override
    public boolean validate(String login) throws ServiceException {
        boolean isLoginCorrect = login.matches(LOGIN_REGEX);
        if (!isLoginCorrect) {
            return false;
        }
        try (DaoHelperImpl daoHelperImpl = daoHelperFactory.createDaoHelper()) {
            UserDao userDao = daoHelperImpl.createUserDao();
            Optional<User> user = userDao.findByLogin(login);
            boolean isUserExists = user.isPresent();
            return !isUserExists;
        } catch (DaoException e) {
            throw new ServiceException(String.format("Can't validate(login) with login: %s", login), e);
        }
    }
}
