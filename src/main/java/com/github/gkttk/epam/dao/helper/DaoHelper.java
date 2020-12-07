package com.github.gkttk.epam.dao.helper;

import com.github.gkttk.epam.connection.ConnectionProxy;
import com.github.gkttk.epam.dao.DishDao;
import com.github.gkttk.epam.dao.OrderDao;
import com.github.gkttk.epam.dao.impl.DishDaoImpl;
import com.github.gkttk.epam.dao.UserDao;
import com.github.gkttk.epam.dao.impl.OrderDaoImpl;
import com.github.gkttk.epam.dao.impl.UserDaoImpl;
import com.github.gkttk.epam.exceptions.DaoException;

import java.sql.SQLException;

public class DaoHelper implements AutoCloseable {

    private final ConnectionProxy connection;

    public DaoHelper(ConnectionProxy connection) {
        this.connection = connection;
    }

    public OrderDao createOrderDao() {
        return new OrderDaoImpl(connection);
    }

    public UserDao createUserDao() {
        return new UserDaoImpl(connection);
    }

    public DishDao createDishDao() {
        return new DishDaoImpl(connection);
    }


    public void startTransaction() throws DaoException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException("Can't startTransaction()", e);
        }
    }

    public void endTransaction() throws DaoException {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DaoException("Can't endTransaction()", e);
        }
    }

    public void commit() throws DaoException {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException("Can't commit()", e);
        }
    }

    public void rollback() throws DaoException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DaoException("Can't commit()", e);
        }
    }


    @Override
    public void close() {
        connection.close();
    }
}
