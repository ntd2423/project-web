import com.google.common.collect.Lists;
import com.ntd.common.Setting;
import com.ntd.entity.User;
import com.ntd.redis.JedisTemplate;
import com.ntd.service.UserService;
import com.ntd.trigger.Dispatcher;
import com.ntd.trigger.impl.other.UserChangeEvent;
import com.ntd.utils.JSONUtil;
import com.ntd.utils.SpringContextUtil;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import redis.clients.jedis.Jedis;

/**
 * Created by nongtiedan on 2016/8/2.
 */
public class UserTest extends SpringTestCase {

    @Resource
    private UserService userService;

    @Resource
    private JedisTemplate jedisTemplate;

    @Test
    public void selectUserByIdTest(){
        User user = userService.selectUserById(1);
        System.out.println(user.getName() + ":" + user.getPhone());
    }

    @Test
    @Rollback(false)
    public void insertUserTest(){
        User user = new User();
        user.setUserId(4);
        user.setName("test");
        user.setPhone("123456");
        user.setAge(20);
        Assert.assertEquals(userService.insertUser(user) > 0, true);
    }

    @Test
    public void testRedis() {
        String result = jedisTemplate.execute(new JedisTemplate.JedisAction<String>() {
            @Override
            public String action(Jedis jedis) {
                return jedis.set("testKey", "123456");
            }
        });
        
        System.out.println(result);
    }

    @Test
    public void testCache() {
        List<Map<String, Object>> mapList = null;
        String listKey = "shopLotteryGetLotteryRewardList";
        if(jedisTemplate.exists(listKey, true)){
            String listJson = jedisTemplate.get(listKey, true);
            if(StringUtils.isNotBlank(listJson)) {
                mapList = JSONUtil.fromJson(listJson, new TypeReference<List<Map<String, Object>>>(){});
            }
        }else{
            mapList = Lists.newArrayList();
            jedisTemplate.setex(listKey, 60, JSONUtil.toJson(mapList), true);
        }

        System.out.println(mapList);
    }


    @Test
    public void otherTest(){
        System.out.println(Setting.isDebug());
        System.out.println(Setting.getNum().length);

        UserService userService = (UserService)SpringContextUtil.getBean("userServiceImpl");
        //UserService userService = (UserService)SpringContextUtil.getBean(UserService.class);
        User user = userService.selectUserById(1);
        System.out.println(user.getName() + ":" + user.getPhone());

        User userAop = userService.selectUserBySlave(1);
        System.out.println(userAop.getName() + ":" + userAop.getPhone());

        Dispatcher.fire(new UserChangeEvent(1, "abc", "level"));

    }

}
