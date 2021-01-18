package com.github.gkttk.epam.dao.entity;

import com.github.gkttk.epam.dao.Dao;
import com.github.gkttk.epam.dao.extractors.FieldExtractor;
import com.github.gkttk.epam.dao.mappers.RowMapper;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;


/**
 * Common abstract dao for entities.
 */
public abstract class AbstractDao<T extends Entity> implements Dao<T> {

    private final static Logger LOGGER = LogManager.getLogger(AbstractDao.class);
    private final static String COMMON_ID_KEY = "id";
    private final Connection connection;
    private final RowMapper<T> rowMapper;
    private final FieldExtractor<T> fieldExtractor;

    public AbstractDao(Connection connection, RowMapper<T> rowMapper, FieldExtractor<T> fieldExtractor) {
        this.connection = connection;
        this.rowMapper = rowMapper;
        this.fieldExtractor = fieldExtractor;
    }


    /**
     * Return list of results depending on the query and parameters.
     */
    protected List<T> getAllResults(String query, Object... params) throws DaoException {
        List<T> results = new ArrayList<>();
        try (PreparedStatement prepareStatement = createPrepareStatement(query, params);
             ResultSet resultSet = prepareStatement.executeQuery()) {
            while (resultSet.next()) {
                T entity = rowMapper.map(resultSet);
                results.add(entity);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't getAllResults(query, ...params) with query: " + query, e);
        }
        return results;
    }

    /**
     * Return list of all entities.
     */
    @Override
    public List<T> findAll() throws DaoException {
        String query = "SELECT * FROM " + getTableName();
        return getAllResults(query);
    }

    /**
     * Return count of rows depending on the query and parameters.
     */
    protected int rowCount(String query, Object... params) throws DaoException {
        try (PreparedStatement prepareStatement = createPrepareStatement(query, params);
             ResultSet resultSet = prepareStatement.executeQuery()) {
            return resultSet.next() ? resultSet.getInt(1) : 0;

        } catch (SQLException e) {
            throw new DaoException("Can't get rowCount(query, ...params) with " + query, e);
        }
    }

    /**
     * Return entity by id.
     */
    @Override
    public Optional<T> findById(long id) throws DaoException {
        String query = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        return getSingleResult(query, id);
    }

    /**
     * Save or Update entity in db. If entity's id == null then current method saves it to db, otherwise current method
     * updates existed entity in db.
     *
     * @return id of saved/updated entity.
     */
    @Override
    public long save(T entity) throws DaoException {
        Map<String, Object> entityFields = fieldExtractor.extractFields(entity);
        Long id = entity.getId();
        String query;

        Object[] entityFieldValues = entityFields.values().stream().skip(1).toArray();
        PreparedStatement statement = null;
        try {
            /*insert*/
            if (id == null) {
                query = getSaveQuery(entityFields);
                statement = createPrepareStatementWithGeneratedKey(query, entityFieldValues);
                statement.executeUpdate();
                return getKey(statement);
                /*update*/
            } else {
                query = getUpdateQuery(entityFields);
                statement = createPrepareStatement(query, entityFieldValues);
                statement.executeUpdate();
                return id;
            }
        } catch (SQLException e) {
            throw new DaoException("Can't save entity: " + entity.toString(), e);

        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    LOGGER.warn("Can't close statement with entity: {}", entity.toString(), e);
                }
            }
        }
    }

    /**
     * Remove entity from db by id.
     */
    @Override
    public void removeById(long id) throws DaoException {
        String query = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement statement = createPrepareStatement(query, id)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't removeById with id: " + id, e);
        }
    }

    /**
     * Return optional of entity depending on the query and parameters.
     */
    protected Optional<T> getSingleResult(String query, Object... params) throws DaoException {
        Optional<T> result = Optional.empty();
        try (PreparedStatement prepareStatement = createPrepareStatement(query, params);
             ResultSet resultSet = prepareStatement.executeQuery()) {
            if (resultSet.next()) {
                T entity = rowMapper.map(resultSet);
                result = Optional.of(entity);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't getSingleResult(query, ...params) with query: " + query, e);
        }
        return result;
    }

    /**
     * Create prepare statement by query and fill it by given parameters.
     */
    protected PreparedStatement createPrepareStatement(String query, Object... params) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        fillPreparedStatement(statement, params);
        return statement;
    }

    /**
     * Create prepare statement with generated keys by query and fill it by given parameters.
     */
    protected PreparedStatement createPrepareStatementWithGeneratedKey(String query, Object... params) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        fillPreparedStatement(statement, params);
        return statement;
    }

    protected abstract String getTableName();

    /**
     * Fills given prepared statement by given parameters.
     */
    private void fillPreparedStatement(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            statement.setObject(i + 1, parameters[i]);
        }

    }

    /**
     * Generates save query by map with column name : value.
     */
    private String getSaveQuery(Map<String, Object> entityFields) {
        StringBuilder sbQuery = new StringBuilder();
        sbQuery.append("INSERT INTO ").append(getTableName());

        StringJoiner columnNames = new StringJoiner(",", "(", ")");
        StringJoiner values = new StringJoiner(",", "(", ")");

        Set<String> keys = entityFields.keySet();
        keys.stream().skip(1).forEach(columnNames::add);

        sbQuery.append(columnNames.toString());
        sbQuery.append(" ").append("values");

        long count = keys.size();
        for (int i = 1; i < count; i++) {
            values.add("?");
        }

        sbQuery.append(values.toString());

        return sbQuery.toString();
    }

    /**
     * Generates update query by map with column name : value.
     */
    private String getUpdateQuery(Map<String, Object> entityFields) {
        StringBuilder sbQuery = new StringBuilder();
        sbQuery.append("UPDATE ").append(getTableName()).append(" ").append("SET");

        StringJoiner joiner = new StringJoiner(",", " ", " ");

        Set<String> keys = entityFields.keySet();

        keys.stream().skip(1).forEach(key -> joiner.add(key + " = ?"));

        sbQuery.append(joiner.toString());
        Object id = entityFields.get(COMMON_ID_KEY);
        sbQuery.append("WHERE id = ").append(id);

        return sbQuery.toString();
    }

    private long getKey(Statement statement) throws SQLException {
        ResultSet generatedKeys = statement.getGeneratedKeys();
        generatedKeys.next();
        return generatedKeys.getLong(1);
    }


}
