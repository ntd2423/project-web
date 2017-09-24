import com.ntd.entity.GiftRank;
import com.ntd.service.GiftService;
import com.ntd.utils.DateCommonUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;

/**
 * Created by nongtiedan on 2016/8/2.
 */
public class GiftTest extends SpringTestCase {

    @Resource
    private GiftService giftService;


    @Test
    public void currentRankByUserIdTest() throws ParseException {

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
        System.out.println(start);
        System.out.println(end);
        List<GiftRank> max1 = giftService.selectSendMax(start, end, 271);
        System.out.println(max1 == null?0:max1.size());

        Date date1 = DateCommonUtils.addDays(DateCommonUtils.getMondayTime(), -7);
        Date date2 = DateCommonUtils.getMondayTime();
        System.out.println(date1);
        System.out.println(date2);
        List<GiftRank> max2 = giftService.selectSendMax(date1, date2, 271);
        System.out.println(max2 == null?0:max2.size());

    }

}
