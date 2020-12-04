package com.github.gkttk.epam.dao.helper.factory;

import com.github.gkttk.epam.connection.ConnectionPool;
import com.github.gkttk.epam.connection.ConnectionProxy;
import com.github.gkttk.epam.dao.helper.DaoHelper;

public class DaoHelperFactory {

    private final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

    public DaoHelper createDaoHelper(){
        ConnectionProxy connection = CONNECTION_POOL.getConnection();
        return new DaoHelper(connection);
    }

}
