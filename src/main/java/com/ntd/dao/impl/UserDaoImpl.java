package com.ntd.dao.impl;

import com.ntd.dao.UserDao;
import com.ntd.entity.User;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * Created by nongtiedan on 2016/8/2.
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Resource
    private SqlSession sqlSession;

    @Override
    public User selectUserById(long userId) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("userId", userId);
        return sqlSession.selectOne("user.selectUserById", params);
    }

    @Override
    public int insertUser(User user) {
        return sqlSession.insert("user.insertUser", user);
    }

}
