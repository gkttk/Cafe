package com.github.gkttk.epam.dao.helper;

import com.github.gkttk.epam.connection.ConnectionProxy;
import com.github.gkttk.epam.dao.dto.CommentInfoDao;
import com.github.gkttk.epam.dao.dto.impl.CommentInfoDaoImpl;
import com.github.gkttk.epam.dao.entity.*;
import com.github.gkttk.epam.dao.entity.impl.*;
import com.github.gkttk.epam.exceptions.DaoException;

import java.sql.SQLException;

public class DaoHelper implements AutoCloseable {

    private final ConnectionProxy connection;

    public DaoHelper(ConnectionProxy connection) {
        this.connection = connection;
    }


    public UserCommentRatingDao createUserCommentRatingDao() {
        return new UserCommentRatingDaoImpl(connection);
    }


    public CommentInfoDao createCommentInfoDao(){
        return new CommentInfoDaoImpl(connection);
    }

    public CommentDao createCommentDao() {
        return new CommentDaoImpl(connection);
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
