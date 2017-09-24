package com.ntd.service.impl;

import com.ntd.aop.annotation.cache.Cacheable;
import com.ntd.aop.annotation.db.SwitchDataSource;
import com.ntd.dao.UserDao;
import com.ntd.common.Const;
import com.ntd.entity.User;
import com.ntd.service.UserService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by nongtiedan on 2016/8/2.
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public User selectUserById(long userId) {
        return userDao.selectUserById(userId);
    }

    @Override
    @Transactional(readOnly=false, rollbackFor={Exception.class})
    public int insertUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    @SwitchDataSource(Const.DB.SLAVE)
    public User selectUserBySlave(long userId) {
        return userDao.selectUserById(userId);
    }

    @Override
    @Cacheable(keyPre = "testSelectUserByCache", cacheTime = 60)
    public User selectUserByCache(long userId) {
        return userDao.selectUserById(userId);
    }

}
