package com.github.gkttk.epam.dao.helper.factory;

import com.github.gkttk.epam.connection.ConnectionPool;
import com.github.gkttk.epam.connection.ConnectionProxy;
import com.github.gkttk.epam.dao.helper.DaoHelper;

public class DaoHelperFactory { //todo static?

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

    public static DaoHelper createDaoHelper(){
        ConnectionProxy connection = CONNECTION_POOL.getConnection();
        return new DaoHelper(connection);
    }

}
