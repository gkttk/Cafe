package com.github.gkttk.epam.dao.entity;

import com.github.gkttk.epam.dao.Dao;
import com.github.gkttk.epam.dao.extractors.FieldExtractor;
import com.github.gkttk.epam.dao.mappers.RowMapper;
import com.github.gkttk.epam.exceptions.DaoException;
import com.github.gkttk.epam.model.entities.Entity;

import java.sql.*;
import java.util.*;

public abstract class AbstractDao<T extends Entity> implements Dao<T> {

    private final static String COMMON_ID_KEY = "id";
    private final Connection connection;
    private final RowMapper<T> rowMapper;
    private final FieldExtractor<T> fieldExtractor;

    public AbstractDao(Connection connection, RowMapper<T> rowMapper, FieldExtractor<T> fieldExtractor) {
        this.connection = connection;
        this.rowMapper = rowMapper;
        this.fieldExtractor = fieldExtractor;
    }


    @Override
    public List<T> findAll() throws DaoException {
        String query = "SELECT * FROM " + getTableName();
        return getAllResults(query);
    }

    @Override
    public Optional<T> getById(Long id) throws DaoException {
        String query = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        return getSingleResult(query, id);
    }


    private Long getKey(Statement statement) throws SQLException {
        ResultSet generatedKeys = statement.getGeneratedKeys();
        generatedKeys.next();
        return generatedKeys.getLong(1);//todo
    }

    @Override
    public Long save(T entity) throws DaoException {//todo
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
                return -1L; //todo stub
            }
        } catch (SQLException e) {
            throw new DaoException("Can't save entity: " + entity.toString(), e);

        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new DaoException("Can't close statement with entity" + entity.toString(), e);
                }
            }
        }
    }

    @Override
    public void removeById(Long id) throws DaoException {
        String query = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement statement = createPrepareStatement(query, id)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't removeById wit id: " + id, e);
        }
    }


    protected Optional<T> getSingleResult(String query, Object... params) throws DaoException {
        Optional<T> result = Optional.empty();
        try (PreparedStatement prepareStatement = createPrepareStatement(query, params);
             ResultSet resultSet = prepareStatement.executeQuery()) {
            if (resultSet.next()) {
                T entity = rowMapper.map(resultSet);
                result = Optional.of(entity);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't getSingleResult() with query: " + query, e);
        }
        return result;
    }


    protected List<T> getAllResults(String query, Object... params) throws DaoException {
        List<T> results = new ArrayList<>();
        try (PreparedStatement prepareStatement = createPrepareStatement(query, params);
             ResultSet resultSet = prepareStatement.executeQuery()) {
            while (resultSet.next()) {
                T entity = rowMapper.map(resultSet);
                results.add(entity);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't getAllResults() with query: " + query, e);
        }
        return results;
    }


    protected PreparedStatement createPrepareStatement(String query, Object... params) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        fillPreparedStatement(statement, params);
        return statement;
    }

    protected PreparedStatement createPrepareStatementWithGeneratedKey(String query, Object... params) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        fillPreparedStatement(statement, params);
        return statement;
    }


    private void fillPreparedStatement(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            statement.setObject(i + 1, parameters[i]);
        }

    }

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


    protected RowMapper<T> getRowMapper() {
        return rowMapper;
    }

    protected abstract String getTableName();

}
