package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.dto.UserInfo;
import com.github.gkttk.epam.model.enums.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

public class UserInfoRowMapperTest {

    private final static String ID_KEY = "id";
    private final static String LOGIN_KEY = "login";
    private final static String ROLE_KEY = "role";
    private final static String POINTS_KEY = "points";
    private final static String BLOCKED_KEY = "blocked";

    private final RowMapper<UserInfo> rowMapper = new UserInfoRowMapper();
    private final ResultSet resultSetMock = Mockito.mock(ResultSet.class);

    @Test
    public void testMapShouldReturnEntity() throws SQLException {
        //given
        long id = 1L;
        String login = "testLogin";
        UserRole role = UserRole.USER;
        int points = 50;
        boolean isBlocked = false;

        UserInfo expectedEntity = new UserInfo(id, login, role, points, isBlocked);

        when(resultSetMock.getLong(ID_KEY)).thenReturn(id);
        when(resultSetMock.getString(LOGIN_KEY)).thenReturn(login);
        when(resultSetMock.getString(ROLE_KEY)).thenReturn(role.name());
        when(resultSetMock.getString(ROLE_KEY)).thenReturn(role.name());
        when(resultSetMock.getInt(POINTS_KEY)).thenReturn(points);
        when(resultSetMock.getBoolean(BLOCKED_KEY)).thenReturn(isBlocked);
        //when
        UserInfo result = rowMapper.map(resultSetMock);
        //then
        Assertions.assertEquals(expectedEntity, result);

    }
}