package com.github.gkttk.epam.dao.mappers;

import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import static org.mockito.Mockito.when;

public class UserRowMapperTest {

    private final static String ID_KEY = "id";
    private final static String LOGIN_KEY = "login";
    private final static String ROLE_KEY = "role";
    private final static String POINTS_KEY = "points";
    private final static String MONEY_KEY = "money";
    private final static String BLOCKED_KEY = "blocked";
    private final static String AVATAR_KEY = "avatar";

    private final RowMapper<User> rowMapper = new UserRowMapper();
    private final ResultSet resultSetMock = Mockito.mock(ResultSet.class);

    @Test
    public void testMapShouldReturnEntity() throws SQLException {
        //given
        long userId = 1L;
        String login = "testLogin";
        UserRole role = UserRole.USER;
        int points = 50;
        BigDecimal money = BigDecimal.TEN;
        boolean isBlocked = false;
        String imgBase64 = "dGVzdEltZ0Jhc2U2NA==";
        byte[] img = Base64.getDecoder().decode(imgBase64);

        Blob blobMock = Mockito.mock(Blob.class);
        User expectedEntity = new User(userId, login, role, points, money, isBlocked, imgBase64);

        when(resultSetMock.getLong(ID_KEY)).thenReturn(userId);
        when(resultSetMock.getString(LOGIN_KEY)).thenReturn(login);
        when(resultSetMock.getString(ROLE_KEY)).thenReturn(role.name());
        when(resultSetMock.getInt(POINTS_KEY)).thenReturn(points);
        when(resultSetMock.getBigDecimal(MONEY_KEY)).thenReturn(money);
        when(resultSetMock.getBoolean(BLOCKED_KEY)).thenReturn(isBlocked);
        when(resultSetMock.getBlob(AVATAR_KEY)).thenReturn(blobMock);
        when(blobMock.length()).thenReturn((long) img.length);
        when(blobMock.getBytes(1, img.length)).thenReturn(img);
        //when
        User result = rowMapper.map(resultSetMock);
        //then
        Assertions.assertEquals(expectedEntity, result);

    }
}