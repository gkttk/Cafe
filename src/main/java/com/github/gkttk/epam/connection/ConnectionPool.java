package com.github.gkttk.epam.connection;

import com.github.gkttk.epam.exceptions.ConnectionFactoryException;
import com.github.gkttk.epam.exceptions.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static ConnectionPool instance;
    private final static Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private final static AtomicBoolean isCreated = new AtomicBoolean(false);
    private final static Lock INSTANCE_LOCK = new ReentrantLock();

    private final static String CONFIG_LOCATION = "config/connection_pool.properties";
    private final static String MAX_INIT_CONNECTIONS_KEY = "pool.init.connection";

    private final ConnectionFactory connectionFactory;
    private final Semaphore connectionSemaphore;
    private Queue<ConnectionProxy> availableConnections;
    private Queue<ConnectionProxy> usedConnections;
    private int maxInitConnections;

    private ConnectionPool() {
        loadProperties();
        connectionSemaphore = new Semaphore(maxInitConnections);
        connectionFactory = new ConnectionFactory();
        availableConnections = new ArrayDeque<>(maxInitConnections);
        usedConnections = new ArrayDeque<>(maxInitConnections);

        for (int i = 0; i < maxInitConnections; i++) {
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
                    isCreated.compareAndSet(false, true);
                }
            } finally {
                INSTANCE_LOCK.unlock();
            }
        }
        return instance;
    }

    public ConnectionProxy getConnection() {
        ConnectionProxy connection;
        try {
            connectionSemaphore.acquire();
            if (availableConnections.size() > 0) {
                connection = availableConnections.poll();
            } else {
                connection = createConnection();
            }
            usedConnections.offer(connection);
        } catch (InterruptedException e) {
            LOGGER.error("Thread was interrupted, can't get acquire.");
            throw new ConnectionFactoryException("Current thread was interrupted");
        }

        return connection;
    }

    public void releaseConnection(ConnectionProxy connection) {
        if (usedConnections.contains(connection)) {
            usedConnections.remove(connection);
            availableConnections.offer(connection);
            connectionSemaphore.release();
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
            throw new ConnectionPoolException("Can't destroy connection pool", e);
        }
    }

    private void loadProperties() {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(CONFIG_LOCATION)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            maxInitConnections = Integer.parseInt(properties.getProperty(MAX_INIT_CONNECTIONS_KEY));
        } catch (FileNotFoundException e) {
            LOGGER.error("File {} not found.", CONFIG_LOCATION, e);
            throw new ConnectionFactoryException("File " + CONFIG_LOCATION + " not found.", e);
        } catch (IOException ex) {
            LOGGER.error("Can't read {} file.", CONFIG_LOCATION, ex);
            throw new ConnectionFactoryException("Can't read " + CONFIG_LOCATION + " file.", ex);
        } catch (NumberFormatException exception) {
            throw new ConnectionFactoryException("MaxInitConnections property can't be parse to int in " +
                    CONFIG_LOCATION + " file.", exception);
        }
    }

    private ConnectionProxy createConnection() {
        Connection connection = connectionFactory.createConnection();
        return new ConnectionProxy(connection);
    }


}
