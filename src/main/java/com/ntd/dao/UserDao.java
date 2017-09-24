package com.ntd.dao;

import com.ntd.entity.User;

/**
 * Created by nongtiedan on 2016/8/2.
 */
public interface UserDao {

    public User selectUserById(long userId);

    public int insertUser(User user);

}
