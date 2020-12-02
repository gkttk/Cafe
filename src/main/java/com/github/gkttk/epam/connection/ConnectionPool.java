package com.github.gkttk.epam.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static final String URL = "jdbc:mysql://localhost:3306/epam_cafe?serverTimezone=UTC&useSSL=false";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "admin";//todo

    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static final Lock lock = new ReentrantLock();
    private static ConnectionPool instance;
    private ArrayDeque<ConnectionProxy> availableConnections;
    private ArrayDeque<ConnectionProxy> usedConnections;
    private static int MAX_START_CONNECTIONS = 10;

    //todo sqlexception in constructor
    private ConnectionPool() {

        availableConnections = new ArrayDeque<>(MAX_START_CONNECTIONS);
        usedConnections = new ArrayDeque<>(MAX_START_CONNECTIONS);

        for (int i = 0; i < MAX_START_CONNECTIONS; i++) {
            ConnectionProxy connection = createConnection();
            availableConnections.add(connection);
        }
    }


    public static ConnectionPool getInstance() {
        try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
              throw new IllegalStateException("Can't get instance of Connection pool", e);//todo
            }
        if (!isCreated.get()) {
            try {
                lock.lock();
                if (!isCreated.get()) {
                    instance = new ConnectionPool();
                    isCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }


    private ConnectionProxy createConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            return new ConnectionProxy(connection);
        } catch (SQLException e) {
            throw new IllegalStateException("Can't create an instance of ConnectionPool", e);//todo
        }

    }

    public synchronized Connection getConnection() { //todo synchronized
        ConnectionProxy connection;
        if (availableConnections.size() > 0) {
            connection = availableConnections.getLast();
        } else {
            connection = createConnection();
        }
        usedConnections.add(connection);
        return connection;
    }

    public synchronized void releaseConnection(ConnectionProxy connection) {//todo synchronized
        availableConnections.addLast(connection);
        usedConnections.remove(connection);
    }


}
