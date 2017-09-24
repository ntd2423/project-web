package com.ntd.dao.impl;

import com.ntd.dao.GiftDao;
import com.ntd.entity.GiftRank;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * Created by nongtiedan on 2016/8/2.
 */
@Repository
public class GiftDaoImpl implements GiftDao {

    @Resource
    private SqlSession sqlSession;

    @Override
    public List<GiftRank> getCurrentRankByUserId(Date start, Date end, Long userId, int limit) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("start", start);
        params.put("end", end);
        params.put("userId", userId);
        params.put("limit", limit);
        return sqlSession.selectList("gift.select_current_rank_by_userId", params);
    }

    @Override
    public List<GiftRank> selectSendMax(Date start,Date end ,int giftId){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("start", start);
        params.put("end", end);
        params.put("giftId", giftId);
        return sqlSession.selectList("gift.select_send_max", params);
    }

}
