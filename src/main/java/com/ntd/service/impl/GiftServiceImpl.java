package com.ntd.service.impl;

import com.ntd.dao.GiftDao;
import com.ntd.entity.GiftRank;
import com.ntd.service.GiftService;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by nongtiedan on 2016/8/2.
 */
@Service
@Transactional(readOnly = true)
public class GiftServiceImpl implements GiftService {

    @Resource
    private GiftDao giftDao;

    @Override
    public List<GiftRank> getCurrentRankByUserId(Date start, Date end, Long userId, int limit) {
        return giftDao.getCurrentRankByUserId(start, end, userId, limit);
    }

    @Override
    public List<GiftRank> selectSendMax(Date start,Date end ,int giftId) {
        return giftDao.selectSendMax(start, end, giftId);
    }

}
