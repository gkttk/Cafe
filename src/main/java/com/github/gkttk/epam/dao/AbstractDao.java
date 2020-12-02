package com.github.gkttk.epam.dao;

import com.github.gkttk.epam.dao.mappers.RowMapper;
import com.github.gkttk.epam.dao.parsers.EntityParser;
import com.github.gkttk.epam.model.entities.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T extends Entity> implements Dao<T> {

    private final Connection connection;
    private final RowMapper<T> rowMapper;
    private final EntityParser<T> entityParser;

    public AbstractDao(Connection connection, RowMapper<T> rowMapper, EntityParser<T> entityParser) {
        this.connection = connection;
        this.rowMapper = rowMapper;
        this.entityParser = entityParser;
    }


    @Override
    public List<T> findAll() {
        String query = "SELECT * FROM " + getTableName();
        return getAllResults(query);
    }

    @Override
    public Optional<T> getById(Long id) throws SQLException {
        String query = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        return getSingleResult(query, id);
    }

    @Override
    public void save(T entity) {
        List<Object> entityFields = entityParser.parse(entity);
        Long id = entity.getId();
        String query;
        /*insert*/
        if (id == null) {
            query = getSaveQuery();
            /*update*/
        } else {
            query = getUpdateQuery();
            entityFields = entityFields.subList(1, entityFields.size());
            entityFields.add(id);//todo
        }

        try (PreparedStatement statement = createPrepareStatement(query, entityFields)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();//todo
        }
    }

    @Override
    public void removeById(Long id) {
        String query = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement statement = createPrepareStatement(query, id)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();//todo
        }
    }

    protected abstract String getTableName();

    protected abstract String getSaveQuery();

    protected abstract String getUpdateQuery();


    protected Optional<T> getSingleResult(String query, Object... params){
        Optional<T> result = Optional.empty();
        try(PreparedStatement prepareStatement = createPrepareStatement(query, params)) {
            ResultSet resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                T entity = rowMapper.map(resultSet);
                result = Optional.of(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();//todo
        }
        return result;
    }


    protected List<T> getAllResults(String query, Object... params){
        List<T> results = new ArrayList<>();
        try(PreparedStatement prepareStatement = createPrepareStatement(query, params)) {
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                T entity = rowMapper.map(resultSet);
                results.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();//todo
        }
        return results;
    }


    protected PreparedStatement createPrepareStatement(String query, Object... params) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        fillPreparedStatement(statement, params);
        return statement;

    }

    private void fillPreparedStatement(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int i = 1; i <= parameters.length; i++) {
            statement.setObject(i, parameters[i - 1]);
        }
    }
}
