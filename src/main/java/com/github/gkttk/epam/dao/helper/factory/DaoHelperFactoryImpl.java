package com.github.gkttk.epam.dao.helper.factory;

import com.github.gkttk.epam.connection.ConnectionPool;
import com.github.gkttk.epam.connection.ConnectionProxy;
import com.github.gkttk.epam.dao.helper.DaoHelperImpl;

public class DaoHelperFactoryImpl implements DaoHelperFactory{

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

    public DaoHelperImpl createDaoHelper(){
        ConnectionProxy connection = CONNECTION_POOL.getConnection();
        return new DaoHelperImpl(connection);
    }

}
