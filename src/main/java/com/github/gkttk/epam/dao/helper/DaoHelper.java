package com.github.gkttk.epam.dao.helper;

import com.github.gkttk.epam.dao.dto.CommentInfoDao;
import com.github.gkttk.epam.dao.dto.UserInfoDao;
import com.github.gkttk.epam.dao.entity.CommentDao;
import com.github.gkttk.epam.dao.entity.DishDao;
import com.github.gkttk.epam.dao.entity.OrderDao;
import com.github.gkttk.epam.dao.entity.UserDao;

public interface DaoHelper {

    UserInfoDao createUserInfoDao();

    CommentInfoDao createCommentInfoDao();

    CommentDao createCommentDao();

    OrderDao createOrderDao();

    UserDao createUserDao();

    DishDao createDishDao();
}
