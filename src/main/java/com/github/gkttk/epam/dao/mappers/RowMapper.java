package com.github.gkttk.epam.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementations of the interface decompose ResultSet with data from db and return entity.
 */
public interface RowMapper<T> {

    T map(ResultSet resultSet) throws SQLException;

}
