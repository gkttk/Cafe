package com.github.gkttk.epam.connection;

import com.github.gkttk.epam.exceptions.ConnectionPoolException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static ConnectionPool instance;
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);

    private final ConnectionFactory connectionFactory;

    private static final Lock INSTANCE_LOCK = new ReentrantLock();
    private final Lock connectionLock;


    private Queue<ConnectionProxy> availableConnections;
    private Queue<ConnectionProxy> usedConnections;
    private final static int MAX_INIT_CONNECTIONS = 10;//todo property

    private ConnectionPool() {
        connectionFactory = new ConnectionFactory();
        connectionLock = new ReentrantLock();

        availableConnections = new ArrayDeque<>(MAX_INIT_CONNECTIONS);
        usedConnections = new ArrayDeque<>(MAX_INIT_CONNECTIONS);

        for (int i = 0; i < MAX_INIT_CONNECTIONS; i++) {
            Connection connection = connectionFactory.createConnection();
            ConnectionProxy proxy = new ConnectionProxy(connection);
            availableConnections.add(proxy);
        }
    }


    public static ConnectionPool getInstance() {
        if (!isCreated.get()) {
            try {
                INSTANCE_LOCK.lock();
                if (!isCreated.get()) {
                    instance = new ConnectionPool();
                    isCreated.set(true);
                }
            } finally {
                INSTANCE_LOCK.unlock();
            }
        }
        return instance;
    }


    private ConnectionProxy createConnection() {
        Connection connection = connectionFactory.createConnection();
        return new ConnectionProxy(connection);

    }

    public ConnectionProxy getConnection() {
        ConnectionProxy connection;
        try {
            connectionLock.lock();
            if (availableConnections.size() > 0) {
                connection = availableConnections.poll();
            } else {
                connection = createConnection();
            }
            usedConnections.offer(connection);
        } finally {
            connectionLock.unlock();
        }

        return connection;
    }

    public void releaseConnection(ConnectionProxy connection) {
        if (usedConnections.contains(connection)) {
            try {
                connectionLock.lock();
                availableConnections.offer(connection);
                usedConnections.remove(connection);
            } finally {
                connectionLock.unlock();
            }
        }

    }

    public void destroy() throws ConnectionPoolException {
        try {
            for (ConnectionProxy connectionProxy : availableConnections) {
                connectionProxy.closeConnection();
            }
            for (ConnectionProxy connectionProxy : usedConnections) {
                connectionProxy.closeConnection();
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("Can't close connection", e);
        }
    }


}
