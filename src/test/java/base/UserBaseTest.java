package base;

import com.ntd.entity.User;
import com.ntd.service.UserService;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import static org.junit.Assert.assertEquals;

/**
 * Created by nongtiedan on 2016/8/2.
 */
public class UserBaseTest extends SpringBaseTestCase {

    @Resource
    private UserService userService;

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

}
