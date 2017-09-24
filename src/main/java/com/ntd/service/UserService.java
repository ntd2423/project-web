package com.ntd.service;

import com.ntd.entity.User;

/**
 * Created by nongtiedan on 2016/8/2.
 */
public interface UserService {

    public User selectUserById(long userId);

    public int insertUser(User user);

    public User selectUserBySlave(long userId);

    public User selectUserByCache(long userId);

}
