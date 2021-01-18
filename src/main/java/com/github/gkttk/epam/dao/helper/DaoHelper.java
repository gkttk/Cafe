package com.github.gkttk.epam.dao.helper;

import com.github.gkttk.epam.dao.dto.CommentInfoDao;
import com.github.gkttk.epam.dao.dto.UserInfoDao;
import com.github.gkttk.epam.dao.entity.CommentDao;
import com.github.gkttk.epam.dao.entity.DishDao;
import com.github.gkttk.epam.dao.entity.OrderDao;
import com.github.gkttk.epam.dao.entity.UserDao;

/**
 * Implementations of the interface can create different daos with the same connection for transaction management.
 */
public interface DaoHelper {

    UserInfoDao createUserInfoDao();

    CommentInfoDao createCommentInfoDao();

    CommentDao createCommentDao();

    OrderDao createOrderDao();

    UserDao createUserDao();

    DishDao createDishDao();
}
