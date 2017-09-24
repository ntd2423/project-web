package com.ntd.service;

import com.ntd.entity.GiftRank;
import java.util.Date;
import java.util.List;

/**
 * Created by nongtiedan on 2016/8/2.
 */
public interface GiftService {

    public List<GiftRank> getCurrentRankByUserId(Date start, Date end, Long userId, int limit);

    public List<GiftRank> selectSendMax(Date start,Date end ,int giftId);

}
