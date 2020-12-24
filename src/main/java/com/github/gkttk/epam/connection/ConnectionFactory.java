package com.github.gkttk.epam.connection;

import com.github.gkttk.epam.exceptions.ConnectionFactoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private final static Logger LOGGER = LogManager.getLogger(ConnectionFactory.class);
    private final static String DRIVER_NAME_KEY = "db.driver_name";
    private final static String URL_KEY = "db.url";
    private final static String LOGIN_KEY = "db.login";
    private final static String PASSWORD_KEY = "db.password";
    private final static String CONFIG_LOCATION = "config/mysql.properties";
    private String driverName;
    private String url;
    private String login;
    private String password;

    public ConnectionFactory() {
        loadProperties();
        init();
    }


    private void loadProperties() {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(CONFIG_LOCATION)) {
            Properties properties = new Properties();
            properties.load(inputStream);

            driverName = properties.getProperty(DRIVER_NAME_KEY);
            url = properties.getProperty(URL_KEY);
            login = properties.getProperty(LOGIN_KEY);
            password = properties.getProperty(PASSWORD_KEY);
        } catch (FileNotFoundException e) {
            LOGGER.error("File {} not found.", CONFIG_LOCATION, e);
            throw new ConnectionFactoryException("File " + CONFIG_LOCATION + " not found.", e);
        } catch (IOException e) {
            LOGGER.error("Can't read {} file.", CONFIG_LOCATION, e);
            throw new ConnectionFactoryException("Can't read " + CONFIG_LOCATION + " file.", e);
        }
    }

    private void init() {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Can't load driver with name {} .", driverName, e);
            throw new ConnectionFactoryException("Can't load class with with name " + driverName, e);
        }
    }

    public Connection createConnection() {//todo return Connection(in ConnectionPool wrap into ConnectionProxy)
        try {
            return DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            LOGGER.error("Can't create a connection with url: {}.", url, e);
            throw new ConnectionFactoryException(String.format("Can't create a connection with" +
                    "url: %s", url), e);
        }
    }


}

