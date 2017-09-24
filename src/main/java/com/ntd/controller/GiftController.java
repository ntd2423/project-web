package com.ntd.controller;

import com.google.common.collect.Maps;
import com.ntd.entity.GiftRank;
import com.ntd.service.GiftService;
import com.ntd.utils.DateCommonUtils;
import com.ntd.utils.JSONResponseBuilder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/gift")
public class GiftController {

    private static final Log logger = LogFactory.getLog(GiftController.class);

    @Resource
    private GiftService giftService;

    /**
     * 礼物信息
     */
    @RequestMapping("/giftInfo")
    public void userInfo(@RequestParam(value="var", required=false) String var,
                         HttpServletRequest request,
                         HttpServletResponse response) throws Exception {

        try {
            Calendar cal = Calendar.getInstance(Locale.CHINA);
            cal.setFirstDayOfWeek(Calendar.MONDAY);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            Date startTime = null;
            Date endTime = null;
            Date currentWeekMonday = cal.getTime();

            endTime = currentWeekMonday;
            cal.add(Calendar.WEEK_OF_YEAR, -1);
            startTime = cal.getTime();
            System.out.println(startTime);
            System.out.println(endTime);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        Map<String, Object> result = Maps.newHashMap();
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date currentDate = new Date();
            String currentStr = sdf.format(currentDate);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(sdf.parse(currentStr));
            int week = calendar2.get(Calendar.DAY_OF_WEEK)-1;
            Date end = null;
            Date start = null;
            if (week != 0) {		//周一至周六
                Calendar cal = Calendar.getInstance();
                cal.setTime(sdf.parse(currentStr));
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                start = cal.getTime();
                cal.add(Calendar.DATE, 7);
                end = cal.getTime();
            } else {		//周日
                Calendar cal = Calendar.getInstance();
                cal.setTime(sdf.parse(currentStr));
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                end = cal.getTime();
                cal.add(Calendar.DATE, -7);
                start = cal.getTime();
            }
            start = DateCommonUtils.addDays(start, -7);
            end = DateCommonUtils.addDays(end, -7);
            logger.info(start);
            logger.info(end);
            List<GiftRank> max1 = giftService.selectSendMax(start, end, 271);
            logger.info(max1 == null?0:max1.get(0));

            Date date1 = DateCommonUtils.addDays(DateCommonUtils.getMondayTime(), -7);
            Date date2 = DateCommonUtils.getMondayTime();
            logger.info(date1);
            logger.info(date2);
            List<GiftRank> max2 = giftService.selectSendMax(date1, date2, 271);
            logger.info(max2 == null?0:max2.get(0));

            result.put("success", 200);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if(StringUtils.isNotBlank(var)) {
                JSONResponseBuilder.buildRespVar(response, result, var);
            } else {
                JSONResponseBuilder.buildResp(response, result);
            }
        }
    }

}
